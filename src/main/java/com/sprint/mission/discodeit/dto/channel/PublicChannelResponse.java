package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.entity.Channel;
import lombok.Getter;

@Getter
public class PublicChannelResponse extends ChannelResponse {
    private String name;
    private String description;

    public PublicChannelResponse(Channel channel) {
        super(channel);
        this.name = channel.getChannelName();
        this.description = channel.getDescription();
    }

    @Override
    public String toString() {
        return "PublicChannelResponse{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", id=" + id +
                ", lastMessageAt=" + lastMessageAt +
                ", messages=" + messages +
                ", users=" + participantIds +
                '}';
    }
}
