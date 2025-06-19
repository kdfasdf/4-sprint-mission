package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;
import org.apache.logging.log4j.CloseableThreadContext.Instance;

public class BinaryContent {

    private final UUID id;
    private final UUID userId;
    private final UUID messageId;

    private final Instant createdAt;

    public BinaryContent(UUID id, UUID userId, UUID messageId) {
        this.id = id;
        this.userId = userId;
        this.messageId = messageId;
        this.createdAt = Instant.now();
    }


}
