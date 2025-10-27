package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.notification.NotificationDto;
import com.sprint.mission.discodeit.entity.Notification;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-27T11:06:34+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationDto toDto(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationDto.NotificationDtoBuilder notificationDto = NotificationDto.builder();

        notificationDto.id( notification.getId() );
        notificationDto.receiverId( notification.getReceiverId() );
        notificationDto.title( notification.getTitle() );
        notificationDto.content( notification.getContent() );
        notificationDto.createdAt( notification.getCreatedAt() );

        return notificationDto.build();
    }
}
