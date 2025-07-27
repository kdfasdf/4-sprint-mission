package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinaryContentRepository extends JpaRepository<BinaryContent, UUID> {

    Optional<BinaryContent> findBinaryContentById(UUID id);

    void deleteById(UUID binaryContentId);

}
