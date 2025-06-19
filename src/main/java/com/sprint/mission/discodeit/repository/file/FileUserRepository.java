package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.util.FileUtils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileUserRepository implements UserRepository {

    private static Path directory;

    public FileUserRepository () {
        directory = Paths.get(System.getProperty("user.dir"), "data", "user");
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
    public List<User> findUsers() {
        return FileUtils.load(directory);
    }

    @Override
    public void delete(User user) {
        Path filePath = directory.resolve(user.getId().toString().concat(".ser"));
        FileUtils.remove(filePath);
    }

}
