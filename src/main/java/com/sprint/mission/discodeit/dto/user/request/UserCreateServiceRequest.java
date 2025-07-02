package com.sprint.mission.discodeit.dto.user.request;

import com.sprint.mission.discodeit.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class UserCreateServiceRequest {

    private final String userName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final MultipartFile profile;

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
    }
}
