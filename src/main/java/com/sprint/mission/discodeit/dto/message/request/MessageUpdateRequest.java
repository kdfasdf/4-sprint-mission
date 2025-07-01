package com.sprint.mission.discodeit.dto.message.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageUpdateRequest {

    private final String content;

    private final UUID channelId;

    private final UUID userId;

    @Builder
    private MessageUpdateRequest(String content, UUID channelId, UUID userId) {
        this.content = content;
        this.channelId = channelId;
        this.userId = userId;
    }

    private void validate(String content, UUID channelId, UUID userId) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("content는 NULL이거나 공백이면 안됨");
        }
        if (channelId == null) {
            throw new IllegalArgumentException("channelId는 NULL이면 안됨");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId는 NULL이면 안됨");
        }
    }

    public MessageUpdateServiceRequest toServiceRequest(UUID messageId) {
        return MessageUpdateServiceRequest.builder()
                .messageId(messageId)
                .content(content)
                .channelId(channelId)
                .userId(userId)
                .build();
    }
}
