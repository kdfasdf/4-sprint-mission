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
    private String newPassword;

    public UserUpdateServiceRequest toServiceRequest(UUID userId, MultipartFile profile) {
        return UserUpdateServiceRequest.builder()
                .userId(userId)
                .newUsername(newUsername)
                .newEmail(newEmail)
                .newPassword(newPassword)
                .profile(profile)
                .build();
    }
}
