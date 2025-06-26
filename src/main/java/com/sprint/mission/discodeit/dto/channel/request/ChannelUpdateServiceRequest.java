package com.sprint.mission.discodeit.dto.channel.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChannelUpdateServiceRequest {

    private UUID   channelId;
    private String channelName;
    private String description;

}
