package com.sprint.mission.discodeit.dto.channel.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrivateChannelCreateRequest {

    @NotNull(message = "유저은 null이면 안됨")
    private final UUID hostId;

    @NotBlank(message = "채널 타입 코드은 null이나 공백이면 안됨")
    private final String channelTypeCode;

    public PrivateChannelCreateServiceRequest toServiceRequest() {
        return PrivateChannelCreateServiceRequest.builder()
                .hostId(hostId)
                .channelTypeCode(channelTypeCode)
                .build();
    }
}
