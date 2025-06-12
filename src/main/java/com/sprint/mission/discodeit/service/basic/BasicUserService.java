package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.MemberStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicUserService implements UserService {

    private UserRepository userRepository;

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(UUID userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public Optional<User> findDormantUserById(UUID userId) {
        return userRepository.findUsers()
                .stream()
                .filter(user -> user.getMemberStatus() == MemberStatus.DORMANT)
                .filter(user -> user.getId().equals(userId))
                .findFirst();
    }

    @Override
    public Optional<User> findDeletedUserById(UUID userId) {
        return userRepository.findUsers()
                .stream()
                .filter(user -> user.getMemberStatus() == MemberStatus.DELETED)
                .filter(user -> user.getId().equals(userId))
                .findFirst();
    }

    @Override
    public List<User> findUsers() {
        return userRepository.findUsers();
    }

    @Override
    public List<User> findDormantUsers() {
        return userRepository.findUsers()
                .stream()
                .filter(user -> user.getMemberStatus() == MemberStatus.DORMANT)
                .toList();
    }

    @Override
    public List<User> findDeletedUsers() {
        return userRepository.findUsers()
                .stream()
                .filter(user -> user.getMemberStatus() == MemberStatus.DELETED)
                .toList();
    }

    @Override
    public void updateUser(UUID userId, User updatedUser) {
        User findUser = findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        userRepository.delete(findUser);

        Optional.ofNullable(updatedUser.getUserName()).ifPresent(findUser::updateUserName);
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(findUser::updateEmail);
        Optional.ofNullable(updatedUser.getPhoneNumber()).ifPresent(findUser::updatePhoneNumber);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(findUser::updatePassword);
        Optional.ofNullable(updatedUser.getMemberStatus()).ifPresent(findUser::editMemberStatus);

        userRepository.save(findUser);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
