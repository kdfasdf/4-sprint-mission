package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    // 생성
    void createMessage(Message message, User user);

    //조회
    Optional<Message> findMessageById(UUID messageId);

    List<Message> findMessagesByChannelId(UUID channelId);

    // 수정
    void updateContent(UUID messageId, String content);

    // 삭제
    void deleteMessage(UUID messageId);

}
