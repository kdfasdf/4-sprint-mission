package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.NotificationErrorCode;
import com.sprint.mission.discodeit.dto.notification.NotificationDto;
import com.sprint.mission.discodeit.entity.Notification;
import com.sprint.mission.discodeit.exception.NotificationException;
import com.sprint.mission.discodeit.mapper.NotificationMapper;
import com.sprint.mission.discodeit.repository.NotificationRepository;
import com.sprint.mission.discodeit.service.NotificationService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicNotificationService implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    public List<NotificationDto> getNotifications(UUID userId) {
        List<Notification> notifications = notificationRepository.findAllByReceiverId(userId);

        return notifications.stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    public void deleteNotification(UUID notificationId, UUID userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException(NotificationErrorCode.NOTIFICATION_NOT_FOUND));

        if(!notification.getReceiverId().equals(userId)) {
            throw new NotificationException(NotificationErrorCode.NOT_AUTHORIZED);
        }
    }
}
