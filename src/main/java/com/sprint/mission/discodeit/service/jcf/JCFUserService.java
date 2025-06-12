package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.MemberStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFUserService implements UserService {

    private final List<User> data;

    private static JCFUserService jcfUserService;

    public static synchronized JCFUserService getInstance() {
        if (jcfUserService == null) {
            jcfUserService = new JCFUserService();
        }
        return jcfUserService;
    }

    private JCFUserService() {
        this.data = new ArrayList<>();
    }

    @Override
    public void createUser(User user) {
        data.add(user);
    }

    @Override
    public User findUserById(UUID userId) {
        User findUser = data.stream()
                .filter(user -> user.getId() == userId)
                .filter(user -> user.getMemberStatus() == MemberStatus.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return findUser;
    }

    @Override
    public User findDormantUserById(UUID userId) {
        User findDormantUser = data.stream()
                .filter(user -> user.getId() == userId)
                .filter(user -> user.getMemberStatus() == MemberStatus.DORMANT)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return findDormantUser;
    }

    @Override
    public User findDeletedUserById(UUID userId) {
        User findDeletedUser = data.stream()
                .filter(user -> user.getId() == userId)
                .filter(user -> user.getMemberStatus() == MemberStatus.DELETED)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return findDeletedUser;
    }

    @Override
    public List<User> findUsers() {
        return data.stream()
                .filter(user -> user.getMemberStatus() == MemberStatus.ACTIVE)
                .toList();
    }

    @Override
    public List<User> findDormantUsers() {
        return data.stream()
                .filter(user -> user.getMemberStatus() == MemberStatus.DORMANT)
                .toList();
    }

    @Override
    public List<User> findDeletedUsers() {
        return data.stream()
                .filter(user -> user.getMemberStatus() == MemberStatus.DELETED)
                .toList();
    }

    @Override
    public void updateUser(UUID userId, User updatedUser) {
        User findUser = data.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Optional.ofNullable(updatedUser.getUserName()).ifPresent(findUser::updateUserName);
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(findUser::updateEmail);
        Optional.ofNullable(updatedUser.getPhoneNumber()).ifPresent(findUser::updatePhoneNumber);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(findUser::updatePassword);
        Optional.ofNullable(updatedUser.getMemberStatus()).ifPresent(findUser::editMemberStatus);

    }

    @Override
    public void deleteUser(User deleteUser) {
        data.stream()
                .filter(user -> user.getId() == deleteUser.getId())
                .findFirst()
                .ifPresent(data::remove);
    }

}
