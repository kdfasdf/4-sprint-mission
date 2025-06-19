package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.util.DataExistenceChecker;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class User extends BaseEntity {

    private final List<Message> messages;
    private final List<Channel> channels;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private ActiveStatus activeStatus;

    public User(String userName, String email, String phoneNumber, String password) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.messages = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.activeStatus = ActiveStatus.ACTIVE;
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
                ", channels=" + this.getChannels() +
                '}';
    }
}
