package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {

    private final List<User> data;

    public JCFUserRepository() {
        this.data = new ArrayList<>();
    }

    @Override
    public void save(User user) {
        data.add(user);
    }

    @Override
    public List<User> findUsers() { return data; }

    @Override
    public Optional<User> findUserById(UUID userId) {
        return data.stream()
                .filter(user -> user.getId() == userId)
                .findFirst();
    }

    @Override
    public void delete(User user) {
        data.remove(user);
    }

}
