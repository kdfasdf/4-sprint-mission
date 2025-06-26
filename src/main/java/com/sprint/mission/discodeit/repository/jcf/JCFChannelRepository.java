package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {

    private final Set<Channel> data;

    public JCFChannelRepository() {
        this.data = new LinkedHashSet<>();
    }

    @Override
    public void save(Channel channel) {
        data.add(channel);
    }

    @Override
    public Optional<Channel> findChannelById(UUID channelId) {
        return data.stream()
                .filter(channel -> channel.getId().equals(channelId))
                .findFirst();
    }


    @Override
    public Set<Channel> findChannels() {
        return data;
    }

    @Override
    public void delete(UUID channerlId) {
        data.removeIf(channel -> channel.getId().equals(channerlId));

    }

}
