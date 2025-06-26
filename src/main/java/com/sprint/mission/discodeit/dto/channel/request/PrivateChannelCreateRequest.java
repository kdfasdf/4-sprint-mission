package com.sprint.mission.discodeit.dto.channel.request;

import java.util.UUID;
import lombok.Getter;

@Getter
public class PrivateChannelCreateRequest {

    private UUID hostId;
    private String channelTypeCode;

    public PrivateChannelCreateRequest(UUID hostId, String channelTypeCode) {
        validate(hostId, channelTypeCode);
        this.hostId = hostId;
        this.channelTypeCode = channelTypeCode;
    }

    public void validate(UUID hostId , String channelTypeCode) {

        if (hostId == null) {
            throw new IllegalArgumentException("유저은 null이면 안됨");
        }

        if (channelTypeCode == null || channelTypeCode.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 타입 코드은 null이나 공백이면 안됨");
        }

        if(!channelTypeCode.startsWith("CHANNEL-2")) {
            throw new IllegalArgumentException("비밀 채널 타입은 2로 시작해야함");
        }
    }

    public PrivateChannelCreateServiceRequest toServiceRequest() {
        return PrivateChannelCreateServiceRequest.builder()
                .hostId(hostId)
                .channelTypeCode(channelTypeCode)
                .build();
    }


}
