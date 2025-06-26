package com.sprint.mission.discodeit.entity;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Message extends BaseEntity{

    private String content;

    private final UUID channelId;

    private final UUID userId;

    private final List<BinaryContent> binaryContents;

    @Builder
    public Message(String content, UUID channelId, UUID userId, List<BinaryContent> binaryContents) {
        this.content = content;
        this.channelId = channelId;
        this.userId = userId;
        this.binaryContents = binaryContents;
    }

    public void editContent(String content) {
        this.content = content;
    }

    public void addBinaryContent(BinaryContent binaryContent) {
        this.binaryContents.add(binaryContent);
    }

    public void removeBinaryContent(BinaryContent binaryContent) {
        this.binaryContents.remove(binaryContent);
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
