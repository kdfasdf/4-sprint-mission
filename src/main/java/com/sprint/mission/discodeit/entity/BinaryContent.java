package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BinaryContent extends BaseEntity {

    private final UUID userId;
    private final UUID messageId;
    private final byte[] data;

    private final Instant createdAt;

    private String fileName;
    private FileType fileType;

    @Builder
    public BinaryContent(UUID userId, UUID messageId, String fileName, FileType fileType, byte[] data) {
        this.userId = userId;
        this.messageId = messageId;
        this.data = data;
        this.createdAt = Instant.now();
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
