package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.UserAuthErrorCode;
import com.sprint.mission.discodeit.constant.UserStatusErrorCode;
import com.sprint.mission.discodeit.dto.auth.request.SignIn;
import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.UserAuthException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BasicAuthService implements AuthService {

    private final UserRepository userRepository;

    private final UserStatusRepository userStatusRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse login(SignIn signIn) {
        User user = userRepository.findByUsername(signIn.getUsername())
                .orElseThrow(() -> new UserAuthException(UserAuthErrorCode.INVALID_USERNAME));

        if (!passwordEncoder.matches(signIn.getPassword(), user.getPassword())) {
            throw new UserAuthException(UserAuthErrorCode.INVALID_PASSWORD);
        }

        UserStatus userStatus = userStatusRepository.findUserStatusByUserId(user.getId())
                .orElseThrow(() -> new UserAuthException(UserStatusErrorCode.USER_STATUS_NOT_FOUND));

        userStatus.updateLastActiveAt();
        userStatusRepository.save(userStatus);

        return userMapper.toResponse(user);
    }

}
