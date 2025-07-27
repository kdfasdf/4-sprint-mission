package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserStatusRepository extends JpaRepository<UserStatus, UUID> {

    Optional<UserStatus> findUserStatusById(UUID id);

    @Query("SELECT us FROM UserStatus us WHERE us.user.id = :id")
    Optional<UserStatus> findUserStatusByUserId(UUID id);

    @Query("DELETE FROM UserStatus us WHERE us.user.id = :userId")
    void deleteByUserId(UUID userId);
}
