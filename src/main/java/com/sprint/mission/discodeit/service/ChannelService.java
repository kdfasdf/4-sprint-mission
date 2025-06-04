package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {

    // 생성
    void createChannel(Channel channel, User user);

    // 조회
    Optional<Channel> findChannelById(UUID channelId);
    List<Channel> findChannels();

    // 수정
    void updateChannel(UUID channelId, Channel updatedChannel);

    // 삭제
    void deleteChannel(UUID channelId);

    // 유저
    void addUser(UUID channelId, User user);
    void removeUser(UUID channelId, User user);

    // 메시지
    void addMessage(UUID channelId, User user, Message message);
    void removeMessage(UUID channelId, User user, Message message);


}
