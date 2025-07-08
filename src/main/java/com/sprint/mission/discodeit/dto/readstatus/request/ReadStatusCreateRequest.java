package com.sprint.mission.discodeit.dto.readstatus.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadStatusCreateRequest {

    @NotNull(message = "채널Id는 null이면 안됨")
    private final UUID channelId;

    @NotNull(message = "유저Id는 null이면 안됨")
    private final UUID userId;

    public ReadStatusCreateServiceRequest toServiceRequest() {
        return ReadStatusCreateServiceRequest.builder()
                .channelId(channelId)
                .userId(userId)
                .build();
    }
}
