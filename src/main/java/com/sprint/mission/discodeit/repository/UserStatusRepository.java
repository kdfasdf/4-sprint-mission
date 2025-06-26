package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserStatusRepository {

    void save(UserStatus userStatus);

    Optional<UserStatus> findUserStatusById(UUID id);

    Optional<UserStatus> findUserStatusByUserId(UUID id);

    Set<UserStatus> findUserStatuses();

    void delete(UUID userId);

}
