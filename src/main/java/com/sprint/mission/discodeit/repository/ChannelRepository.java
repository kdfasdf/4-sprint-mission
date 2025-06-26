package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ChannelRepository {

    void save(Channel channel);

    Optional<Channel> findChannelById(UUID channelId);

    Set<Channel> findChannels();

    void delete(UUID channelId);

}
