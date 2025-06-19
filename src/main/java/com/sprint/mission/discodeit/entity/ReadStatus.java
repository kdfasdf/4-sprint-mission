package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ReadStatus extends BaseEntity{

    private final UUID userId;
    private final UUID channelId;

    private UUID messageId;

    public ReadStatus(UUID userId, UUID channelId, UUID messageId) {
        this.userId = userId;
        this.channelId = channelId;
        this.messageId = messageId;
    }

    // 다른 객체의 UUID가 들어오는 것을 방지하기 위함
    public void updateLastMessageReadStatus(Message message) {
        this.setUpdatedAt();
        this.messageId = message.getId();
    }

}
