package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {

    private final Set<Message> data;

    public JCFMessageRepository() {
        this.data = new LinkedHashSet<>();
    }

    @Override
    public void save(Message message) {
        data.add(message);
    }

    @Override
    public Optional<Message> findMessageById(UUID messageId) {
        return data.stream()
                .filter(message -> message.getId() == messageId)
                .findFirst();
    }

    @Override
    public Set<Message> findMessages() {
        return data;
    }

    @Override
    public void delete(UUID messageId) {
        data.removeIf(message -> message.getId() == messageId);
    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        data.removeIf(message -> message.getChannelId().equals(channelId));
    }
}
