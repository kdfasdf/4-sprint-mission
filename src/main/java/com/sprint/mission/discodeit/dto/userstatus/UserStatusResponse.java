package com.sprint.mission.discodeit.dto.userstatus;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserStatusResponse {

    private final UUID userId;

    private final Instant lastActiveAt;

    private final Instant createdAt;

    private final boolean online;

    public UserStatusResponse(UserStatus userStatus) {
        this.userId = userStatus.getUserId();
        this.lastActiveAt = userStatus.getLastOnlineTime();
        this.online = userStatus.isOnline();
        this.createdAt = userStatus.getCreatedAt();
    }

    @Override
    public String toString() {
        return "UserStatusResponse{" +
                "isOnline=" + online +
                ", userId=" + userId +
                ", lastOnlineTime=" + lastActiveAt +
                '}';
    }
}
