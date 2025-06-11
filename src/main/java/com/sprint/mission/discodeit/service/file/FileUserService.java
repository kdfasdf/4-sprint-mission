package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.MemberStatus;
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
        directory = Paths.get(System.getProperty("user.dir"), "data");
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
    public Optional<User> findUserById(UUID id) {
        return findUsers().stream()
                .filter(user -> user.getId().equals(id))
                .filter(user -> user.getMemberStatus() == MemberStatus.ACTIVE)
                .findFirst();
    }

    @Override
    public Optional<User> findDormantUserById(UUID id) {
        return findUsers().stream()
                .filter(user -> user.getId().equals(id))
                .filter(user -> user.getMemberStatus() == MemberStatus.DORMANT)
                .findFirst();
    }

    @Override
    public Optional<User> findDeletedUserById(UUID id) {
        return findUsers().stream()
                .filter(user -> user.getId().equals(id))
                .filter(user -> user.getMemberStatus() == MemberStatus.DELETED)
                .findFirst();
    }

    @Override
    public List<User> findUsers() {
        return FileUtils.load(directory, User.class);
    }

    @Override
    public List<User> findDormantUsers() {
        return findUsers().stream()
                .filter(user -> user.getMemberStatus() == MemberStatus.DORMANT)
                .toList();
    }

    @Override
    public List<User> findDeletedUsers() {
        return findUsers().stream()
                .filter(user -> user.getMemberStatus() == MemberStatus.DELETED)
                .toList();
    }

    @Override
    public void updateUser(UUID userId, User updatedUser) {
        User findUser = findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Optional.ofNullable(updatedUser.getUserName()).ifPresent(findUser::updateUserName);
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(findUser::updateEmail);
        Optional.ofNullable(updatedUser.getPhoneNumber()).ifPresent(findUser::updatePhoneNumber);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(findUser::updatePassword);
        Optional.ofNullable(updatedUser.getMemberStatus()).ifPresent(findUser::editMemberStatus);

        Path filePath = directory.resolve(userId.toString().concat(".ser"));
        FileUtils.save(filePath, findUser);

    }

    @Override
    public void deleteUser(UUID userId) {
        Path filePath = directory.resolve(userId.toString().concat(".ser"));
        FileUtils.remove(filePath);
    }
}
