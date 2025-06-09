package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    // 생성
    void createUser(User user);

    //조회
    Optional<User> findUserById(UUID userId);

    Optional<User> findDormantUserById(UUID userId);

    Optional<User> findDeletedUserById(UUID userId);

    List<User> findUsers();

    List<User> findDormantUsers();

    List<User> findDeletedUsers();

    //수정
    void updateUser(UUID userId, User updatedUser);
    //삭제
    void deleteUser(UUID userId);

}
