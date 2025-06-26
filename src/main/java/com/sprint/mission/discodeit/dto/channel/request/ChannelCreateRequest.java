package com.sprint.mission.discodeit.dto.channel.request;

import java.util.UUID;
import lombok.Getter;

@Getter
public class ChannelCreateRequest {

    private final String channelName;
    private final String description;
    private final UUID hostId;
    private final String channelTypeCode;

    public ChannelCreateRequest(String channelName, String description, UUID hostId, String channelTypeCode) {
        validate(channelName, description, hostId, channelTypeCode);
        this.channelName = channelName;
        this.description = description;
        this.hostId = hostId;
        this.channelTypeCode = channelTypeCode;
    }

    public void validate(String channelName, String description, UUID hostId, String channelTypeCode) {
        if (channelName == null || channelName.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 이름은 null이거나 공백이면 안됨");
        }

        if (hostId == null) {
            throw new IllegalArgumentException("유저은 null이면 안됨");
        }

        if (description == null) {
            throw new IllegalArgumentException("채널 설명은 null이면 안됨");
        }

        if (channelTypeCode == null || channelTypeCode.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 타입 코드은 null이나 공백이면 안됨");
        }

        if (!channelTypeCode.startsWith("CHANNEL-1")) {
            throw new IllegalArgumentException("공공 채널 타입은 1로 시작해야함");
        }
    }

    public ChannelCreateServiceRequest toServiceRequest() {
        return ChannelCreateServiceRequest.builder()
                .channelName(channelName)
                .description(description)
                .hostId(hostId)
                .channelTypeCode(channelTypeCode)
                .build();
    }

}
