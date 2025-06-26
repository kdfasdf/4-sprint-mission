package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.util.FileUtils;
import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

public class FileUserRepository implements UserRepository {

    private static Path directory;

    @Value("${discodeit.repository.user}")
    private String userDir;

    public FileUserRepository () {
    }

    @PostConstruct
    private void init() {
        directory = Paths.get(System.getProperty("user.dir"), userDir);
        FileUtils.initDirectory(directory);
    }

    @Override
    public void save(User user) {
        Path filePath = directory.resolve(user.getId().toString().concat(".ser"));
        FileUtils.save(filePath, user);
    }

    @Override
    public Optional<User> findUserById(UUID userId) {
        return findUsers().stream()
                .filter(user -> user.getId().equals(userId))
                .filter(user -> user.getActiveStatus() == ActiveStatus.ACTIVE)
                .findFirst();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return findUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getActiveStatus() == ActiveStatus.ACTIVE)
                .findFirst();
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return findUsers().stream()
                .filter(user -> user.getUserName().equals(userName))
                .filter(user -> user.getActiveStatus() == ActiveStatus.ACTIVE)
                .findFirst();
    }

    @Override
    public Set<User> findUsers() {
        return FileUtils.load(directory);
    }

    @Override
    public void delete(UUID userId) {
        Path filePath = directory.resolve(userId.toString().concat(".ser"));
        FileUtils.remove(filePath);
    }

}
