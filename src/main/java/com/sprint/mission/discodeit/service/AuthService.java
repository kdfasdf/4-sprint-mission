package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.auth.request.SignIn;
import com.sprint.mission.discodeit.dto.user.UserResponse;

public interface AuthService {

    UserResponse login(SignIn signIn);
}
