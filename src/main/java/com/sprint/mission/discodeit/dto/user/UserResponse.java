package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserResponse {

    private final List<MessageResponse> messages;
    private final List<String> channels;
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private String userName;
    private String email;
    private String phoneNumber;
    private ActiveStatus activeStatus;
    private UserStatus userStatus;
    private BinaryContent profile;

    public UserResponse(User user, UserStatus userStatus) {
        this.id = user.getId();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.messages = toMessageResponses(user.getMessages());
        this.channels = toChannelIds(user.getReadStatuses());
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.activeStatus = user.getActiveStatus();
        this.userStatus = userStatus;
        this.profile = user.getProfile();
    }

    private static List<MessageResponse> toMessageResponses(Set<Message> messages) {
        return messages.stream().map(MessageResponse::new).toList();
    }

    private static List<String> toChannelIds(Set<ReadStatus> readStatuses) {
        return readStatuses.stream()
                .map(ReadStatus::getChannelId)
                .map(UUID::toString)
                .toList();
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "activeStatus=" + activeStatus +
                ", messages=" + messages +
                ", channels=" + channels +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}
