package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {

    // 생성
    void createUser(User user);

    //조회
    User findUserById(UUID userId);

    User findDormantUserById(UUID userId);

    User findDeletedUserById(UUID userId);

    List<User> findUsers();

    List<User> findDormantUsers();

    List<User> findDeletedUsers();

    //수정
    void updateUser(UUID userId, User updatedUser);
    //삭제
    void deleteUser(User user);

}
