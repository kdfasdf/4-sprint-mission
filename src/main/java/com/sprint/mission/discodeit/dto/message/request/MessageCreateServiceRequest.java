package com.sprint.mission.discodeit.dto.message.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class MessageCreateServiceRequest {

    private final String message;

    private final UUID channelId;

    private final UUID userId;

    @Builder.Default
    private final List<MultipartFile> attachments = new ArrayList<>();

    public Message toEntity(List<BinaryContent> binaryContents) {
        return Message.builder()
                .content(message)
                .channelId(channelId)
                .userId(userId)
                .binaryContents(binaryContents)
                .build();
    }
}
