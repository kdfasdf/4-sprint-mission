package com.sprint.mission.discodeit.dto.channel.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChannelUpdateRequest {

    private final String name;

    private final String description;

    public ChannelUpdateServiceRequest toServiceRequest(UUID channelId) {
        return ChannelUpdateServiceRequest.builder()
                .channelId(channelId)
                .name(name)
                .description(description)
                .build();
    }
}
