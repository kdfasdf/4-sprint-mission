package com.sprint.mission.discodeit.dto.user.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private final UUID userId;

    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private BinaryContent profile;

    public static UserUpdateRequest of(UUID userId, String userName, String email, String phoneNumber, String password, BinaryContent profile) {
        return new UserUpdateRequest(userId, userName, email, phoneNumber, password, profile);
    }

    public static UserUpdateRequest of(UUID userId, String userName, String email, String phoneNumber, String password) {
        return new UserUpdateRequest(userId, userName, email, phoneNumber, password, null);
    }

    private UserUpdateRequest(UUID userId, String userName, String email, String phoneNumber, String password, BinaryContent profile) {
        validate(userId, userName, email, phoneNumber, password, profile);
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profile = profile;
    }

    public UserUpdateServiceRequest toServiceRequest() {
        return UserUpdateServiceRequest.builder()
                .userId(userId)
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .profile(profile)
                .build();
    }

    public void validate(UUID userId, String userName, String email, String phoneNumber, String password, BinaryContent profile) {
        if (userId == null) {
            throw new IllegalArgumentException("유저는 null이면 안됨");
        }
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
