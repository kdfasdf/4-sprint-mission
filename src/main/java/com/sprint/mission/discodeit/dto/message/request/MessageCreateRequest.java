package com.sprint.mission.discodeit.dto.message.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class MessageCreateRequest {

    @NotBlank(message = "메시지는 null이거나 빈문자열, 공백이면 안됨")
    private final String content;

    @NotNull(message = "채널은 null이면 안됨")
    private final UUID channelId;

    @NotNull(message = "유저은 null이면 안됨")
    private final UUID authorId;

    public MessageCreateServiceRequest toServiceRequest(List<MultipartFile> attachments) {
        return MessageCreateServiceRequest.builder()
            .message(content)
            .channelId(channelId)
            .userId(authorId)
            .attachments(attachments)
            .build();
    }
}
