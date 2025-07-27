package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.entity.User;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserResponse {

    private final UUID id;
    private final String username;
    private final String email;
    private BinaryContentResponse profile;
    private final boolean online;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        assignBinaryContentResponseIfUserProfilePresent(user);
        this.online = user.isOnline();
    }

    private void assignBinaryContentResponseIfUserProfilePresent(User user) {
        user.getOptionalProfile().ifPresent(binaryContent -> this.profile = new BinaryContentResponse(binaryContent));
    }
}
