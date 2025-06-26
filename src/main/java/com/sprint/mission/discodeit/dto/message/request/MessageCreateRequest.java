package com.sprint.mission.discodeit.dto.message.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class MessageCreateRequest {

    private String message;

    private UUID channelId;

    private UUID userId;

    List<BinaryContent> binaryContents;

    public MessageCreateRequest(String message, UUID channelId, UUID userId, List<BinaryContent> binaryContents) {
        validate(message, channelId, userId);
        this.message = message;
        this.channelId = channelId;
        this.userId = userId;
        this.binaryContents = binaryContents;
    }

    public void validate(String message, UUID channelId, UUID userId) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("message는 null이거나 공백이면 안됨");
        }
        if (channelId == null ) {
            throw new IllegalArgumentException("channelId가 null이면 안됨");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId가 null이면 안됨");
        }
    }

    public MessageCreateServiceRequest toServiceRequest() {
        return MessageCreateServiceRequest.builder()
            .message(message)
            .channelId(channelId)
            .userId(userId)
            .build();
    }

}
