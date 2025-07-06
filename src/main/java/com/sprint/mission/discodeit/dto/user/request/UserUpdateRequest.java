package com.sprint.mission.discodeit.dto.user.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {

    private final String userName;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않음")
    private final String email;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰 번호 형식이 올바르지 않음.")
    private final String phoneNumber;

    private final String password;

    private final MultipartFile profile;

    public UserUpdateServiceRequest toServiceRequest(UUID userId) {
        return UserUpdateServiceRequest.builder()
                .userId(userId)
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .profile(profile)
                .build();
    }

    public void validate(String userName, String email, String phoneNumber, String password, BinaryContent profile) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("유저 네임은 null이거나 공백이면 안됨");
        }
        if (email == null || email.trim().isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("이메일 형식에 맞지 않음");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty() || !phoneNumber.matches("^[0-9+\\-]+$")) {
            throw new IllegalArgumentException("전화번호 형식에 맞지 않음");
        }
        if (profile != null && !profile.getFileType().getCode().startsWith("1")) {
            throw new IllegalArgumentException("이미지 외의 파일은 프로필 등록 불가");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 null이거나 공백이면 안됨");
        }
    }
}
