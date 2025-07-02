package com.sprint.mission.discodeit.util;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.FileType;
import java.io.IOException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class BinaryContentConverter {
    public static BinaryContent toBinaryContent(UUID userId, MultipartFile file) throws IOException {
        return BinaryContent.builder()
                .userId(userId)
                .fileName(file.getOriginalFilename())
                .fileType(FileType.getFileTypeByExtension(file.getContentType()))
                .data(file.getBytes())
                .build();
    }
}
