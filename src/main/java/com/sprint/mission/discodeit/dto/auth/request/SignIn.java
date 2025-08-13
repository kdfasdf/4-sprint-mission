package com.sprint.mission.discodeit.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class SignIn {

    @Length(min = 1, max = 50, message = "사용자이름은 1~50자 이어야 함")
    @NotBlank(message = "사용자이름은 필수입니다.")
    private String username;

    @Length(min = 1, max = 80, message = "비밀번호는 1~60자 이어야 함")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

}
