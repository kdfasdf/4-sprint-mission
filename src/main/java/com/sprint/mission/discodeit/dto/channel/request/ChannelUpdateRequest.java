package com.sprint.mission.discodeit.dto.channel.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChannelUpdateRequest {

    private UUID   channelId;
    private String channelName;
    private String description;


    @Builder
    private ChannelUpdateRequest(UUID channelId, String channelName, String description) {
        validate(channelId, channelName, description );
        this.channelId = channelId;
        this.channelName = channelName;
        this.description = description;
    }

    public ChannelUpdateServiceRequest toServiceRequest() {
        return ChannelUpdateServiceRequest.builder()
                .channelId(channelId)
                .channelName(channelName)
                .description(description)
                .build();
    }

    private void validate(UUID channelId, String channelName, String description) {
        if (channelId == null) {
            throw new IllegalArgumentException("채널-ID는 null이면 안됨");
        }

        if (channelName != null && channelName.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 이름은 공백이면 안됨");
        }

        if (description != null && description.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 설명은 공백이면 안됨");
        }
    }

}
