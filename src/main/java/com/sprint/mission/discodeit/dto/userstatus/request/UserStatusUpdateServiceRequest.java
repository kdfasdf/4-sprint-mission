package com.sprint.mission.discodeit.dto.userstatus.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserStatusUpdateServiceRequest {

    private UUID userId;

}
