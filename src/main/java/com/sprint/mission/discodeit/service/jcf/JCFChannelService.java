package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFChannelService implements ChannelService {

    private static JCFChannelService jcfChannelService;
    private final List<Channel> data;

    private JCFChannelService() {
        this.data = new ArrayList<>();
    }

    public static synchronized JCFChannelService getInstance() {
        if (jcfChannelService == null) {
            jcfChannelService = new JCFChannelService();
        }
        return jcfChannelService;
    }

    @Override
    public void createChannel(Channel channel, User user) {
        if(user.getActiveStatus() == ActiveStatus.ACTIVE) {
            data.add(channel);
            channel.addUser(user);
        }
    }

    @Override
    public Channel findChannelById(UUID channelId) {
        Channel findChannel = data.stream()
                .filter(channel -> channel.getId() == channelId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));

        return findChannel;
    }

    @Override
    public List<Channel> findChannels() {
        return data;
    }

    @Override
    public void updateChannel(UUID channelId, Channel updatedChannel) {
        Channel findChannel = data.stream()
                .filter(channel -> channel.getId() == channelId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));

        findChannel.setUpdatedAt();

        Optional.ofNullable(updatedChannel.getChannelName()).ifPresent(findChannel::editChannelName);
        Optional.ofNullable(updatedChannel.getDescription()).ifPresent(findChannel::editDescription);

    }

    @Override
    public void addUser(UUID channelId, User user) {
        data.stream()
                .filter(channel -> channel.getId() == channelId)
                .findFirst()
                .ifPresent(channel -> {
                    channel.addUser(user);
                });
    }

    @Override
    public void removeUser(UUID channelId, User user) {
        data.stream()
                .filter(channel -> channel.getId() == channelId)
                .findFirst()
                .ifPresent(channel -> {
                    channel.removeUser(user);
                });
    }

    @Override
    public void addMessage(UUID channelId, User user, Message message) {
        data.stream()
                .filter(channel -> channel.getId() == channelId)
                .findFirst()
                .ifPresent(channel -> {
                    channel.addMessage(user, message);
                });
    }

    @Override
    public void removeMessage(UUID channelId, User user, Message message) {
        data.stream()
                .filter(channel -> channel .getId() == channelId)
                .findFirst()
                .ifPresent(channel -> {
                    channel.removeMessage(user, message);
                });
    }

    @Override
    public void deleteChannel(Channel deleteChannel) {
        data.stream()
                .filter(channel -> channel.getId() == deleteChannel.getId())
                .findFirst()
                .ifPresent(data::remove);
    }

}
