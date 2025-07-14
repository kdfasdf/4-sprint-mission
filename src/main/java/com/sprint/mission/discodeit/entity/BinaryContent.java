package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BinaryContent extends BaseEntity {

//    private final UUID userId;
//    private final UUID messageId;
    private final byte[] bytes;

    private final Instant createdAt;

    private String contentType;
    private Long size;
    private String fileName;

//    private FileType fileType;

    @Builder
    @JsonCreator
    public BinaryContent(
//            @JsonProperty("userId") UUID userId,
//                         @JsonProperty("messageId") UUID messageId,
                         @JsonProperty("fileName") String fileName,
                         @JsonProperty("contentType") String contentType,
                         @JsonProperty("size") Long size,
//                         @JsonProperty("fileType") FileType fileType,
                         @JsonProperty("data") byte[] bytes) {
//        this.userId = userId;
//        this.messageId = messageId;
        this.bytes = bytes;
        this.createdAt = Instant.now();
        this.contentType = contentType;
        this.size = size;
        this.fileName = fileName;
//        this.fileType = fileType;
    }
}
