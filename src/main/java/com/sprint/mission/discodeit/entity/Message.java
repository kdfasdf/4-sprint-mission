package com.sprint.mission.discodeit.entity;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Message extends BaseEntity{

    private String content;

    private UUID channelId;

    private UUID userId;

    public Message(String content, UUID channelId, UUID userId) {
        this.content = content;
        this.channelId = channelId;
        this.userId = userId;
    }

    public void editContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", channelId=" + channelId +
                '}';
    }

}
