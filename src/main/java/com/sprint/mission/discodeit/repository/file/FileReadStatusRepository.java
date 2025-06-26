package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.util.FileUtils;
import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

public class FileReadStatusRepository implements ReadStatusRepository {

    private static Path directory;

    @Value("${discodeit.repository.readStatus}")
    private String readStatusDir;

    public FileReadStatusRepository() {
    }

    @PostConstruct
    private void init() {
        directory = Paths.get(System.getProperty("user.dir"), readStatusDir);
        FileUtils.initDirectory(directory);
    }

    @Override
    public void save(ReadStatus readStatus) {
        Path filePath = directory.resolve(readStatus.getId().toString().concat(".ser"));
        FileUtils.save(filePath, readStatus);
    }

    @Override
    public Set<ReadStatus> findAllReadStatus() {
        return FileUtils.load(directory);
    }

    @Override
    public Optional<ReadStatus> findReadStatusById(UUID id) {
        return findAllReadStatus().stream()
                .filter(readStatus -> readStatus.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return findAllReadStatus().stream()
                .filter(readStatus -> readStatus.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Optional<ReadStatus> findReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {
        return findAllReadStatus().stream()
                .filter(readStatus -> readStatus.getUserId().equals(userId) && readStatus.getChannelId().equals(channelId))
                .findFirst();
    }

    @Override
    public void deleteById(UUID id) {
        directory.resolve(directory.resolve(id.toString().concat(".ser")));
    }

    @Override
    public void deleteByUserIdAndChannelId(UUID userId, UUID channelId) {
        findAllReadStatus().stream()
                .filter(readStatus -> readStatus.getId().equals(userId) && readStatus.getChannelId().equals(channelId))
                .findFirst()
                .ifPresent(readStatus -> directory.resolve(directory.resolve(readStatus.getId().toString().concat(".ser"))));
    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        findAllReadStatus().stream()
                .filter(readStatus -> readStatus.getChannelId().equals(channelId))
                .map(ReadStatus::getId)
                .forEach(this::deleteById);
    }
}
