package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.util.FileUtils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {

    private static Path directory;
    private static FileChannelRepository fileChannelRepository;

    public static FileChannelRepository getInstance() {
        if(fileChannelRepository == null) {
            fileChannelRepository = new FileChannelRepository();
        }
        return fileChannelRepository;
    }

    private FileChannelRepository() {
        directory = Paths.get(System.getProperty("user.dir"), "data", "channel");
    }

    @Override
    public void save(Channel channel) {
        Path filePath = directory.resolve(channel.getId().toString().concat(".ser"));
        FileUtils.save(filePath, channel);
    }

    @Override
    public Optional<Channel> findChannelById(UUID channelId) {
        return findChannels().stream()
                .filter(channel -> channel.getId().equals(channelId))
                .findFirst();
    }

    @Override
    public List<Channel> findChannels() {
        return FileUtils.load(directory);
    }

    @Override
    public void delete(Channel channel) {
        Path filePath = directory.resolve(channel.getId().toString().concat(".ser"));
        FileUtils.remove(filePath);
    }

}
