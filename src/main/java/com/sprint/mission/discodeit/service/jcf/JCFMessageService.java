package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.MemberStatus;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFMessageService implements MessageService {

    private final List<Message> data;

    private static JCFMessageService jcfMessageService;

    public static synchronized JCFMessageService getInstance() {
        if (jcfMessageService == null) {
            jcfMessageService = new JCFMessageService();
        }
        return jcfMessageService;
    }

    private JCFMessageService() {
        this.data = new ArrayList<>();
    }

    @Override
    public void createMessage(Message message, User user) {
        if(user.getMemberStatus() == MemberStatus.ACTIVE) {
            data.add(message);
        }
    }

    @Override
    public Optional<Message> findMessageById(UUID messageId) {
        return data.stream()
                .filter(message -> message.getId() == messageId)
                .findFirst();
    }

    @Override
    public List<Message> findMessagesByChannelId(UUID channelId) {
        return data.stream()
                .filter(message -> message.getChannelId() == channelId)
                .toList();
    }

    @Override
    public void updateContent(UUID messageId, String content) {
        data.stream()
                .filter(message -> message.getId() == messageId)
                .findFirst()
                .ifPresent(message -> message.editContent(content));
    }

    @Override
    public void deleteMessage(UUID messageId) {
        data.stream()
                .filter(message -> message.getId() == messageId)
                .findFirst()
                .ifPresent(data::remove);
    }

}
