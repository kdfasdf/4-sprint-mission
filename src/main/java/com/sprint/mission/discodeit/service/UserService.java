package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateServiceRequest;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateServiceRequest;
import java.util.List;
import java.util.UUID;

public interface UserService {

    // 생성
    void createUser(UserCreateServiceRequest request);

    //조회
    UserResponse findUserById(UUID userId);

    UserResponse findDormantUserById(UUID userId);

    UserResponse findDeletedUserById(UUID userId);

    List<UserResponse> findUsers();

    List<UserResponse> findDormantUsers();

    List<UserResponse> findDeletedUsers();

    //수정
    UserResponse updateUser(UserUpdateServiceRequest request);
    //삭제
    void deleteUser(UUID userId);
}
