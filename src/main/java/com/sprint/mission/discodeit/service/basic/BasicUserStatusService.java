package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.UserErrorCode;
import com.sprint.mission.discodeit.constant.UserStatusErrorCode;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponse;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusCreateServiceRequest;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusUpdateServiceRequest;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.UserException;
import com.sprint.mission.discodeit.exception.UserStatusException;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

    @Qualifier("fileUserStatusRepository")
    private final UserStatusRepository userStatusRepository;

    @Qualifier("fileUserRepository")
    private final UserRepository userRepository;

    @Override
    public void createUserStatus(UserStatusCreateServiceRequest request) {
        userRepository.findUserById(request.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if(userStatusRepository.findUserStatusById(request.getUserId()).isPresent()){
            throw new UserStatusException(UserStatusErrorCode.USER_STATUS_ALREADY_EXIST);
        }

        userStatusRepository.save(request.toEntity());
    }

    @Override
    public UserStatusResponse findUserStatusByUserId(UUID userId) {
        return userStatusRepository.findUserStatuses().stream()
                .filter(userStatus -> userStatus.getUserId().equals(userId))
                .findFirst()
                .map(UserStatusResponse::new)
                .orElseThrow(() -> new UserStatusException(UserStatusErrorCode.USER_STATUS_NOT_FOUND));
    }

    @Override
    public List<UserStatusResponse> findUserStatuses() {
        return userStatusRepository.findUserStatuses()
                .stream()
                .map(UserStatusResponse::new)
                .toList();
    }

    @Override
    public UserStatusResponse updateUserStatus(UserStatusUpdateServiceRequest request) {
        UserStatus userStatusToUpdate = userStatusRepository.findUserStatusByUserId(request.getUserId())
                .orElseThrow(() -> new UserStatusException(UserStatusErrorCode.USER_STATUS_NOT_FOUND));

        userStatusRepository.delete(request.getUserId());
        // 추후에 수정해야할 필드 추가되면 수정로직 추가

        userStatusToUpdate.updateLastOnlineTime();

        userStatusRepository.save(userStatusToUpdate);

        return new UserStatusResponse(userStatusToUpdate);

    }

    @Override
    public UserStatusResponse updateUserStatusByUserId(UUID userId) {
        UserStatus userStatusToUpdate = userStatusRepository.findUserStatusById(userId)
                .orElseThrow(() -> new UserStatusException(UserStatusErrorCode.USER_STATUS_NOT_FOUND));

        userStatusRepository.delete(userId);
        // 추후에 수정해야할 필드 추가되면 수정로직 추가

        userStatusToUpdate.updateLastOnlineTime();

        userStatusRepository.save(userStatusToUpdate);

        return new UserStatusResponse(userStatusToUpdate);
    }

    @Override
    public void deleteByUserId(UUID userId) {
        userStatusRepository.findUserStatusById(userId)
                .ifPresentOrElse(
                        userStatus -> userStatusRepository.delete(userStatus.getId()),
                        () -> { throw new UserStatusException(UserStatusErrorCode.USER_STATUS_NOT_FOUND); }
                );
    }
}
