package com.sprint.mission.discodeit.dto.user.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateServiceRequest {

    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private BinaryContent profile;

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
