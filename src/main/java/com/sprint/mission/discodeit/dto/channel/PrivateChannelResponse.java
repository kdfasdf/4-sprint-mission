package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.entity.Channel;
import lombok.Getter;

@Getter
public class PrivateChannelResponse extends ChannelResponse {

    public PrivateChannelResponse(Channel channel) {
        super(channel);
    }

    @Override
    public String toString() {
        return "PrivateChannelResponse{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", lastMessageAt=" + lastMessageAt +
                ", messages=" + messages +
                ", users=" + participantIds +
                '}';
    }
}
