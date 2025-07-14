package com.sprint.mission.discodeit.dto.userstatus.request;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserStatusUpdateServiceRequest {

    private final UUID userId;
    private final Instant newLastActiveAt;

}
