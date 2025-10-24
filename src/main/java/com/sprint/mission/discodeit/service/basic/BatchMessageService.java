package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.ChannelErrorCode;
import com.sprint.mission.discodeit.constant.UserErrorCode;
import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateServiceRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.ChannelException;
import com.sprint.mission.discodeit.exception.UserException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchMessageService {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    private final LinkedBlockingQueue<PendingMessage> messageQueue = new LinkedBlockingQueue<>(10000);

    // 단일 배치 프로세서 스레드 (즉시 처리용)
    private final ExecutorService batchExecutor = Executors.newSingleThreadExecutor();

    // 배치 처리 중인지 체크
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    // 동적 배치 크기 (트래픽에 따라 조정)
    private final AtomicInteger targetBatchSize = new AtomicInteger(10);

    @PreDestroy
    public void shutdown() {
        batchExecutor.shutdown();
        try {
            if (!batchExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                batchExecutor.shutdownNow();
            }
            if (!messageQueue.isEmpty()) {
                processBatch();
            }
        } catch (InterruptedException e) {
            batchExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Transactional
    public MessageResponse createMessage(MessageCreateServiceRequest request) {

        // 검증 (캐시 활용 권장)
        User author = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Channel channel = channelRepository.findById(request.getChannelId())
                .orElseThrow(() -> new ChannelException(ChannelErrorCode.CHANNEL_NOT_FOUND));

        UUID messageId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        // CompletableFuture로 응답 관리 (DeferredResult보다 가벼움)
        CompletableFuture<MessageResponse> futureResponse = new CompletableFuture<>();

        PendingMessage pending = new PendingMessage(
                messageId,
                request.getMessage(),
                author.getId(),
                channel.getId(),
                now,
                now,
                futureResponse
        );

        try {
            messageQueue.put(pending);

            // 즉시 배치 처리 트리거 (대기 시간 0ms)
            triggerBatchIfNeeded();

            // 동기적으로 대기 (최대 1초)
            return futureResponse.get(1, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Message queue interrupted", e);
        } catch (TimeoutException e) {
            throw new RuntimeException("Message processing timeout", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Message processing failed", e.getCause());
        }
    }

    // 배치 처리 트리거 (스마트 배칭)
    private void triggerBatchIfNeeded() {
        int queueSize = messageQueue.size();
        int target = targetBatchSize.get();

        // 조건 1: 큐에 메시지가 있고, 처리 중이 아니면 즉시 실행
        // 조건 2: 타겟 배치 크기에 도달하면 우선순위 높게 실행
        if (queueSize > 0 && isProcessing.compareAndSet(false, true)) {
            batchExecutor.execute(this::processBatch);
        }
    }

    // 배치 처리 (연속 처리 방식)
    private void processBatch() {
        try {
            while (!messageQueue.isEmpty()) {
                int currentBatchSize = Math.min(targetBatchSize.get(), 100);
                List<PendingMessage> batch = new ArrayList<>(currentBatchSize);

                // 큐에서 꺼내기 (논블로킹)
                int drained = messageQueue.drainTo(batch, currentBatchSize);

                if (drained == 0) {
                    break;
                }

                long startTime = System.nanoTime();

                try {
                    insertBatch(batch);

                    long elapsedNano = System.nanoTime() - startTime;
                    double elapsedMs = elapsedNano / 1_000_000.0;
                    double avgPerMsg = elapsedMs / drained;

                    // 동적 배치 크기 조정
                    adjustBatchSize(elapsedMs, drained);

                    log.debug("✅ Batch: {} msgs in {:.2f}ms (avg: {:.2f}ms), queue: {}, target: {}",
                            drained, elapsedMs, avgPerMsg, messageQueue.size(), targetBatchSize.get());

                } catch (Exception e) {
                    log.error("❌ Batch failed: {} messages", drained, e);
                    batch.forEach(msg -> msg.getFutureResponse().completeExceptionally(e));
                }
            }
        } finally {
            isProcessing.set(false);

            // 처리 중 새 메시지가 들어왔으면 다시 트리거
            if (!messageQueue.isEmpty() && isProcessing.compareAndSet(false, true)) {
                batchExecutor.execute(this::processBatch);
            }
        }
    }

    // 동적 배치 크기 조정 (adaptive batching)
    private void adjustBatchSize(double elapsedMs, int processedCount) {
        int current = targetBatchSize.get();

        // 목표: 배치 처리 시간을 5ms 이하로 유지
        if (elapsedMs < 3.0 && processedCount >= current) {
            // 처리가 너무 빠르면 배치 크기 증가 (처리량 향상)
            targetBatchSize.compareAndSet(current, Math.min(current + 10, 100));
        } else if (elapsedMs > 5.0) {
            // 처리가 느리면 배치 크기 감소 (지연 시간 단축)
            targetBatchSize.compareAndSet(current, Math.max(current - 5, 5));
        }
        // 3~5ms 사이면 현재 크기 유지
    }

    // JDBC Batch Insert (Multi-row insert)
    private void insertBatch(List<PendingMessage> messages) {
        if (messages.isEmpty()) {
            return;
        }

        // 단일 INSERT 문으로 처리
        StringBuilder sql = new StringBuilder(
                "INSERT INTO messages (id, content, author_id, channel_id, created_at, updated_at) VALUES "
        );

        List<Object> params = new ArrayList<>(messages.size() * 6);

        for (int i = 0; i < messages.size(); i++) {
            PendingMessage msg = messages.get(i);

            if (i > 0) sql.append(", ");
            sql.append("(?, ?, ?, ?, ?, ?)");

            params.add(msg.getId());
            params.add(msg.getContent());
            params.add(msg.getAuthorId());
            params.add(msg.getChannelId());
            params.add(msg.getCreatedAt());
            params.add(msg.getUpdatedAt());
        }

        jdbcTemplate.update(sql.toString(), params.toArray());

        // 각 메시지에 응답 전달
        messages.forEach(msg -> {
            MessageResponse response = MessageResponse.builder()
                    .id(msg.getId())
                    .content(msg.getContent())
                    .channelId(msg.getChannelId())
                    .build();
            msg.getFutureResponse().complete(response);
        });
    }

    @Data
    @AllArgsConstructor
    private static class PendingMessage {
        private UUID id;
        private String content;
        private UUID authorId;
        private UUID channelId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private CompletableFuture<MessageResponse> futureResponse;
    }
}
