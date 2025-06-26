package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface MessageRepository {

    void save(Message message);

    Optional<Message> findMessageById(UUID messageId);

    Set<Message> findMessages();

    void delete(UUID messageId);

    void deleteAllByChannelId(UUID channelId);

}
