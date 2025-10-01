package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.BinaryContentErrorCode;
import com.sprint.mission.discodeit.constant.UserErrorCode;
import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateServiceRequest;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateServiceRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Role;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.BinaryContentException;
import com.sprint.mission.discodeit.exception.UserException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import com.sprint.mission.discodeit.util.BinaryContentConverter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

    private final UserRepository userRepository;

    private final UserStatusRepository userStatusRepository;

    private final BinaryContentRepository binaryContentRepository;

    private final BinaryContentStorage binaryContentStorage;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(UserCreateServiceRequest request) {
        log.info("User attempting registeration - username : {}, userEmail : {}", request.getUsername(), request.getEmail());
        validateEmailDoesNotExist(request.getEmail());
        validateUserDoesNotExist(request.getUsername());

        MultipartFile profile = request.getProfile();
        User newUser = userMapper.toEntity(request);
        UserStatus newUserStatus = new UserStatus(newUser);
        BinaryContent binaryProfile;

        newUser.updateUserStatus(newUserStatus);

        if(profile != null) {
            binaryProfile = getBinaryContent(profile);
            newUser.updateProfile(binaryProfile);
            binaryContentRepository.save(binaryProfile);
            binaryContentStorage.put(binaryProfile.getId(), binaryProfile.getBytes());
        }

        String password = request.getPassword();
        String hashedPassword = passwordEncoder.encode(password);
        newUser.updatePassword(hashedPassword);

        newUser.updateRole(Role.USER);

        userRepository.save(newUser);

        userStatusRepository.save(newUserStatus);
        log.info("user created successfully - userId : {}", newUser.getId());
        return userMapper.toResponse(newUser);
    }

    private void validateEmailDoesNotExist(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new UserException(UserErrorCode.EMAIL_DUPLICATED);
                });
        log.debug("email is not duplicated : {}", email);
    }

    private void validateUserDoesNotExist(String userName) {
        userRepository.findByUsername(userName)
                .ifPresent(user -> {
                    throw new UserException(UserErrorCode.USER_NAME_DUPLICATED);
                });
        log.debug("username is not duplicated : {}", userName);
    }

    private static BinaryContent getBinaryContent(MultipartFile profile) {
        BinaryContent binaryProfile;
        try {
            binaryProfile = BinaryContentConverter.toBinaryContent(profile);
        } catch(IOException e) {
            throw new BinaryContentException(BinaryContentErrorCode.MULTIPART_FILE_CONVERT_FAILED);
        }
        return binaryProfile;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUserById(UUID userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        return userMapper.toResponse(findUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserUpdateServiceRequest request) {
        User userToUpdate = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        log.info("User before update - userId : {}, username : {}, userEmail : {}", userToUpdate.getId(), userToUpdate.getUsername(), userToUpdate.getEmail());

        Optional.ofNullable(request.getNewUsername()).ifPresent(userToUpdate::updateUserName);
        Optional.ofNullable(request.getNewEmail()).ifPresent(userToUpdate::updateEmail);
        Optional.ofNullable(request.getNewPassword()).ifPresent(userToUpdate::updatePassword);
        Optional.ofNullable(request.getProfile()).ifPresentOrElse(binaryContent -> {
            BinaryContent updateBinaryContent = getBinaryContent(binaryContent);
            userToUpdate.updateProfile(updateBinaryContent);
            userRepository.save(userToUpdate);
            binaryContentRepository.save(updateBinaryContent);
            binaryContentStorage.put(updateBinaryContent.getId(), updateBinaryContent.getBytes());
        }, () -> userRepository.save(userToUpdate));

        log.info("User after update - userId : {}, username : {}, userEmail : {}", userToUpdate.getId(), userToUpdate.getUsername(), userToUpdate.getEmail());
        return userMapper.toResponse(userToUpdate);
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        userStatusRepository.deleteById(userId);
        userRepository.deleteById(userId);
        log.info("deleted user - userId : {}", userId);
    }
}
