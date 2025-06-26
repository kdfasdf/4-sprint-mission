package com.sprint.mission.discodeit.dto.message.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageUpdateServiceRequest {

    private final UUID messageId;

    private final String content;

    private final UUID channelId;

    private final UUID userId;
}
