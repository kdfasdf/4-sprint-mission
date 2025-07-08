package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.auth.request.SignIn;
import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
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
        User user = userRepository.findUserByEmail(signIn.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (!user.getPassword().equals(signIn.getPassword())) {
            throw new IllegalArgumentException("Password is incorrect.");
        }

        UserStatus userStatus = userStatusRepository.findUserStatusByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User status not found."));

        userStatus.updateLastOnlineTime();
        userStatusRepository.save(userStatus);

        return new UserResponse(user, userStatus);
    }
}
