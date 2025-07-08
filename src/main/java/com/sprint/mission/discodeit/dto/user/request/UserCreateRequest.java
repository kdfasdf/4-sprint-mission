package com.sprint.mission.discodeit.dto.user.request;

import com.sprint.mission.discodeit.entity.BinaryContent;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    private final String userName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final BinaryContent profile;


    public static UserCreateRequest of(String userName, String email, String phoneNumber, String password, BinaryContent profile) {
        return new UserCreateRequest(userName, email, phoneNumber, password, profile);
    }

    public static UserCreateRequest of(String userName, String email, String phoneNumber, String password) {
        return new UserCreateRequest(userName, email, phoneNumber, password, null);
    }

    private UserCreateRequest(String userName, String email, String phoneNumber, String password, BinaryContent profile) {
        validate(userName, email, phoneNumber, password, profile);
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profile = profile;
    }

    public UserCreateServiceRequest toServiceRequest() {
        return UserCreateServiceRequest.builder()
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
