package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.MemberStatus;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicChannelService implements ChannelService {

    private ChannelRepository channelRepository;
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    public BasicChannelService(ChannelRepository channelRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void createChannel(Channel channel, User user) {
        Optional.ofNullable(user).orElseThrow(() -> new IllegalArgumentException("User is null."));
        Optional.ofNullable(channel).orElseThrow(() -> new IllegalArgumentException("Channel is null."));

        if(user.getMemberStatus() == MemberStatus.ACTIVE) {
            user.addChannel(channel);
            channelRepository.save(channel);
            userRepository.save(user);
        }

    }

    @Override
    public Channel findChannelById(UUID channelId) {
        Channel findChannel = channelRepository.findChannelById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));

        return findChannel;
    }

    @Override
    public List<Channel> findChannels() {
        return channelRepository.findChannels();
    }

    @Override
    public void updateChannel(UUID channelId, Channel updatedChannel) {
        Channel findChannel = findChannelById(channelId);

        channelRepository.delete(findChannel);

        Optional.ofNullable(updatedChannel.getChannelName()).ifPresent(findChannel::editChannelName);
        Optional.ofNullable(updatedChannel.getDescription()).ifPresent(findChannel::editDescription);

        channelRepository.save(findChannel);
    }

    @Override
    public void deleteChannel(Channel channel) {
        channelRepository.delete(channel);
    }

    @Override
    public void addUser(UUID channelId, User user) {
        Optional.ofNullable(user).orElseThrow(() -> new IllegalArgumentException("User is null."));

        Channel findChannel = findChannelById(channelId);

        channelRepository.delete(findChannel);

        findChannel.addUser(user);

        channelRepository.save(findChannel);
        userRepository.save(user);
    }

    @Override
    public void removeUser(UUID channelId, User user) {
        Channel findChannel = findChannelById(channelId);

        channelRepository.delete(findChannel);

        findChannel.removeUser(user);

        channelRepository.save(findChannel);
    }

    @Override
    public void addMessage(UUID channelId, User user, Message message) {
        Optional.ofNullable(message).orElseThrow(() -> new IllegalArgumentException("Message is null."));

        Channel findChannel = findChannelById(channelId);

        channelRepository.delete(findChannel);

        findChannel.addMessage(user, message);

        channelRepository.save(findChannel);
        messageRepository.save(message);
        userRepository.save(user);
    }

    @Override
    public void removeMessage(UUID channelId, User user, Message message) {
        Channel findChannel = findChannelById(channelId);

        channelRepository.delete(findChannel);

        findChannel.removeMessage(user, message);

        channelRepository.save(findChannel);
        messageRepository.save(message);
        userRepository.save(user);
    }

}

