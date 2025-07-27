package com.sprint.mission.discodeit.dto.message;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.user.UserResponse;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageResponse {

    private final UUID id;

    private final Instant createdAt;

    private final Instant updatedAt;

    private final String content;

    private final UUID channelId;

    private final UserResponse author;

    private final List<BinaryContentResponse> attachments;


    @Override
    public String toString() {
        return "MessageResponse{" +
                "channelId=" + channelId +
                ", content='" + content + '\'' +
                ", messageId=" + id +
                '}';
    }
}
