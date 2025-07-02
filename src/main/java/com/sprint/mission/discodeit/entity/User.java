package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.util.DataExistenceChecker;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User extends BaseEntity {

    private final Set<Message> messages;
    private final Set<ReadStatus> readStatuses;

    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private ActiveStatus activeStatus;
    private BinaryContent profile;

    @Builder
    public User(String userName, String email, String phoneNumber, String password, BinaryContent profile) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profile = profile;
        this.messages = new LinkedHashSet<>();
        this.readStatuses = new LinkedHashSet<>();
        this.activeStatus = ActiveStatus.ACTIVE;
    }

    public Optional<BinaryContent> getOptionalProfile() {
        return Optional.ofNullable(profile);
    }

    public List<UUID> getChannelIds() {
        return readStatuses.stream().map(ReadStatus::getChannelId).toList();
    }

    public List<String> getMessageContents() {
        return messages.stream().map(Message::getContent).toList();
    }

    public void addReadStatus(ReadStatus readStatus) {
        if(!DataExistenceChecker.isExistDataInField(readStatuses, readStatus)) {
            readStatuses.add(readStatus);
        }
    }

    public void removeReadStatus(ReadStatus readStatus) {
        readStatuses.remove(readStatus);
    }

    public void addMessage(Message message) {
        if(!DataExistenceChecker.isExistDataInField(messages, message)) {
            messages.add(message);
        }
    }

    public void removeMessage(Message message) {
        if(DataExistenceChecker.isExistDataInField(messages, message)) {
            messages.remove(message);
        }
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfile(BinaryContent profile) {
        this.profile = profile;
    }

    public void updateActiveStatus(ActiveStatus activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void updateUserStatus() {
        this.activeStatus = this.activeStatus == ActiveStatus.ACTIVE ? ActiveStatus.DORMANT : ActiveStatus.ACTIVE;
    }

    public void editMemberStatus(ActiveStatus activeStatus) {
        this.activeStatus = activeStatus;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", messages=" + this.getMessages() +
                ", channels=" + this.getChannelIds() +
                '}';
    }
}
