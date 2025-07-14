package com.sprint.mission.discodeit.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateRequest {

    private String newUsername;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않음")
    private String newEmail;

//    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰 번호 형식이 올바르지 않음.")
//    private String phoneNumber;

    private String newPassword;

    public UserUpdateServiceRequest toServiceRequest(UUID userId, MultipartFile profile) {
        return UserUpdateServiceRequest.builder()
                .userId(userId)
                .newUsername(newUsername)
                .newEmail(newEmail)
//                .phoneNumber(phoneNumber)
                .newPassword(newPassword)
                .profile(profile)
                .build();
    }
}
