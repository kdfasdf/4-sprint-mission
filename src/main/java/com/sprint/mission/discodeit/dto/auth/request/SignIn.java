package com.sprint.mission.discodeit.dto.auth.request;

import lombok.Getter;

@Getter
public class SignIn {

    private String email;

    private String password;

    public SignIn(String email, String password) {
        validate(email, password);
        this.email = email;
        this.password = password;
    }

    public void validate(String email, String password) {
        if (email == null || email.trim().isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("이메일 형식에 맞지 않음");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 null이거나 공백이면 안됨");
        }
    }
}
