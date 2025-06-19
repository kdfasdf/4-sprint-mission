package com.sprint.mission.discodeit.entity;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class UserStatus extends BaseEntity {

    private UUID userId;

    private Instant lastOnlineTime;

    public UserStatus(UUID userId, Instant lastOnlineTime) {
        this.userId = userId;
        this.lastOnlineTime = lastOnlineTime;
    }

    public boolean isOnline() {
        return Duration.between(lastOnlineTime, Instant.now()).toMinutes() <= 5;
    }

    public void updateLastOnlineTime() {
        this.lastOnlineTime = Instant.now();
        this.setUpdatedAt();
    }

}
