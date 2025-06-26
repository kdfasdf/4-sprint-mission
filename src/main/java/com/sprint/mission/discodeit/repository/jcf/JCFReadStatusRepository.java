package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JCFReadStatusRepository implements ReadStatusRepository {

    private final Set<ReadStatus> data;

    public JCFReadStatusRepository() {
        this.data = new LinkedHashSet<>();
    }

    @Override
    public void save(ReadStatus readStatus) {
        data.add(readStatus);
    }

    @Override
    public Optional<ReadStatus> findReadStatusById(UUID id) {
        return data.stream()
                .filter(readStatus -> readStatus.getId().equals(id))
                .findFirst();
    }

    @Override
    public Set<ReadStatus> findAllReadStatus() { return data; }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return data.stream()
                .filter(readStatus -> readStatus.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Optional<ReadStatus> findReadStatusByUserIdAndChannelId(UUID id, UUID channelId) {
        return data.stream()
                .filter(readStatus -> readStatus.getUserId().equals(id) && readStatus.getChannelId().equals(channelId))
                .findFirst();
    }

    @Override
    public void deleteById(UUID id) {
        data.removeIf(readStatus -> readStatus.getId().equals(id));
    }

    @Override
    public void deleteByUserIdAndChannelId(UUID userId, UUID channelId) {
        data.removeIf(readStatus -> readStatus.getUserId().equals(userId) && readStatus.getChannelId().equals(channelId));
    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        data.stream()
                .filter(readStatus -> readStatus.getChannelId().equals(channelId))
                .map(ReadStatus::getId)
                .forEach(this::deleteById);
    }


}
