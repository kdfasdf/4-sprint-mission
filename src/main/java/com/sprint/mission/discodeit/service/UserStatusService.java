package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponse;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusCreateServiceRequest;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusUpdateServiceRequest;
import java.util.List;
import java.util.UUID;

public interface UserStatusService {

    void createUserStatus(UserStatusCreateServiceRequest request);

    UserStatusResponse findUserStatusByUserId(UUID userId);

    List<UserStatusResponse> findUserStatuses();

    UserStatusResponse updateUserStatus(UserStatusUpdateServiceRequest request);

    UserStatusResponse updateUserStatusByUserId(UUID userId);

    void deleteByUserId(UUID userId);
}
