package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.UserErrorCode;
import com.sprint.mission.discodeit.dto.auth.request.RoleUpdateRequest;
import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.entity.Role;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.UserException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BasicAuthService implements AuthService {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse updateRole(RoleUpdateRequest roleUpdateRequest) {
        User user = userRepository.findById(roleUpdateRequest.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        user.updateRole(roleUpdateRequest.getNewRole());
        userRepository.save(user);

        return userMapper.toResponse(user);

    }

    @Transactional
    public void registerAdmin() {
        Optional<User> existAdmin = userRepository.findByEmail(adminEmail);

        if(existAdmin.isPresent()) {
            return;
        }

        User newAdmin = User.builder()
                .username("admin")
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.ADMIN)
                .build();

        UserStatus userStatus = new UserStatus(newAdmin);
        newAdmin.updateUserStatus(userStatus);

        userRepository.save(newAdmin);
    }

}
