package com.sprint.mission.discodeit.dto.userstatus.request;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserStatusUpdateRequest {

    private final Instant newLastActiveAt;

    public UserStatusUpdateRequest(Instant newLastActiveAt) {
        validate(newLastActiveAt);
        this.newLastActiveAt = newLastActiveAt;
    }

    public void validate(Instant lastActiveAt) {
        if (lastActiveAt == null) {
            throw new IllegalArgumentException("lastActiveAt는 null이면 안됨");
        }
    }

    public UserStatusUpdateServiceRequest toServiceRequest(UUID userId) {
        return UserStatusUpdateServiceRequest.builder()
                .userId(userId)
                .build();
    }
}
