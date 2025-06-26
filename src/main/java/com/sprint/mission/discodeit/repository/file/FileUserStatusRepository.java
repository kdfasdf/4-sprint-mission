package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.util.FileUtils;
import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

public class FileUserStatusRepository implements UserStatusRepository {

    private static Path directory;

    @Value("${discodeit.repository.userStatus}")
    private String userStatusDir;

    public FileUserStatusRepository() {
    }

    @PostConstruct
    private void init() {
        directory = Paths.get(System.getProperty("user.dir"), userStatusDir);
        FileUtils.initDirectory(directory);
    }

    @Override
    public void save(UserStatus userStatus) {
        Path filePath = directory.resolve(userStatus.getId().toString().concat(".ser"));
        FileUtils.save(filePath, userStatus);
    }

    @Override
    public Set<UserStatus> findUserStatuses() {
        return FileUtils.load(directory);
    }

    @Override
    public Optional<UserStatus> findUserStatusById(UUID id) {
        return findUserStatuses().stream()
                .filter(userStatus -> userStatus.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<UserStatus> findUserStatusByUserId(UUID id) { return findUserStatuses().stream().filter(userStatus -> userStatus.getUserId().equals(id)).findFirst(); }

    @Override
    public void delete(UUID id) {
        directory.resolve(directory.resolve(id.toString().concat(".ser")));
    }
}
