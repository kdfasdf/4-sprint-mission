package com.sprint.mission.discodeit.dto.channel;


import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ChannelResponse {
    protected final List<MessageResponse> messages;
    protected final List<UUID> users;
    protected final UUID id;
    protected final Instant createdAt;
    protected final Instant lastMessageAt;

    protected ChannelResponse(Channel channel) {
        this.id = channel.getId();
        this.createdAt = channel.getCreatedAt();
        this.lastMessageAt = getLastMessageAt(channel.getMessages());
        this.messages = toMessageResponses(channel.getMessages());
        this.users = toUserIds(channel.getReadStatuses());
    }

    protected static List<MessageResponse> toMessageResponses(Set<Message> messages) {
        return messages.stream().map(MessageResponse::new).toList();
    }

    protected static List<UUID> toUserIds(Set<ReadStatus> readStatuses) {
        return readStatuses.stream().map(ReadStatus::getUserId).toList();
    }

    protected static Instant getLastMessageAt(Set<Message> messages) {
        return messages.stream().map(Message::getCreatedAt).max(Instant::compareTo).orElse(null);
    }
}
