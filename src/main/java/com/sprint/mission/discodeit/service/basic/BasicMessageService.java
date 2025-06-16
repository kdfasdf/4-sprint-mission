package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;

    public BasicMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void createMessage(Message message, User user) {
        Optional.ofNullable(user).orElseThrow(() -> new IllegalArgumentException("User is null."));
        Optional.ofNullable(message).orElseThrow(() -> new IllegalArgumentException("Message is null."));
        messageRepository.save(message);
    }

    @Override
    public Message findMessageById(UUID messageId) {
        Message findMessage = messageRepository.findMessageById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found."));

        return findMessage;
    }

    @Override
    public List<Message> findMessagesByChannelId(UUID channelId) {
        return messageRepository.findMessages()
                .stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public void updateContent(UUID messageId, String content) {
        Optional.ofNullable(content).orElseThrow(() -> new IllegalArgumentException("Content is null."));

       Message message = messageRepository.findMessageById(messageId)
               .orElseThrow(() -> new IllegalArgumentException("Message not found."));

       message.editContent(content);

       messageRepository.save(message);
    }

    @Override
    public void deleteMessage(UUID messageId) {
        messageRepository.findMessageById(messageId)
                .ifPresent(messageRepository::delete);
    }

}
