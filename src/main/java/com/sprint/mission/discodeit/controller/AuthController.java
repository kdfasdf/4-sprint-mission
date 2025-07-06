package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.auth.request.SignIn;
import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping( "/login")
    public ResponseEntity<UserResponse> login(@RequestBody SignIn signIn) {
        return ResponseEntity.ok(authService.login(signIn));
    }
}
