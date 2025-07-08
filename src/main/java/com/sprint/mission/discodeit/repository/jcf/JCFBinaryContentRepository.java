package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JCFBinaryContentRepository implements BinaryContentRepository {

    private final Set<BinaryContent> data;

    public JCFBinaryContentRepository() {
        this.data = new LinkedHashSet<>();
    }

    @Override
    public void save(BinaryContent binaryContent) {
        data.add(binaryContent);
    }

    @Override
    public Optional<BinaryContent> findBinaryContentById(UUID id) {
        return data.stream()
                .filter(binaryContent -> binaryContent.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<BinaryContent> findBinaryContentsByUserId(UUID userId) {
        return data.stream()
                .filter(binaryContent -> binaryContent.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public Set<BinaryContent> findBinaryContents() {
        return data;
    }

    @Override
    public List<BinaryContent> findBinaryContentsByMessageId(UUID messageId) {
        return data.stream()
                .filter(binaryContent -> binaryContent.getMessageId().equals(messageId))
                .toList();
    }

    @Override
    public void deleteById(UUID binaryContentId) {
        data.removeIf(binaryContent -> binaryContent.getId().equals(binaryContentId));
    }

    @Override
    public void deleteByUserId(UUID userId) {
        data.stream()
                .filter(binaryContent -> binaryContent.getUserId().equals(userId))
                .map(BinaryContent::getId)
                .forEach(this::deleteById);
    }
}
