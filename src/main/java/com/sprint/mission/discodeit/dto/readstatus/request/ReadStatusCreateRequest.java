package com.sprint.mission.discodeit.dto.readstatus.request;

import java.util.UUID;
import lombok.Getter;

@Getter
public class ReadStatusCreateRequest {

    private final UUID channelId;
    private final UUID userId;

    public ReadStatusCreateRequest(UUID channelId, UUID userId) {
        validate(channelId, userId);
        this.channelId = channelId;
        this.userId = userId;
    }

    private void validate(UUID channelId, UUID userId) {
        if(channelId == null && userId == null) {
            throw new IllegalArgumentException("channelId와 userId는 모두 null이면 안됨");
        }

        if (channelId == null) {
            throw new IllegalArgumentException("channelId는 null이면 안됨");
        }

        if (userId == null) {
            throw new IllegalArgumentException("userId는 null이면 안됨");
        }
    }

    public ReadStatusCreateServiceRequest toServiceRequest() {
        return ReadStatusCreateServiceRequest.builder()
                .channelId(channelId)
                .userId(userId)
                .build();
    }
}
