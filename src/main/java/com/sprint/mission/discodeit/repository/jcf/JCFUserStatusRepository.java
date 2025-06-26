package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JCFUserStatusRepository implements UserStatusRepository {

    private final Set<UserStatus> data;

    public JCFUserStatusRepository() {
        this.data = new LinkedHashSet<>();
    }

    @Override
    public void save(UserStatus userStatus) {
        data.add(userStatus);
    }

    @Override
    public Optional<UserStatus> findUserStatusById(UUID id) {
        return data.stream()
                .filter(userStatus -> userStatus.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<UserStatus> findUserStatusByUserId(UUID userId) {
        return data.stream()
                .filter(userStatus -> userStatus.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public Set<UserStatus> findUserStatuses() {
        return data;
    }

    @Override
    public void delete(UUID userId) {
        data.removeIf(userStatus -> userStatus.getUserId().equals(userId));
    }

}
