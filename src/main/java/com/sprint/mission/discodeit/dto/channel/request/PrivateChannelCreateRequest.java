package com.sprint.mission.discodeit.dto.channel.request;

import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrivateChannelCreateRequest {

    @Size(min = 2, message = "비밀 채팅방은 최소 2명이 있어야 함")
    private final List<UUID> participantIds;

//    @NotBlank(message = "채널 타입 코드은 null이나 공백이면 안됨")
//    private final String channelTypeCode;

//    @NotNull("hostId은 null이면 안됨")
//    private final UUID hostId

    public PrivateChannelCreateServiceRequest toServiceRequest() {
        return PrivateChannelCreateServiceRequest.builder()
                .participantIds(participantIds)
//                .channelTypeCode(channelTypeCode)
                .build();
    }
}
