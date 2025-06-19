package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(User user) {
        Optional.ofNullable(user).orElseThrow(() -> new IllegalArgumentException("User is null."));
        userRepository.save(user);
    }

    @Override
    public User findUserById(UUID userId) {
        User findUser = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return findUser;
    }

    @Override
    public User findDormantUserById(UUID userId) {
        User findDormantUser = userRepository.findUsers()
                .stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DORMANT)
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return findDormantUser;
    }

    @Override
    public User findDeletedUserById(UUID userId) {
        User findDeletedUser = userRepository.findUsers()
                .stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DELETED)
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return findDeletedUser;
    }

    @Override
    public List<User> findUsers() {
        return userRepository.findUsers();
    }

    @Override
    public List<User> findDormantUsers() {
        return userRepository.findUsers()
                .stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DORMANT)
                .toList();
    }

    @Override
    public List<User> findDeletedUsers() {
        return userRepository.findUsers()
                .stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DELETED)
                .toList();
    }

    @Override
    public void updateUser(UUID userId, User updatedUser) {
        Optional.ofNullable(updatedUser).orElseThrow(() -> new IllegalArgumentException("User is null."));

        User findUser = findUserById(userId);

        userRepository.delete(findUser);

        Optional.ofNullable(updatedUser.getUserName()).ifPresent(findUser::updateUserName);
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(findUser::updateEmail);
        Optional.ofNullable(updatedUser.getPhoneNumber()).ifPresent(findUser::updatePhoneNumber);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(findUser::updatePassword);
        Optional.ofNullable(updatedUser.getActiveStatus()).ifPresent(findUser::editMemberStatus);

        userRepository.save(findUser);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
