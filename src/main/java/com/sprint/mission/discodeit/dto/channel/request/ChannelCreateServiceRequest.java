package com.sprint.mission.discodeit.dto.channel.request;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChannelCreateServiceRequest {

    private final String channelName;
    private final String description;
    private final UUID hostId;
    private final String channelTypeCode;

    public Channel toEntity(ChannelType channelType) {
        return Channel.builder()
                .channelName(channelName)
                .description(description)
                .hostId(hostId)
                .channelType(channelType)
                .build();
    }

}
