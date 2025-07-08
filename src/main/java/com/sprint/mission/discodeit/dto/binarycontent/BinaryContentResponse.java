package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.Arrays;
import java.util.UUID;
import lombok.Getter;

@Getter
public class BinaryContentResponse {

    private UUID binaryContentId;

    private String fileName;

    private String extension;

    private byte[] data;

    public BinaryContentResponse(BinaryContent binaryContent) {
        this.binaryContentId = binaryContent.getMessageId();
        this.fileName = binaryContent.getFileName();
        this.extension = binaryContent.getFileType().getExtension();
        this.data = binaryContent.getData();
    }

    @Override
    public String toString() {
        return "BinaryContentResponse{" +
                "binaryContentId=" + binaryContentId +
                ", fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
