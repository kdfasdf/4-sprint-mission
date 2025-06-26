package com.sprint.mission.discodeit.dto.userstatus;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserStatusResponse {

    private UUID userId;

    Instant lastOnlineTime;

    boolean isOnline;

    public UserStatusResponse(UserStatus userStatus) {
        this.userId = userStatus.getUserId();
        this.lastOnlineTime = userStatus.getLastOnlineTime();
        this.isOnline = userStatus.isOnline();
    }

    @Override
    public String toString() {
        return "UserStatusResponse{" +
                "isOnline=" + isOnline +
                ", userId=" + userId +
                ", lastOnlineTime=" + lastOnlineTime +
                '}';
    }
}
