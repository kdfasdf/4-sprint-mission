package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.BinaryContentErrorCode;
import com.sprint.mission.discodeit.constant.ChannelErrorCode;
import com.sprint.mission.discodeit.constant.MessageErrorCode;
import com.sprint.mission.discodeit.constant.UserErrorCode;
import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateServiceRequest;
import com.sprint.mission.discodeit.dto.message.request.MessageUpdateServiceRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.BinaryContentException;
import com.sprint.mission.discodeit.exception.ChannelException;
import com.sprint.mission.discodeit.exception.MessageException;
import com.sprint.mission.discodeit.exception.UserException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.util.BinaryContentConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

    @Qualifier("fileMessageRepository")
    private final MessageRepository messageRepository;

    @Qualifier("fileChannelRepository")
    private final ChannelRepository channelRepository;

    @Qualifier("fileUserRepository")
    private final UserRepository userRepository;

    @Qualifier("fileBinaryContentRepository")
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public MessageResponse createMessage(MessageCreateServiceRequest request) {

        List<BinaryContent> binaryContents = new ArrayList<>();

        Optional.ofNullable(request.getAttachments())
                .ifPresent(attachments -> attachments.stream()
                        .map(this::getBinaryContent)
                        .forEach(binaryContents::add));

        Message message = request.toEntity(binaryContents);

        Channel findChannel = channelRepository.findChannelById(request.getChannelId())
                .orElseThrow(() -> new ChannelException(ChannelErrorCode.CHANNEL_NOT_FOUND));

        User findUser = userRepository.findUserById(request.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        findChannel.addMessage(message);
        findUser.addMessage(message);

        binaryContents.stream()
                        .forEach(binaryContentRepository::save);

        channelRepository.save(findChannel);
        userRepository.save(findUser);
        messageRepository.save(message);
        return new MessageResponse(message);
    }

    private BinaryContent getBinaryContent(MultipartFile profile) {
        BinaryContent binaryProfile;
        try {
            binaryProfile = BinaryContentConverter.toBinaryContent(profile);
        } catch(IOException e) {
            throw new BinaryContentException(BinaryContentErrorCode.MULTIPART_FILE_CONVERT_FAILED);
        }
        return binaryProfile;
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
                .orElseThrow(() -> new MessageException(MessageErrorCode.MESSAGE_NOT_FOUND));

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
        Optional.ofNullable(request.getContent()).orElseThrow(() -> new MessageException(MessageErrorCode.MESSAGE_NOT_FOUND));

        Message messageToUpdate = messageRepository.findMessageById(request.getMessageId())
               .orElseThrow(() -> new MessageException(MessageErrorCode.MESSAGE_NOT_FOUND));

        User author = userRepository.findUserById(request.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Channel channel = channelRepository.findChannelById(request.getChannelId())
                .orElseThrow(() -> new ChannelException(ChannelErrorCode.CHANNEL_NOT_FOUND));


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
                        () -> {throw new MessageException(MessageErrorCode.MESSAGE_NOT_FOUND);}
                );
    }

    public void editChannelMessageContent(Channel channel, MessageUpdateServiceRequest request) {
        channel.getMessages().stream()
                .filter(channelMessage -> channelMessage.getId().equals(request.getMessageId()))
                .findFirst()
                .ifPresentOrElse(channelMessage -> channelMessage.editContent(request.getContent()),
                        () -> {throw new MessageException(MessageErrorCode.MESSAGE_NOT_FOUND);}
                );
    }

    @Override
    public void deleteMessage(UUID messageId) {

        Message findMessage = messageRepository.findMessageById(messageId)
                .orElseThrow(() -> new MessageException(MessageErrorCode.MESSAGE_NOT_FOUND));

        User author = userRepository.findUserById(findMessage.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Channel channel = channelRepository.findChannelById(findMessage.getChannelId())
                .orElseThrow(() -> new ChannelException(ChannelErrorCode.CHANNEL_NOT_FOUND));

//        if(findMessage.getBinaryContents() != null && !findMessage.getBinaryContents().isEmpty()) {
//            List<BinaryContent> binaryContent = binaryContentRepository.findBinaryContentsByMessageId(messageId);
//            removeBinaryContentsOfMessage(binaryContent);
//        }

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
                        () -> { throw new MessageException(MessageErrorCode.MESSAGE_NOT_FOUND); }
                );
    }

    private void removeMessageFromChannel(Channel channel, UUID messageId) {
        channel.getMessages().stream()
                .filter(channelMessage -> channelMessage.getId().equals(messageId))
                .findFirst()
                .ifPresentOrElse(
                        channel::removeMessage,
                        () -> { throw new MessageException(MessageErrorCode.MESSAGE_NOT_FOUND); }
                );
    }

    private void removeMessage(UUID messageId) {
        messageRepository.findMessageById(messageId)
                .ifPresentOrElse(
                        message -> messageRepository.delete(messageId),
                        () -> { throw new MessageException(MessageErrorCode.MESSAGE_NOT_FOUND); }
                );
    }
}
