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

    public PrivateChannelCreateServiceRequest toServiceRequest() {
        return PrivateChannelCreateServiceRequest.builder()
                .participantIds(participantIds)
                .build();
    }
}
