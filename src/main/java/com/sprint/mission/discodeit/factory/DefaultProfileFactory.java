package com.sprint.mission.discodeit.factory;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.FileType;
import java.util.UUID;

public class DefaultProfileFactory {

    public static BinaryContent createDefaultProfile(UUID userId) {
        byte[] defaultImage = new byte[0];
        return BinaryContent.builder()
                .userId(userId)
                .fileName("default-profile.png")
                .fileType(FileType.PNG)
                .data(defaultImage)
                .build();
    }
}
