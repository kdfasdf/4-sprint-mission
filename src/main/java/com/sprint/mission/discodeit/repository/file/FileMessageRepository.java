package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.util.FileUtils;
import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

public class FileMessageRepository implements MessageRepository {

    private static Path directory;

    @Value("${discodeit.repository.message}")
    private String messageDir;

    public FileMessageRepository() {
    }

    @PostConstruct
    private void init() {
        directory = Path.of(System.getProperty("user.dir"), messageDir);
        FileUtils.initDirectory(directory);
    }

    @Override
    public void save(Message message) {
        Path filePath = directory.resolve(message.getId().toString().concat(".ser"));
        FileUtils.save(filePath, message);
    }

    @Override
    public Optional<Message> findMessageById(UUID messageId) {
        return findMessages().stream()
                .filter(message -> message.getId().equals(messageId))
                .findFirst();
    }

    @Override
    public Set<Message> findMessages() {
        return FileUtils.load(directory);
    }

    @Override
    public void delete(UUID messageId) {
        Path filePath = directory.resolve(messageId.toString().concat(".ser"));
        FileUtils.remove(filePath);
    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        Path filePath = directory.resolve(channelId.toString().concat(".ser"));
        findMessages().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .forEach(message -> FileUtils.remove(filePath));
    }
}
