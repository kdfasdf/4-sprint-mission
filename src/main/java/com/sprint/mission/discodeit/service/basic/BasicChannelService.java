package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.ChannelErrorCode;
import com.sprint.mission.discodeit.dto.channel.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelResponse;
import com.sprint.mission.discodeit.dto.channel.PublicChannelResponse;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateServiceRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateServiceRequest;
import com.sprint.mission.discodeit.dto.channel.request.PrivateChannelCreateServiceRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.exception.ChannelException;
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

//        ChannelType channelType = ChannelType.getChannelTypeByCode(request.getChannelTypeCode());
        Channel channel = request.toEntity(ChannelType.PUBLIC);

//        User hostUser = userRepository.findUserById(request.getHostId()).orElseThrow(() -> new IllegalArgumentException("User not found."));
//
//        if(hostUser.getActiveStatus() == ActiveStatus.ACTIVE) {
//            ReadStatus readStatus = new ReadStatus(hostUser.getId(), channel.getId());
//
//            hostUser.addReadStatus(readStatus);
//            channel.addUserReadStatus(readStatus);
//
//            readStatusRepository.save(readStatus);
//            channelRepository.save(channel);
//            userRepository.save(hostUser);
//        }
        channelRepository.save(channel);
        return new PublicChannelResponse(channel);
    }

    @Override
    public ChannelResponse createPrivateChannel(PrivateChannelCreateServiceRequest request) {

//        ChannelType channelType = ChannelType.getChannelTypeByCode(request.getChannelTypeCode());
        Channel channel = request.toEntity(ChannelType.PRIVATE);

//        User hostUser = userRepository.findUserById(request.getHostId()).orElseThrow(() -> new IllegalArgumentException("User not found."));
//
//        if(hostUser.getActiveStatus() == ActiveStatus.ACTIVE) {
//            ReadStatus readStatus = new ReadStatus(hostUser.getId(), channel.getId());
//
//            hostUser.addReadStatus(readStatus);
//            channel.addUserReadStatus(readStatus);
//
//            readStatusRepository.save(readStatus);
//            channelRepository.save(channel);
//            userRepository.save(hostUser);
//        }

        request.getParticipantIds().stream()
                .map(userId -> new ReadStatus(userId, channel.getId()))
                .forEach(readStatus -> {
                    channel.addUserReadStatus(readStatus);
                    readStatusRepository.save(readStatus);
                });

        channelRepository.save(channel);
        return new PrivateChannelResponse(channel);
    }

    @Override
    public ChannelResponse findChannelById(UUID channelId) {

        return channelRepository.findChannelById(channelId)
                .map(channel ->
                        channel.getChannelType() == ChannelType.PUBLIC ? new PublicChannelResponse(channel) : new PrivateChannelResponse(channel)
                )
                .orElseThrow(() -> new ChannelException(ChannelErrorCode.CHANNEL_NOT_FOUND));

    }

    @Override
    public  List<ChannelResponse> findAllChannelsByUserId(UUID userId) {
        List<UUID> joinChannels = readStatusRepository.findAllByUserId(userId)
                .stream()
                .map(ReadStatus::getChannelId)
                .toList();

        return channelRepository.findChannels()
                .stream()
                .filter(channel -> joinChannels.contains(channel.getId())|| channel.getChannelType() == ChannelType.PUBLIC)
                .map(channel ->
                        channel.getChannelType() == ChannelType.PUBLIC ? new PublicChannelResponse(channel) : new PrivateChannelResponse(channel)
                )
                .toList();
    }

    //현재 privateChannel은 수정 불가
    @Override
    public ChannelResponse updateChannel(ChannelUpdateServiceRequest request) {
        Channel channelToUpdate = channelRepository.findChannelById(request.getChannelId())
                .orElseThrow(() -> new IllegalArgumentException("Channel not found."));

        Optional.ofNullable(request.getName()).ifPresent(channelToUpdate::editChannelName);
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

