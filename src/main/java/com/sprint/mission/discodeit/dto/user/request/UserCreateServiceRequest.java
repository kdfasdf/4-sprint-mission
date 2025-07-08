package com.sprint.mission.discodeit.dto.user.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateServiceRequest {

    private final String userName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final BinaryContent profile;

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .profile(profile)
                .build();
    }
}
