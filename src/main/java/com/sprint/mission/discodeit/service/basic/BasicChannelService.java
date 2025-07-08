package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channel.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelResponse;
import com.sprint.mission.discodeit.dto.channel.PublicChannelResponse;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateServiceRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateServiceRequest;
import com.sprint.mission.discodeit.dto.channel.request.PrivateChannelCreateServiceRequest;
import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

    @Qualifier("fileChannelRepository")
    private final ChannelRepository channelRepository;

    @Qualifier("fileUserRepository")
    private final UserRepository userRepository;

    @Qualifier("fileMessageRepository")
    private final MessageRepository messageRepository;

    @Qualifier("fileReadStatusRepository")
    private final ReadStatusRepository readStatusRepository;

    @Override
    public ChannelResponse createPublicChannel(ChannelCreateServiceRequest request) {

        ChannelType channelType = ChannelType.getChannelTypeByCode(request.getChannelTypeCode());
        Channel channel = request.toEntity(channelType);

        User hostUser = userRepository.findUserById(request.getHostId()).orElseThrow(() -> new IllegalArgumentException("User not found."));

        if(hostUser.getActiveStatus() == ActiveStatus.ACTIVE) {
            ReadStatus readStatus = new ReadStatus(hostUser.getId(), channel.getId());

            hostUser.addReadStatus(readStatus);
            channel.addUserReadStatus(readStatus);

            readStatusRepository.save(readStatus);
            channelRepository.save(channel);
            userRepository.save(hostUser);
        }
        return new PublicChannelResponse(channel);
    }

    @Override
    public ChannelResponse createPrivateChannel(PrivateChannelCreateServiceRequest request) {

        ChannelType channelType = ChannelType.getChannelTypeByCode(request.getChannelTypeCode());
        Channel channel = request.toEntity(channelType);

        User hostUser = userRepository.findUserById(request.getHostId()).orElseThrow(() -> new IllegalArgumentException("User not found."));

        if(hostUser.getActiveStatus() == ActiveStatus.ACTIVE) {
            ReadStatus readStatus = new ReadStatus(hostUser.getId(), channel.getId());

            hostUser.addReadStatus(readStatus);
            channel.addUserReadStatus(readStatus);

            readStatusRepository.save(readStatus);
            channelRepository.save(channel);
            userRepository.save(hostUser);
        }

        return new PrivateChannelResponse(channel);
    }

    @Override
    public ChannelResponse findChannelById(UUID channelId) {

        return channelRepository.findChannelById(channelId)
                .map(channel ->
                        channel.getChannelType().getCode().startsWith("CHANNEL-1") ? new PublicChannelResponse(channel) : new PrivateChannelResponse(channel)
                )
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));

    }

    @Override
    public  List<ChannelResponse> findAllChannelsByUserId(UUID userId) {
        return channelRepository.findChannels()
                .stream()
                .filter(channel -> channel.getReadStatuses().stream().anyMatch(readStatus -> readStatus.getUserId().equals(userId)))
                .map(channel ->
                        channel.getChannelType().getCode().startsWith("CHANNEL-1") ? new PublicChannelResponse(channel) : new PrivateChannelResponse(channel)
                )
                .toList();
    }

    //현재 privateChannel은 수정 불가
    @Override
    public ChannelResponse updateChannel(ChannelUpdateServiceRequest request) {
        Channel channelToUpdate = channelRepository.findChannelById(request.getChannelId())
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));

        Optional.ofNullable(request.getChannelName()).ifPresent(channelToUpdate::editChannelName);
        Optional.ofNullable(request.getDescription()).ifPresent(channelToUpdate::editDescription);

        channelRepository.save(channelToUpdate);

        return new PublicChannelResponse(channelToUpdate);
    }

    @Override
    public void deleteChannel(UUID channelId) {
        messageRepository.deleteAllByChannelId(channelId);
        readStatusRepository.deleteAllByChannelId(channelId);
        channelRepository.delete(channelId);
    }
}

