package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.UserStatusErrorCode;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponse;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusUpdateServiceRequest;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.UserStatusException;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

    private final UserStatusRepository userStatusRepository;

    private final UserRepository userRepository;

    private final UserStatusMapper userStatusMapper;


    @Override
    @Transactional(readOnly = true)
    public UserStatusResponse findUserStatusByUserId(UUID userId) {
        return userStatusRepository.findUserStatusByUserId(userId)
                .map(userStatusMapper::toResponse)
                .orElseThrow(() -> new UserStatusException(UserStatusErrorCode.USER_STATUS_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserStatusResponse> findUserStatuses() {
        return userStatusRepository.findAll()
                .stream()
                .map(userStatusMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserStatusResponse updateUserStatus(UserStatusUpdateServiceRequest request) {
        UserStatus userStatusToUpdate = userStatusRepository.findUserStatusByUserId(request.getUserId())
                .orElseThrow(() -> new UserStatusException(UserStatusErrorCode.USER_STATUS_NOT_FOUND));

        userStatusToUpdate.updateLastActiveAt();

        userStatusRepository.save(userStatusToUpdate);

        return new UserStatusResponse(userStatusToUpdate);

    }

    @Override
    @Transactional
    public UserStatusResponse updateUserStatusByUserId(UUID userId) {
        UserStatus userStatusToUpdate = userStatusRepository.findUserStatusById(userId)
                .orElseThrow(() -> new UserStatusException(UserStatusErrorCode.USER_STATUS_NOT_FOUND));

        userStatusToUpdate.updateLastActiveAt();

        userStatusRepository.save(userStatusToUpdate);

        return userStatusMapper.toResponse(userStatusToUpdate);
    }

    @Override
    @Transactional
    public void deleteByUserId(UUID userId) {
        userStatusRepository.findUserStatusById(userId)
                .ifPresentOrElse(
                        userStatus -> userStatusRepository.deleteByUserId(userStatus.getId()),
                        () -> { throw new UserStatusException(UserStatusErrorCode.USER_STATUS_NOT_FOUND); }
                );
    }
}
