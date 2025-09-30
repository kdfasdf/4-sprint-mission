package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String username;
    private String email;
    private BinaryContentResponse profile;
    private boolean online;
    private List<String> roles;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        assignBinaryContentResponseIfUserProfilePresent(user);
        this.online = user.isOnline();
        this.roles = user.getRoles();
    }

    private void assignBinaryContentResponseIfUserProfilePresent(User user) {
        user.getOptionalProfile().ifPresent(binaryContent -> this.profile = new BinaryContentResponse(binaryContent));
    }
}
