package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;
import lombok.Getter;

@Getter
public class BinaryContentResponse {

    private UUID id;

    private String fileName;

    private String contentType;

    private Instant createdAt;

    private Long size;

    private byte[] bytes;

    public BinaryContentResponse(BinaryContent binaryContent) {
        this.id = binaryContent.getId();
        this.fileName = binaryContent.getFileName();
        this.contentType = binaryContent.getContentType();
        this.bytes = binaryContent.getBytes();
        this.createdAt = binaryContent.getCreatedAt();
        this.size = binaryContent.getSize();
    }

    @Override
    public String toString() {
        return "BinaryContentResponse{" +
                ", fileName='" + fileName + '\'' +
                ", extension='" + contentType + '\'' +
                ", createdAt=" + createdAt +
                ", size=" + size +
                ", data=" + Arrays.toString(bytes) +
                '}';
    }
}
