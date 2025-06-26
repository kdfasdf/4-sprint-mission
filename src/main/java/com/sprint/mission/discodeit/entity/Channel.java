package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.util.DataExistenceChecker;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Channel extends BaseEntity {

    private final Set<Message> messages;
    private final Set<ReadStatus> readStatuses;
    private final UUID hostId;

    private String channelName;
    private String description;

    private ChannelType channelType;

    @Builder
    private Channel(UUID hostId, String channelName, String description, ChannelType channelType) {
        this.hostId = hostId;
        this.channelName = channelName;
        this.description = description;
        this.readStatuses= new LinkedHashSet<>();
        this.messages = new LinkedHashSet<>();
        this.channelType = channelType;
    }

    public void addUserReadStatus(ReadStatus readStatus) {
        if (!DataExistenceChecker.isExistDataInField(readStatuses, readStatus)) {
            readStatuses.add(readStatus);
        }
    }

    public void removeUserReadStatus(ReadStatus readStatus) {
        readStatuses.remove(readStatus);
    }

    public void addMessage(Message message) {
        if (!DataExistenceChecker.isExistDataInField(messages, message)) {
            messages.add(message);
        }
    }

    public void removeMessage(Message message) {
        if(DataExistenceChecker.isExistDataInField(messages, message)) {
            messages.remove(message);
        }
    }

    public List<String> getMessageContents() {
        return messages.stream().map(Message::getContent).toList();
    }

    private List<String> getUserIds() {
        return readStatuses.stream().map(ReadStatus::getUserId).map(UUID::toString).toList();
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
                ", users=" + this.getUserIds() +
                '}';
    }
}
