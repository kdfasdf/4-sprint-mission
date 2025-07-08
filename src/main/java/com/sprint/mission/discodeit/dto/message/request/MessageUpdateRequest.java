package com.sprint.mission.discodeit.dto.message.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageUpdateRequest {

    private final UUID messageId;

    private final String content;

    private final UUID channelId;

    private final UUID userId;

    @Builder
    private MessageUpdateRequest(UUID messageId, String content, UUID channelId, UUID userId) {
        validate(messageId);
        this.messageId = messageId;
        this.content = content;
        this.channelId = channelId;
        this.userId = userId;
    }

    private void validate(UUID messageId) {
        if (messageId == null) {
            throw new IllegalArgumentException("MessageId is null.");
        }
    }

    public MessageUpdateServiceRequest toServiceRequest() {
        return MessageUpdateServiceRequest.builder()
                .messageId(messageId)
                .content(content)
                .channelId(channelId)
                .userId(userId)
                .build();
    }
}
