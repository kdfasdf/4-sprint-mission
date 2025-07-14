package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.util.FileUtils;
import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("fileBinaryContentRepository")
public class FileBinaryContentRepository implements BinaryContentRepository {

    private static Path directory;

    @Value("${discodeit.repository.binaryContent}")
    private String binaryContentDir;

    public FileBinaryContentRepository() {
    }

    @PostConstruct
    private void init() {
        directory = Paths.get(System.getProperty("user.dir"), binaryContentDir);
        FileUtils.initDirectory(directory);
    }

    @Override
    public void save(BinaryContent binaryContent) {
        Path filePath = directory.resolve(binaryContent.getId().toString().concat(".ser"));
        FileUtils.save(filePath, binaryContent);
    }

    @Override
    public Set<BinaryContent> findBinaryContents() { return FileUtils.load(directory); }

    @Override
    public Optional<BinaryContent> findBinaryContentById(UUID binaryContentId) {
        return findBinaryContents().stream()
                .filter(binaryContent -> binaryContent.getId().equals(binaryContentId))
                .findFirst();
    }

//    @Override
//    public Optional<BinaryContent> findBinaryContentsByUserId(UUID userId) {
//        return findBinaryContents()
//                .stream()
//                .filter(binaryContent -> binaryContent.getUserId().equals(userId))
//                .findFirst();
//    }
//
//    @Override
//    public List<BinaryContent> findBinaryContentsByMessageId(UUID messageId) {
//        return findBinaryContents()
//                .stream()
//                .filter(binaryContent -> binaryContent.getMessageId().equals(messageId))
//                .toList();
//    }

    @Override
    public void deleteById(UUID binaryContentId) {
        Path filePath = directory.resolve(binaryContentId.toString().concat(".ser"));
        FileUtils.remove(filePath);
    }

//    @Override
//    public void deleteByUserId(UUID userId) {
//        Set<BinaryContent> binaryContents = findBinaryContents();
//
//        if(binaryContents.isEmpty()) {
//            return;
//        }
//
//        binaryContents
//                .stream()
//                .filter(binaryContent -> binaryContent.getUserId().equals(userId))
//                .map(BinaryContent::getId)
//                .forEach(this::deleteById);
//    }
}
