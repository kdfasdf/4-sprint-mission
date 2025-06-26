package com.sprint.mission.discodeit.dto.message;

import com.sprint.mission.discodeit.entity.Message;
import java.util.UUID;
import lombok.Getter;

@Getter
public class MessageResponse {

    private final String content;

    private final UUID channelId;

    private final UUID userId;

    private final UUID messageId;

    public MessageResponse(Message message) {
        this.messageId = message.getId();
        this.content = message.getContent();
        this.channelId = message.getChannelId();
        this.userId = message.getUserId();
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "channelId=" + channelId +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", messageId=" + messageId +
                '}';
    }
}
