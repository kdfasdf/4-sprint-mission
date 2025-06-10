package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.util.DataExistenceChecker;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel extends BaseEntity {

    private final List<Message> messages;
    private final List<User> users;
    private String channelName;
    private String description;

    private Channel(String channelName, String description, User user) {
        this.channelName = channelName;
        this.description = description;
        this.users = new ArrayList<>(List.of(user));
        this.messages = new ArrayList<>();
    }

    /**
     * @param channelName 채널 이름
     * @param description 채널 설명
     * @param user        채널을 만든 유저
     * @return
     */
    public static Channel of(String channelName, String description, User user) {
        return new Channel(channelName, description, user);
    }

    public static Channel of(String channelName, User user) {
        return new Channel(channelName, "", user);
    }

    public void addUser(User user) {
        if (!DataExistenceChecker.isExistDataInField(users, user)) {
            users.add(user);
            user.addChannel(this);
        }
    }

    public void removeUser(User user) {
        if (DataExistenceChecker.isExistDataInField(users, user)) {
            users.remove(user);
            user.removeChannel(this);
        }
    }

    public void addMessage(User user, Message message) {
        if (!DataExistenceChecker.isExistDataInField(messages, message)) {
            messages.add(message);
            user.addMessage(message);
        }
    }

    public void removeMessage(User user, Message message) {
        if(DataExistenceChecker.isExistDataInField(messages, message)) {
            messages.remove(message);
            user.removeMessage(message);
        }
    }

    public List<String> getMessageContents() {
        return messages.stream().map(Message::getContent).toList();
    }

    public List<String> getChannelUsers() {
        return users.stream().map(User::getUserName).toList();
    }

    public UUID getChannelId() {
        return this.id;
    }

    public Long getCreatedAt() {
        return this.createdAt;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getDescription() {
        return description;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void editChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", channelName='" + channelName + '\'' +
                ", description='" + description + '\'' +
                ", messages=" + this.getMessageContents() +
                ", users=" + this.getChannelUsers() +
                '}';
    }
}
