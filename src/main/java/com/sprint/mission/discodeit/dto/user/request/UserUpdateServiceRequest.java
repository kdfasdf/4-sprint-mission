package com.sprint.mission.discodeit.dto.user.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class UserUpdateServiceRequest {

    private final UUID userId;

    private final String userName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final MultipartFile profile;
}
