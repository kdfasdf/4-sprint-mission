package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BinaryContentRepository {

    void save(BinaryContent binaryContent);

    Optional<BinaryContent> findBinaryContentById(UUID id);

    Optional<BinaryContent> findBinaryContentsByUserId(UUID userId);

    Set<BinaryContent> findBinaryContents();

    List<BinaryContent> findBinaryContentsByMessageId(UUID messageId);

    void deleteById(UUID binaryContentId);

    void deleteByUserId(UUID userId);
}
