package com.sprint.mission.discodeit.dto.readstatus;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ReadStatusResponse {

    private final UUID id;
    private final UUID channelId;
    private final UUID userId;
    private final Instant lastReadAt;
    private final Instant createdAt;
    private final Instant updatedAt;

    public ReadStatusResponse (ReadStatus readStatus) {
        this.id = readStatus.getId();
        this.channelId = readStatus.getChannelId();
        this.userId = readStatus.getUserId();
        this.lastReadAt = readStatus.getLastReadAt();
        this.createdAt = readStatus.getCreatedAt();
        this.updatedAt = readStatus.getUpdatedAt();
    }

    @Override
    public String toString() {
        return "ReadStatusResponse{" +
                "channelId=" + channelId +
                ", id=" + id +
                ", userId=" + userId +
                ", lastReadAt=" + lastReadAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
