package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.util.DataExistenceChecker;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends BaseEntity {

    private final List<Message> messages;
    private final List<Channel> channels;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private MemberStatus memberStatus;

    public User(String userName, String email, String phoneNumber, String password) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.messages = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.memberStatus = MemberStatus.ACTIVE;
    }

    public List<String> getChannelNames() {
        return channels.stream().map(Channel::getChannelName).toList();
    }

    public List<String> getMessageContents() {
        return messages.stream().map(Message::getContent).toList();
    }

    public void addChannel(Channel channel) {
        if(!DataExistenceChecker.isExistDataInField(channels, channel)) {
            channels.add(channel);
            channel.addUser(this);
        }
    }

    public void removeChannel(Channel channel) {
        if(DataExistenceChecker.isExistDataInField(channels, channel)) {
            channels.remove(channel);
            channel.removeUser(this);
        }
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

    public UUID getId() {
        return this.id;
    }

    public Long getCreatedAt() {
        return this.createdAt;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getEmail() {
        return email;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    public void editMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
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
                ", channels=" + this.getChannels() +
                '}';
    }
}
