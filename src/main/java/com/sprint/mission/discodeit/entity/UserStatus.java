package com.sprint.mission.discodeit.entity;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserStatus extends BaseEntity {

    private final UUID userId;

    private Instant lastOnlineTime = Instant.now();;

    @Builder
    public UserStatus(UUID userId) {
        this.userId = userId;
    }

    public boolean isOnline() {
        return Duration.between(lastOnlineTime, Instant.now()).toMinutes() <= 5;
    }

    public void updateLastOnlineTime() {
        this.lastOnlineTime = Instant.now();
        this.setUpdatedAt();
    }

}
