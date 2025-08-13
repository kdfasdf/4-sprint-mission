package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateServiceRequest;
import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.MessageAttachment;
import com.sprint.mission.discodeit.entity.User;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-13T13:10:37+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Message toEntity(MessageCreateServiceRequest request, User user, Channel channel) {
        if ( request == null && user == null && channel == null ) {
            return null;
        }

        Message.MessageBuilder message = Message.builder();

        if ( request != null ) {
            message.content( request.getMessage() );
        }
        message.author( user );
        message.channel( channel );

        return message.build();
    }

    @Override
    public MessageResponse toResponse(Message message) {
        if ( message == null ) {
            return null;
        }

        UUID channelId = null;
        List<BinaryContentResponse> attachments = null;
        UUID id = null;
        Instant createdAt = null;
        Instant updatedAt = null;
        String content = null;
        UserResponse author = null;

        channelId = messageChannelId( message );
        attachments = messageAttachmentListToBinaryContentResponseList( message.getAttachments() );
        id = message.getId();
        createdAt = message.getCreatedAt();
        updatedAt = message.getUpdatedAt();
        content = message.getContent();
        author = userMapper.toResponse( message.getAuthor() );

        MessageResponse messageResponse = new MessageResponse( id, createdAt, updatedAt, content, channelId, author, attachments );

        return messageResponse;
    }

    private UUID messageChannelId(Message message) {
        if ( message == null ) {
            return null;
        }
        Channel channel = message.getChannel();
        if ( channel == null ) {
            return null;
        }
        UUID id = channel.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected BinaryContentResponse messageAttachmentToBinaryContentResponse(MessageAttachment messageAttachment) {
        if ( messageAttachment == null ) {
            return null;
        }

        BinaryContent binaryContent = null;

        binaryContent = messageAttachment.getBinaryContent();

        BinaryContentResponse binaryContentResponse = new BinaryContentResponse( binaryContent );

        return binaryContentResponse;
    }

    protected List<BinaryContentResponse> messageAttachmentListToBinaryContentResponseList(List<MessageAttachment> list) {
        if ( list == null ) {
            return null;
        }

        List<BinaryContentResponse> list1 = new ArrayList<BinaryContentResponse>( list.size() );
        for ( MessageAttachment messageAttachment : list ) {
            list1.add( messageAttachmentToBinaryContentResponse( messageAttachment ) );
        }

        return list1;
    }
}
