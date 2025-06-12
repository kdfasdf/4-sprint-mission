package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.MemberStatus;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.util.FileUtils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileMessageService implements MessageService {

    private static FileMessageService fileMessageService;
    private static final Path directory;

    static {
        directory = Paths.get(System.getProperty("user.dir"), "data", "message");
        FileUtils.initDirectory(directory);
    }

    public static synchronized FileMessageService getInstance() {
        if(fileMessageService == null) {
            fileMessageService = new FileMessageService();
        }
        return fileMessageService;
    }

    private FileMessageService() {

    }

    @Override
    public void createMessage(Message message, User user) {
        if(user.getMemberStatus() == MemberStatus.ACTIVE) {
            user.addMessage(message);
            Path filePath = directory.resolve(message.getId().toString().concat(".ser"));
            FileUtils.save(filePath, message);
            //Todo 유저 덮어쓰기
        }
    }

    @Override
    public Message findMessageById(UUID messageId) {
        Message findMessage = findAllMessages()
                .stream()
                .filter(message -> message.getId().equals(messageId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Message not found."));

        return findMessage;
    }

    @Override
    public List<Message> findMessagesByChannelId(UUID channelId) {
        return findAllMessages()
                .stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public void updateContent(UUID messageId, String content) {
        Message findMessage = findMessageById(messageId);

        findMessage.editContent(content);

        Path filePath = directory.resolve(messageId.toString().concat(".ser"));
        FileUtils.save(filePath, findMessage);
    }

    @Override
    public void deleteMessage(UUID messageId) {
        Path filePath = directory.resolve(messageId.toString().concat(".ser"));
        FileUtils.remove(filePath);
    }

    private List<Message> findAllMessages() {
        return FileUtils.load(directory);
    }

}
