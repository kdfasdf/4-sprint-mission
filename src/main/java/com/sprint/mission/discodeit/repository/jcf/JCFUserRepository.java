package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {

    private final Set<User> data;

    public JCFUserRepository() {
        this.data = new LinkedHashSet<>();
    }

    @Override
    public void save(User user) {
        data.add(user);
    }

    @Override
    public Set<User> findUsers() { return data; }

    @Override
    public Optional<User> findUserById(UUID userId) {
        return data.stream()
                .filter(user -> user.getId() == userId)
                .findFirst();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return data.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return data.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst();
    }

    @Override
    public void delete(UUID userId) {
        data.removeIf(user -> user.getId().equals(userId));
    }
}
