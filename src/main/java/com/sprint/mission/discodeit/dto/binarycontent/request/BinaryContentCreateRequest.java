package com.sprint.mission.discodeit.dto.binarycontent.request;

import lombok.Getter;

@Getter
public class BinaryContentCreateRequest {

    String fileName;
    String fileType;
    byte[] data;

    public BinaryContentCreateRequest(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public void validate() {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("fileName는 null이나 공백이면 안됨");
        }

        if (fileType == null || fileType.trim().isEmpty()) {
            throw new IllegalArgumentException("fileType는 null이나 공백이면 안됨");
        }

        if (data == null) {
            throw new IllegalArgumentException("data는 null이면 안됨");
        }
    }

    public BinaryContentCreateServiceRequest toServiceRequest() {
        return BinaryContentCreateServiceRequest.builder()
                .fileName(fileName)
                .fileType(fileType)
                .data(data)
                .build();
    }
}
