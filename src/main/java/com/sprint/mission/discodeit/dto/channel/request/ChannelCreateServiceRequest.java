package com.sprint.mission.discodeit.dto.channel.request;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChannelCreateServiceRequest {

    private String channelName;
    private String description;
    private UUID hostId;
    private String channelTypeCode;

    public Channel toEntity(ChannelType channelType) {
        return Channel.builder()
                .channelName(channelName)
                .description(description)
                .hostId(hostId)
                .channelType(channelType)
                .build();
    }

}
