package com.sprint.mission.discodeit.dto.message;

import com.sprint.mission.discodeit.entity.Message;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class MessageResponse {

    private final UUID id;

    private final String content;

    private final UUID channelId;

    private final UUID authorId;

    private final Instant createdAt;

    private final List<UUID> attachmentIds;

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.channelId = message.getChannelId();
        this.authorId = message.getUserId();
        this.attachmentIds = message.getBinaryContents().stream().map(binaryContent -> binaryContent.getId()).toList();
        this.createdAt = message.getCreatedAt();
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "channelId=" + channelId +
                ", content='" + content + '\'' +
                ", userId=" + authorId +
                ", messageId=" + id +
                '}';
    }
}
