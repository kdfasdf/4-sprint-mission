package com.sprint.mission.discodeit.dto.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateRequest {

    @JsonProperty("username")
    @NotBlank(message = "유저 네임은 null이거나 공백이면 안됨")
    private String userName;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않음")
    private String email;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰 번호 형식이 올바르지 않음.")
    private String phoneNumber;

    @NotBlank(message = "비밀번호는 null이거나 공백이면 안됨")
    private String password;

    private MultipartFile profile;

    public UserCreateServiceRequest toServiceRequest(MultipartFile profile) {
        return UserCreateServiceRequest.builder()
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .profile(profile)
                .build();
    }
}
