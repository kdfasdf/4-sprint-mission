package com.sprint.mission.discodeit.dto.readstatus;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ReadStatusResponse {

    private UUID id;
    private UUID channelId;
    private UUID userId;
    private Instant lastReadAt;

    public ReadStatusResponse (ReadStatus readStatus) {
        this.id = readStatus.getId();
        this.channelId = readStatus.getChannelId();
        this.userId = readStatus.getUserId();
        this.lastReadAt = readStatus.getLastReadAt();
    }

    @Override
    public String toString() {
        return "ReadStatusResponse{" +
                "channelId=" + channelId +
                ", id=" + id +
                ", userId=" + userId +
                ", lastReadAt=" + lastReadAt +
                '}';
    }
}
