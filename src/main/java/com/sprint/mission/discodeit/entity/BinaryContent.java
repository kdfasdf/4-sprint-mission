package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BinaryContent extends BaseEntity {

    private final byte[] bytes;

    private final Instant createdAt;

    private String contentType;
    private Long size;
    private String fileName;


    @Builder
    @JsonCreator
    public BinaryContent(
                         @JsonProperty("fileName") String fileName,
                         @JsonProperty("contentType") String contentType,
                         @JsonProperty("size") Long size,
                         @JsonProperty("data") byte[] bytes) {
        this.bytes = bytes;
        this.createdAt = Instant.now();
        this.contentType = contentType;
        this.size = size;
        this.fileName = fileName;
    }
}
