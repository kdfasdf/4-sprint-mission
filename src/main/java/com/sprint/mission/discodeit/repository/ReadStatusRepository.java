package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ReadStatusRepository {

    void save(ReadStatus readStatus);

    Optional<ReadStatus> findReadStatusById(UUID id);

    Set<ReadStatus> findAllReadStatus();

    List<ReadStatus> findAllByUserId(UUID userId);

    Optional<ReadStatus> findReadStatusByUserIdAndChannelId(UUID id, UUID channelId);

    void deleteById(UUID id);

    void deleteByUserIdAndChannelId(UUID userId, UUID channelId);

    void deleteAllByChannelId(UUID channelId);
}
