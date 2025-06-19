package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.util.FileUtils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileChannelService implements ChannelService {

    private static FileChannelService fileChannelService;
    private static FileUserService fileUserService = FileUserService.getInstance();

    private static final Path directory;

    static {
        directory = Paths.get(System.getProperty("user.dir"), "data", "channel");
        FileUtils.initDirectory(directory);
    }

    public static synchronized FileChannelService getInstance() {
        if (fileChannelService == null) {
            fileChannelService = new FileChannelService();
        }
        return fileChannelService;
    }

    private FileChannelService() {}

    @Override
    public void createChannel(Channel channel, User user) {
        if(user.getActiveStatus() == ActiveStatus.ACTIVE) {
            channel.addUser(user);
            Path filePath = directory.resolve(channel.getId().toString().concat(".ser"));
            FileUtils.save(filePath, channel);
            fileUserService.createUser(user);
        }
    }

    @Override
    public List<Channel> findChannels() {
        return FileUtils.load(directory);
    }

    @Override
    public Channel findChannelById(UUID channelId) {
        Channel findChannel = findChannels().stream()
                .filter(channel -> channel.getId().equals(channelId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));

        return findChannel;
    }

    @Override
    public void updateChannel(UUID channelId, Channel updatedChannel) {
        Channel findChannel = findChannelById(channelId);

        Optional.ofNullable(updatedChannel.getChannelName()).ifPresent(findChannel::editChannelName);
        Optional.ofNullable(updatedChannel.getDescription()).ifPresent(findChannel::editDescription);

        Path filePath = directory.resolve(channelId.toString().concat(".ser"));
        FileUtils.save(filePath, findChannel);
    }

    @Override
    public void deleteChannel(Channel channel) {
        Path filePath = directory.resolve(channel.getId().toString().concat(".ser"));
        FileUtils.remove(filePath);
    }

    @Override
    public void addUser(UUID channelId, User user) {
        Channel findChannel = findChannelById(channelId);

        findChannel.addUser(user);
        Path filePath = directory.resolve(channelId.toString().concat(".ser"));
        FileUtils.save(filePath, findChannel);
        fileUserService.createUser(user);
    }

    @Override
    public void removeUser(UUID channelId, User user) {
        Channel findChannel = findChannelById(channelId);

        findChannel.removeUser(user);
        Path filePath = directory.resolve(channelId.toString().concat(".ser"));
        FileUtils.remove(filePath);
        fileUserService.createUser(user);
    }

    @Override
    public void addMessage(UUID channelId, User user, Message message) {
        Channel findChannel = findChannelById(channelId);
        findChannel.addMessage(user, message);
        Path filePath = directory.resolve(channelId.toString().concat(".ser"));
        FileUtils.save(filePath, findChannel);
        fileUserService.createUser(user);
    }

    @Override
    public void removeMessage(UUID channelId, User user, Message message) {
        Channel findChannel = findChannelById(channelId);
        findChannel.removeMessage(user, message);
        Path filePath = directory.resolve(channelId.toString().concat(".ser"));
        FileUtils.save(filePath, findChannel);
        fileUserService.createUser(user);
    }

}
