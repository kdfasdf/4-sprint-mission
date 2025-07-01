package com.sprint.mission.discodeit.api.controller;

import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateRequest;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserStatusService userStatusService;

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public ResponseEntity<Void> createUser(UserCreateRequest request) {
        userService.createUser(request.toServiceRequest());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    public ResponseEntity<UserResponse> findUser(@RequestParam UUID userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public ResponseEntity<List<UserResponse>> findUsers() {
        return ResponseEntity.ok(userService.findUsers());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{userId}")
    public ResponseEntity<Void> deleteUser(@RequestParam UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/users/{userId}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(request.toServiceRequest()));
    }

}
