package com.sprint.mission.discodeit.dto.userstatus.request;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserStatusCreateServiceRequest {

    private final UUID userId;

    public UserStatus toEntity() {
        return UserStatus.builder()
                .userId(userId)
                .build();
    }
}
