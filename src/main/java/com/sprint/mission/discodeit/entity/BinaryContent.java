package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonCreator
    public BinaryContent(@JsonProperty("userId") UUID userId,
                         @JsonProperty("messageId") UUID messageId,
                         @JsonProperty("fileName") String fileName,
                         @JsonProperty("fileType") FileType fileType,
                         @JsonProperty("data") byte[] data) {
        this.userId = userId;
        this.messageId = messageId;
        this.data = data;
        this.createdAt = Instant.now();
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
