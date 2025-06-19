package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {

    private final List<Message> data;

    public JCFMessageRepository() {
        this.data = new ArrayList<>();
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
    public List<Message> findMessages() {
        return data;
    }

    @Override
    public void delete(Message message) {
        data.remove(message);
    }

}
