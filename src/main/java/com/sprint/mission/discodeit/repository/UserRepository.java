package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository {

    void save(User user);

    Optional<User> findUserById(UUID userId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUserName(String userName);

    Set<User> findUsers();

    void delete(UUID userId);

}
