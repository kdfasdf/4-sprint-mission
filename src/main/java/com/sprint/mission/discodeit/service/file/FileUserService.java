package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.util.FileUtils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileUserService implements UserService {

    private static FileUserService fileUserService;
    private static final Path directory;

    static {
        directory = Paths.get(System.getProperty("user.dir"), "data", "user");
        FileUtils.initDirectory(directory);
    }

    public static synchronized FileUserService getInstance() {
        if(fileUserService == null) {
            fileUserService = new FileUserService();
        }
        return fileUserService;
    }

    private FileUserService() {}

    @Override
    public void createUser(User user) {
        Path filePath = directory.resolve(user.getId().toString().concat(".ser"));
        FileUtils.save(filePath, user);
    }


    @Override
    public User findUserById(UUID id) {
        User findUser = findUsers().stream()
                .filter(user -> user.getId().equals(id))
                .filter(user -> user.getActiveStatus() == ActiveStatus.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return findUser;
    }

    @Override
    public User findDormantUserById(UUID id) {
        User findDormantUser = findUsers().stream()
                .filter(user -> user.getId().equals(id))
                .filter(user -> user.getActiveStatus() == ActiveStatus.DORMANT)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return findDormantUser;
    }

    @Override
    public User findDeletedUserById(UUID id) {
        User findDeletedUser = findUsers().stream()
                .filter(user -> user.getId().equals(id))
                .filter(user -> user.getActiveStatus() == ActiveStatus.DELETED)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return findDeletedUser;
    }

    @Override
    public List<User> findUsers() {
        return FileUtils.load(directory);
    }

    @Override
    public List<User> findDormantUsers() {
        return findUsers().stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DORMANT)
                .toList();
    }

    @Override
    public List<User> findDeletedUsers() {
        return findUsers().stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DELETED)
                .toList();
    }

    @Override
    public void updateUser(UUID userId, User updatedUser) {
        User findUser = findUserById(userId);

        Optional.ofNullable(updatedUser.getUserName()).ifPresent(findUser::updateUserName);
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(findUser::updateEmail);
        Optional.ofNullable(updatedUser.getPhoneNumber()).ifPresent(findUser::updatePhoneNumber);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(findUser::updatePassword);
        Optional.ofNullable(updatedUser.getActiveStatus()).ifPresent(findUser::editMemberStatus);

        Path filePath = directory.resolve(userId.toString().concat(".ser"));
        FileUtils.save(filePath, findUser);

    }

    @Override
    public void deleteUser(User user) {
        Path filePath = directory.resolve(user.getId().toString().concat(".ser"));
        FileUtils.remove(filePath);
    }
}
