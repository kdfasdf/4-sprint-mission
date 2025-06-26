package com.sprint.mission.discodeit.dto.message.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageCreateServiceRequest {

    private String message;

    private UUID channelId;

    private UUID userId;

    @Builder.Default
    List<BinaryContent> binaryContents = new ArrayList<>();

    public Message toEntity() {
        return Message.builder()
                .content(message)
                .channelId(channelId)
                .userId(userId)
                .binaryContents(binaryContents)
                .build();
    }

}
