package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BaseEntity{

    private String content;

    /**
     * Channel 객체가 아닌 ChannelId를 필드로 가지도록 설계한 이유인데 이 부분 리뷰 부탁드립니다.
     * -  디스코드 사용 시 메시지 입장에서 Channel의 name 이나 description과 같은 필드가 필요가 없음
     */
    private UUID channelId;

    /**
     * User 객체가 아닌 UserId를 필드로 가지도록 설계한 이유인데 이 부분 리뷰 부탁드립니다.
     * - 현재 User 객체는 getter를 통해 너무 많은 정보를 노출하고 있다고 생각합니다.(password 등)
     * - 메시지 내 프로필을 누를 시 ID, 닉네임 정도의 필드만 가지고 있습니다.
     */
    private UUID userId;

    public Message(String content, UUID channelId, UUID userId) {
        this.content = content;
        this.channelId = channelId;
        this.userId = userId;
    }

    public UUID getMessageId() {
        return this.id;
    }

    public Long getCreatedAt() {
        return this.createdAt;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public String getContent() {
        return content;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setContent(String content) {
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
