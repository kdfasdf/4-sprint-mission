package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserById(UUID userId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    List<User> findAll();

    void deleteById(UUID userId);
}
