package com.sprint.mission.discodeit.dto.message.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageUpdateRequest {

    @NotBlank(message = "메시지는 null이거나 빈문자열, 공백이면 안됨")
    private final String content;

    @NotNull(message = "채널ID는 null이면 안됨")
    private final UUID channelId;

    @NotNull(message = "유저ID는 null이면 안됨")
    private final UUID userId;

    public MessageUpdateServiceRequest toServiceRequest(UUID messageId) {
        return MessageUpdateServiceRequest.builder()
                .messageId(messageId)
                .content(content)
                .channelId(channelId)
                .userId(userId)
                .build();
    }
}
