package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.util.FileUtils;
import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

public class FileChannelRepository implements ChannelRepository {

    private static Path directory;

    @Value("${discodeit.repository.channel}")
    private String channelDir;

    public FileChannelRepository() {
    }

    @PostConstruct
    private void init() {
        directory = Paths.get(System.getProperty("user.dir"), channelDir);
        FileUtils.initDirectory(directory);
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
    public Set<Channel> findChannels() {
        return FileUtils.load(directory);
    }

    @Override
    public void delete(UUID channelId) {
        Path filePath = directory.resolve(channelId.toString().concat(".ser"));
        FileUtils.remove(filePath);
    }
}
