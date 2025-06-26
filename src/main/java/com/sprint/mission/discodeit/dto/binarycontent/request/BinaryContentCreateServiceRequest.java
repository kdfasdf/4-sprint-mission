package com.sprint.mission.discodeit.dto.binarycontent.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.FileType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BinaryContentCreateServiceRequest {

    String fileName;
    String fileType;
    byte[] data;

    public BinaryContent toEntity(FileType fileType) {
        return BinaryContent.builder()
                .fileName(fileName)
                .fileType(fileType)
                .data(data)
                .build();
    }

}
