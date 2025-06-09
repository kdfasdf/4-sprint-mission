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

    /**
     * 이 부분 검토 부탁드립니다
     * 정책에 따라 상이할 것 같으나 ID를 통해 찾을 수 있는 사용자는 활성 사용자로 제한하였습니다.
     * 메서드명에는 해당 내용을 반영하지 않았는데 이유는 다음과 같습니다
     * 1. 많은 서비스에서 ID를 통해 사용자를 조회할 때 활성화된 사용자를 조회하는 것을 기본 정책으로 채택하고있습니다.
     * 2. 위 이유의 연장선으로 다른 상태의 사용자를 조회할 때 상태를 메서드에 포함시켜주면 됩니다.
     */
    @Override
    public Optional<User> findUserById(UUID userId) {
        return data.stream()
                .filter(user -> user.getId() == userId)  // 자료구조에 따라 다르겠으나 카디널리티 높은 필드 우선 필터링
                .filter(user -> user.getMemberStatus() == MemberStatus.ACTIVE)
                .findFirst();
    }

    @Override
    public Optional<User> findDormantUserById(UUID userId) {
        return data.stream()
                .filter(user -> user.getId() == userId)
                .filter(user -> user.getMemberStatus() == MemberStatus.DORMANT)
                .findFirst();
    }

    @Override
    public Optional<User> findDeletedUserById(UUID userId) {
        return data.stream()
                .filter(user -> user.getId() == userId)
                .filter(user -> user.getMemberStatus() == MemberStatus.DELETED)
                .findFirst();
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

        Optional.ofNullable(updatedUser.getUserName()).ifPresent(findUser::setUserName);
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(findUser::setEmail);
        Optional.ofNullable(updatedUser.getPhoneNumber()).ifPresent(findUser::setPhoneNumber);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(findUser::setPassword);
        Optional.ofNullable(updatedUser.getMemberStatus()).ifPresent(findUser::setMemberStatus);

    }

    @Override
    public void deleteUser(UUID userId) {
        data.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .ifPresent(data::remove);
    }

}
