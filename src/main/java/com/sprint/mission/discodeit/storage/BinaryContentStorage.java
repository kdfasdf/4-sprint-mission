package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface BinaryContentStorage {
    UUID put (UUID binaryContentId, byte[] bytes);

    InputStream get(UUID binaryContentId);

    ResponseEntity<Resource> download(BinaryContentResponse response);
}
