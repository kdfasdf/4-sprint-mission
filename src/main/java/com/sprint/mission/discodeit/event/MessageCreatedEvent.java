package com.sprint.mission.discodeit.event;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageCreatedEvent {
    private final UUID id;
    private final UUID authorId;
    private final UUID channelId;
    private final String content;
}
