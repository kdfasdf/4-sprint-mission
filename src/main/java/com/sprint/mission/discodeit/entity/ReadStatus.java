package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadStatus extends BaseEntity {

    private final UUID userId;
    private final UUID channelId;
    private Instant lastReadAt;

    @Builder
    public ReadStatus(UUID userId, UUID channelId) {
        this.userId = userId;
        this.channelId = channelId;
        this.lastReadAt = Instant.now();
    }

    public void updateLastReadAt() {
        this.setUpdatedAt();
    }

}
