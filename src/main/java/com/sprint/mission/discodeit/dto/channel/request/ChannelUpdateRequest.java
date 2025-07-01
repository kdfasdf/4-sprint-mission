package com.sprint.mission.discodeit.dto.channel.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChannelUpdateRequest {

    private final String channelName;

    private final String description;


    @Builder
    private ChannelUpdateRequest(String channelName, String description) {
        validate(channelName, description);
        this.channelName = channelName;
        this.description = description;
    }

    public ChannelUpdateServiceRequest toServiceRequest(UUID channelId) {
        return ChannelUpdateServiceRequest.builder()
                .channelId(channelId)
                .channelName(channelName)
                .description(description)
                .build();
    }

    private void validate(String channelName, String description) {
        if (channelName != null && channelName.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 이름은 공백이면 안됨");
        }

        if (description != null && description.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 설명은 공백이면 안됨");
        }
    }
}
