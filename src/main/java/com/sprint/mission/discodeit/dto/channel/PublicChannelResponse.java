package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.entity.Channel;
import lombok.Getter;

@Getter
public class PublicChannelResponse extends ChannelResponse {

    private String channelName;
    private String description;

    public PublicChannelResponse(Channel channel) {
        super(channel);
        this.channelName = channel.getChannelName();
        this.description = channel.getDescription();
    }

    @Override
    public String toString() {
        return "PublicChannelResponse{" +
                "channelName='" + channelName + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", id=" + id +
                ", lastMessageAt=" + lastMessageAt +
                ", messages=" + messages +
                ", users=" + users +
                '}';
    }
}
