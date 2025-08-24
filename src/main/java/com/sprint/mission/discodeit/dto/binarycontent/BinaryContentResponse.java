package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BinaryContentResponse {

    private UUID id;

    private String fileName;

    private Long size;

    private String contentType;

    private byte[] bytes;

    public BinaryContentResponse(BinaryContent binaryContent) {
        this.id = binaryContent.getId();
        this.fileName = binaryContent.getFileName();
        this.contentType = binaryContent.getContentType();
        this.bytes = binaryContent.getBytes();
        this.size = binaryContent.getSize();
    }

}
