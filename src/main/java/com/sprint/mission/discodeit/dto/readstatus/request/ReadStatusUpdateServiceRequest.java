package com.sprint.mission.discodeit.dto.readstatus.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadStatusUpdateServiceRequest {

    private final UUID readStatusId;
    private final UUID userId;
    private final UUID channelId;

}
