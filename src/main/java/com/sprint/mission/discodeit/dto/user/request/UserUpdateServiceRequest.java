package com.sprint.mission.discodeit.dto.user.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateServiceRequest {

    private final UUID userId;

    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private BinaryContent profile;

}
