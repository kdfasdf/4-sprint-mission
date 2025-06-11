package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {

    private final List<Channel> data;

    private static JCFChannelRepository jcfChannelRepository;

    public static synchronized JCFChannelRepository getInstance() {
        if (jcfChannelRepository == null) {
            jcfChannelRepository = new JCFChannelRepository();
        }
        return jcfChannelRepository;
    }

    private JCFChannelRepository() {
        this.data = new ArrayList<>();
    }

    @Override
    public void save(Channel channel) {
        data.add(channel);
    }

    @Override
    public Optional<Channel> findChannelById(UUID channelId) {
        return data.stream()
                .filter(channel -> channel.getId() == channelId)
                .findFirst();
    }


    @Override
    public List<Channel> findChannels() {
        return data;
    }

    @Override
    public void delete(Channel channel) {
        data.remove(channel);
    }

}
