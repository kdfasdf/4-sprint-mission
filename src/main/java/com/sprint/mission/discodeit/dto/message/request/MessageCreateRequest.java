package com.sprint.mission.discodeit.dto.message.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageCreateRequest {

    @NotBlank(message = "메시지는 null이거나 빈문자열, 공백이면 안됨")
    private final String message;

    @NotNull(message = "채널은 null이면 안됨")
    private final UUID channelId;

    @NotNull(message = "유저은 null이면 안됨")
    private final UUID userId;

    private final List<BinaryContent> binaryContents;

    public MessageCreateServiceRequest toServiceRequest() {
        return MessageCreateServiceRequest.builder()
            .message(message)
            .channelId(channelId)
            .userId(userId)
            .build();
    }
}
