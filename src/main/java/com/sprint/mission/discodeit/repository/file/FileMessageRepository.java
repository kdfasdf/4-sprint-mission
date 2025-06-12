package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.util.FileUtils;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileMessageRepository implements MessageRepository {

    private static Path directory;
    private static FileMessageRepository fileMessageRepository;

    public static synchronized FileMessageRepository getInstance() {
        if(fileMessageRepository == null) {
            fileMessageRepository = new FileMessageRepository();
        }
        return fileMessageRepository;
    }

    private FileMessageRepository() {
        directory = Path.of(System.getProperty("user.dir"), "data", "message");
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
    public List<Message> findMessages() {
        return FileUtils.load(directory);
    }

    @Override
    public void delete(Message message) {
        Path filePath = directory.resolve(message.getId().toString().concat(".ser"));
        FileUtils.remove(filePath);
    }

}
