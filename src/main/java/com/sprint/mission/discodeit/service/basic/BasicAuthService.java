package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.UserAuthErrorCode;
import com.sprint.mission.discodeit.constant.UserStatusErrorCode;
import com.sprint.mission.discodeit.dto.auth.request.SignIn;
import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.UserAuthException;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

    @Qualifier("fileUserRepository")
    private final UserRepository userRepository;

    @Qualifier("fileUserStatusRepository")
    private final UserStatusRepository userStatusRepository;


    @Override
    public UserResponse login(SignIn signIn) {
        User user = userRepository.findUserByUserName(signIn.getUsername())
                .orElseThrow(() -> new UserAuthException(UserAuthErrorCode.INVALID_USERNAME));

        if (!user.getPassword().equals(signIn.getPassword())) {
            throw new UserAuthException(UserAuthErrorCode.INVALID_PASSWORD);
        }

        UserStatus userStatus = userStatusRepository.findUserStatusByUserId(user.getId())
                .orElseThrow(() -> new UserAuthException(UserStatusErrorCode.USER_STATUS_NOT_FOUND));

        userStatus.updateLastOnlineTime();
        userStatusRepository.save(userStatus);

        return new UserResponse(user, userStatus);
    }
}
