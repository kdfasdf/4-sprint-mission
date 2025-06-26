package com.sprint.mission.discodeit.dto.user.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateServiceRequest {

    private final UUID userId;

    private final String userName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final BinaryContent profile;
}
