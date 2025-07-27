package com.sprint.mission.discodeit.dto.userstatus;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserStatusResponse {

    private final UUID id;

    private final UUID userId;

    private final Instant lastActiveAt;

    private final boolean online;

    public UserStatusResponse(UserStatus userStatus) {
        this.id = userStatus.getId();
        this.userId = userStatus.getUserId();
        this.lastActiveAt = userStatus.getLastActiveAt();
        this.online = userStatus.isOnline();
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
