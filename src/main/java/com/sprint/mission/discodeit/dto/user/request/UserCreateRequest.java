package com.sprint.mission.discodeit.dto.user.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "유저 네임은 null이거나 공백이면 안됨")
    private String userName;

    @Email(message = "이메일 형식이 올바르지 않음")
    private String email;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰 번호 형식이 올바르지 않음.")
    private String phoneNumber;

    @NotBlank(message = "비밀번호는 null이거나 공백이면 안됨")
    private String password;

    private BinaryContent profile;

    public UserCreateServiceRequest toServiceRequest() {
        return UserCreateServiceRequest.builder()
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .profile(profile)
                .build();
    }
}
