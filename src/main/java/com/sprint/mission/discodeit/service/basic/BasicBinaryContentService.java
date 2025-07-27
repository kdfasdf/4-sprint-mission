package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.BinaryContentErrorCode;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.binarycontent.request.BinaryContentCreateServiceRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.exception.BinaryContentException;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentMapper binaryContentMapper;

    private final BinaryContentStorage binaryContentStorage;

    @Override
    public void createBinaryContent(BinaryContentCreateServiceRequest request) {
        BinaryContent binaryContent = request.toEntity();
        binaryContentStorage.put(binaryContent.getId(), request.getBytes());
        binaryContentRepository.save(request.toEntity());
    }

    @Override
    public void deleteById(UUID binaryContentId) {
        binaryContentRepository.deleteById(binaryContentId);
    }

    @Override
    public BinaryContentResponse findById(UUID binaryContentId) {
        return binaryContentRepository.findBinaryContentById(binaryContentId)
                .map(binaryContentMapper::toResponse)
                .orElseThrow(() -> new BinaryContentException(BinaryContentErrorCode.BINARY_CONTENT_NOT_FOUND));
    }

    @Override
    public List<BinaryContentResponse> findAllByIdIn(List<UUID> ids) {
        return ids.stream()
                .map(id -> binaryContentRepository.findBinaryContentById(id).orElseThrow(
                                () -> new BinaryContentException(BinaryContentErrorCode.BINARY_CONTENT_NOT_FOUND))
                )
                .map(binaryContentMapper::toResponse)
                .toList();
    }

    @Override
    public ResponseEntity<Resource> download(BinaryContentResponse response) {
        return binaryContentStorage.download(response);
    }
}
