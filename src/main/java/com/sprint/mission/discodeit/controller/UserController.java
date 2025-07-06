package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateRequest;
import com.sprint.mission.discodeit.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findUsers() {
        return ResponseEntity.ok(userService.findUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @ModelAttribute UserCreateRequest request) {
        return ResponseEntity.ok(userService.createUser(request.toServiceRequest()));
    }

    @GetMapping( "/{userId}")
    public ResponseEntity<UserResponse> findUser(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") UUID userId, @Valid @ModelAttribute UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(request.toServiceRequest(userId)));
    }

}
