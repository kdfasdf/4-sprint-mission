package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.auth.request.RoleUpdateRequest;
import com.sprint.mission.discodeit.dto.user.UserResponse;

public interface AuthService {

    UserResponse updateRole(RoleUpdateRequest roleUpdateRequest);

    void registerAdmin();
}
