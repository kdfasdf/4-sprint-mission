package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateServiceRequest;
import com.sprint.mission.discodeit.dto.message.request.MessageUpdateServiceRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public void createMessage(MessageCreateServiceRequest request) {
        Message message = request.toEntity();

        Channel findChannel = channelRepository.findChannelById(request.getChannelId())
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));

        User findUser = userRepository.findUserById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        findChannel.addMessage(message);
        findUser.addMessage(message);

        channelRepository.save(findChannel);
        userRepository.save(findUser);
        messageRepository.save(message);
    }

    private void addBinaryContentsToMessage(List<BinaryContent> binaryContents) {
        if(!binaryContents.isEmpty()) {
            for (BinaryContent content : binaryContents) {
                binaryContentRepository.save(content);
            }
        }
    }

    @Override
    public MessageResponse findMessageById(UUID messageId) {
        Message findMessage = messageRepository.findMessageById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found."));

        return new MessageResponse(findMessage);
    }

    @Override
    public List<MessageResponse> findMessagesByChannelId(UUID channelId) {
        return messageRepository.findMessages()
                .stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .map(MessageResponse::new)
                .toList();
    }

    @Override
    public List<MessageResponse> findMessagesByUserId(UUID userId) {
        return messageRepository.findMessages()
                .stream()
                .filter(message -> message.getUserId().equals(userId))
                .map(MessageResponse::new)
                .toList();
    }

    @Override
    public MessageResponse updateContent(MessageUpdateServiceRequest request) {
        Optional.ofNullable(request.getContent()).orElseThrow(() -> new IllegalArgumentException("Content is null."));

        Message messageToUpdate = messageRepository.findMessageById(request.getMessageId())
               .orElseThrow(() -> new IllegalArgumentException("Message not found."));

        User author = userRepository.findUserById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Channel channel = channelRepository.findChannelById(request.getChannelId())
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));


        editUserMessageContent(author, request);
        editChannelMessageContent(channel, request);

        messageToUpdate.editContent(request.getContent());

        channelRepository.save(channel);
        userRepository.save(author);
        messageRepository.save(messageToUpdate);

        return new MessageResponse(messageToUpdate);
    }

    private void editUserMessageContent(User author, MessageUpdateServiceRequest request) {
        author.getMessages().stream()
                .filter(myMessage -> myMessage.getId().equals(request.getMessageId()))
                .findFirst()
                .ifPresentOrElse(myMessage -> myMessage.editContent(request.getContent()),
                        () -> {throw new IllegalArgumentException("Message not found.");}
                );
    }

    public void editChannelMessageContent(Channel channel, MessageUpdateServiceRequest request) {
        channel.getMessages().stream()
                .filter(channelMessage -> channelMessage.getId().equals(request.getMessageId()))
                .findFirst()
                .ifPresentOrElse(channelMessage -> channelMessage.editContent(request.getContent()),
                        () -> {throw new IllegalArgumentException("Message not found.");}
                );
    }

    @Override
    public void deleteMessage(UUID messageId) {

        Message findMessage = messageRepository.findMessageById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found."));

        User author = userRepository.findUserById(findMessage.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Channel channel = channelRepository.findChannelById(findMessage.getChannelId())
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));

        if(findMessage.getBinaryContents() != null && !findMessage.getBinaryContents().isEmpty()) {
            List<BinaryContent> binaryContent = binaryContentRepository.findBinaryContentsByMessageId(messageId);
            removeBinaryContentsOfMessage(binaryContent);
        }

        removeMessageFromUser(author, messageId);
        removeMessageFromChannel(channel, messageId);
        removeMessage(messageId);

    }

    private void removeBinaryContentsOfMessage(List<BinaryContent> binaryContents) {
        if(!binaryContents.isEmpty()) {
            for (BinaryContent content : binaryContents) {
                binaryContentRepository.deleteById(content.getId());
            }
        }
    }

    private void removeMessageFromUser(User author, UUID messageId) {
        author.getMessages().stream()
                .filter(myMessage -> myMessage.getId().equals(messageId))
                .findFirst()
                .ifPresentOrElse(
                        author::removeMessage,
                        () -> { throw new IllegalArgumentException("Message not found."); }
                );
    }

    private void removeMessageFromChannel(Channel channel, UUID messageId) {
        channel.getMessages().stream()
                .filter(channelMessage -> channelMessage.getId().equals(messageId))
                .findFirst()
                .ifPresentOrElse(
                        channel::removeMessage,
                        () -> { throw new IllegalArgumentException("Message not found."); }
                );
    }

    private void removeMessage(UUID messageId) {
        messageRepository.findMessageById(messageId)
                .ifPresentOrElse(
                        message -> messageRepository.delete(messageId),
                        () -> { throw new IllegalArgumentException("Message not found."); }
                );
    }
}
