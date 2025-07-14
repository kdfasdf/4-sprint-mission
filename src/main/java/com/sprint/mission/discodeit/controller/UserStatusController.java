package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponse;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.service.UserStatusService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserStatusController {

    private final UserStatusService userStatusService;

    @PatchMapping("/users/{userId}/userStatus")
    public ResponseEntity<UserStatusResponse> updateUserStatus(@PathVariable("userId") UUID userId, @Valid @RequestBody UserStatusUpdateRequest request) {
        return ResponseEntity.ok().body(userStatusService.updateUserStatus(request.toServiceRequest(userId)));
    }
}
