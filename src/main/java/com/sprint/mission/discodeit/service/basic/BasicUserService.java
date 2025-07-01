package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateServiceRequest;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateServiceRequest;
import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.factory.DefaultProfileFactory;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

    @Qualifier("fileUserRepository")
    private final UserRepository userRepository;

    @Qualifier("fileUserStatusRepository")
    private final UserStatusRepository userStatusRepository;

    @Qualifier("fileBinaryContentRepository")
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public void createUser(UserCreateServiceRequest request) {
        validateEmailDoesNotExist(request.getEmail());
        validateUserDoesNotExist(request.getUserName());

        BinaryContent profile = request.getProfile();

        User newUser = request.toEntity();

        if(profile == null) {
            profile = DefaultProfileFactory.createDefaultProfile(newUser.getId());
        }

        UserStatus newUserStatus = new UserStatus(newUser.getId());
        newUser.updateProfile(profile);

        binaryContentRepository.save(profile);
        userStatusRepository.save(newUserStatus);
        userRepository.save(newUser);
    }

    private void validateEmailDoesNotExist(String email) {
        userRepository.findUserByEmail(email)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email is duplicated.");
                });
    }

    private void validateUserDoesNotExist(String userName) {
        userRepository.findUserByUserName(userName)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("User name is duplicated.");
                });
    }

    @Override
    public UserResponse findUserById(UUID userId) {
        User findUser = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        UserStatus findUserStatus = userStatusRepository.findUserStatusByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User status not found."));


        return new UserResponse(findUser, findUserStatus);
    }

    @Override
    public UserResponse findDormantUserById(UUID userId) {
        User findDormantUser = userRepository.findUsers()
                .stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DORMANT)
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        UserStatus findUserStatus = userStatusRepository.findUserStatusById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User status not found."));


        return new UserResponse(findDormantUser, findUserStatus);
    }

    @Override
    public UserResponse findDeletedUserById(UUID userId) {
        User findDeletedUser = userRepository.findUsers()
                .stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DELETED)
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        UserStatus findUserStatus = userStatusRepository.findUserStatusById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User status not found."));


        return new UserResponse(findDeletedUser, findUserStatus);
    }

    @Override
    public List<UserResponse> findUsers() {
        return userRepository.findUsers()
                .stream()
                .map(user ->
                    new UserResponse(user, userStatusRepository.findUserStatusByUserId(user.getId())
                                .orElseThrow(() -> new IllegalArgumentException("User status not found.")))
                )
                .toList();
    }

    @Override
    public List<UserResponse> findDormantUsers() {
        return userRepository.findUsers()
                .stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DORMANT)
                .map(user ->
                        new UserResponse(user, userStatusRepository.findUserStatusById(user.getId())
                                        .orElseThrow(() -> new IllegalArgumentException("User status not found.")))
                )
                .toList();
    }

    @Override
    public List<UserResponse> findDeletedUsers() {
        return userRepository.findUsers()
                .stream()
                .filter(user -> user.getActiveStatus() == ActiveStatus.DELETED)
                .map(user ->
                        new UserResponse(user, userStatusRepository.findUserStatusById(user.getId())
                                .orElseThrow(() -> new IllegalArgumentException("User status not found.")))
                )
                .toList();
    }

    @Override
    public UserResponse updateUser(UserUpdateServiceRequest request) {

        User userToUpdate = userRepository.findUserById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Optional.ofNullable(request.getUserName()).ifPresent(userToUpdate::updateUserName);
        Optional.ofNullable(request.getEmail()).ifPresent(userToUpdate::updateEmail);
        Optional.ofNullable(request.getPhoneNumber()).ifPresent(userToUpdate::updatePhoneNumber);
        Optional.ofNullable(request.getPassword()).ifPresent(userToUpdate::updatePassword);

        userRepository.save(userToUpdate);

        return new UserResponse(userToUpdate, userStatusRepository.findUserStatusByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User status not found.")));
    }

    @Override
    public void deleteUser(UUID userId) {
        userStatusRepository.delete(userId);
        userRepository.delete(userId);
        binaryContentRepository.deleteByUserId(userId);
    }
}
