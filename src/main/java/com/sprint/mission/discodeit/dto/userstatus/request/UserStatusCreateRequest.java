package com.sprint.mission.discodeit.dto.userstatus.request;

import java.util.UUID;
import lombok.Getter;

@Getter
public class UserStatusCreateRequest {

    private final UUID userId;

    public UserStatusCreateRequest(UUID userId) {
        validate(userId);
        this.userId = userId;
    }

    public void validate(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId는 null이면 안됨");
        }
    }

    public UserStatusCreateServiceRequest toServiceRequest() {
        return UserStatusCreateServiceRequest.builder()
                .userId(userId)
                .build();
    }
}
