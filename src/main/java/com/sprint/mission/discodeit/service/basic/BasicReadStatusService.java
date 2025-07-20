package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.constant.ChannelErrorCode;
import com.sprint.mission.discodeit.constant.ReadStatusErrorCode;
import com.sprint.mission.discodeit.constant.UserErrorCode;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusCreateServiceRequest;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusUpdateServiceRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.ChannelException;
import com.sprint.mission.discodeit.exception.ReadStatusException;
import com.sprint.mission.discodeit.exception.UserException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {

    @Qualifier("fileReadStatusRepository")
    private final ReadStatusRepository readStatusRepository;

    @Qualifier("fileChannelRepository")
    private final ChannelRepository channelRepository;

    @Qualifier("fileUserRepository")
    private final UserRepository userRepository;

    @Override
    public ReadStatusResponse createReadStatus(ReadStatusCreateServiceRequest request) {
        Channel channel = channelRepository.findChannelById(request.getChannelId())
                .orElseThrow(() -> new ChannelException(ChannelErrorCode.CHANNEL_NOT_FOUND));

        User user = userRepository.findUserById(request.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        ReadStatus readStatus = readStatusRepository.findReadStatusByUserIdAndChannelId(request.getUserId(), request.getChannelId())
                .orElseThrow(() -> new ReadStatusException(ReadStatusErrorCode.READ_STATUS_NOT_FOUND));

        readStatus.updateLastReadAt();

        channel.addUserReadStatus(readStatus);
        user.addReadStatus(readStatus);

        userRepository.save(user);
        channelRepository.save(channel);
        readStatusRepository.save(readStatus);

        return new ReadStatusResponse(readStatus);
    }

    @Override
    public ReadStatusResponse findReadStatusById(UUID userId) {
        return readStatusRepository.findReadStatusById(userId)
                .map(ReadStatusResponse::new)
                .orElseThrow(() -> new ReadStatusException(ReadStatusErrorCode.READ_STATUS_NOT_FOUND));
    }

    @Override
    public ReadStatusResponse findReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {
        return readStatusRepository.findReadStatusByUserIdAndChannelId(userId, channelId)
                .map(ReadStatusResponse::new)
                .orElseThrow(() -> new ReadStatusException(ReadStatusErrorCode.READ_STATUS_NOT_FOUND));
    }

    @Override
    public List<ReadStatusResponse> findAllByUserId(UUID userId) {
        return readStatusRepository.findAllByUserId(userId)
                .stream()
                .map(ReadStatusResponse::new)
                .toList();
    }

    @Override
    public ReadStatusResponse updateReadStatus(ReadStatusUpdateServiceRequest request) {

        ReadStatus readStatusToUpdate = readStatusRepository.findReadStatusById(request.getReadStatusId())
                .orElseThrow(() -> new ReadStatusException(ReadStatusErrorCode.READ_STATUS_NOT_FOUND));

        readStatusToUpdate.updateLastReadAt();

        readStatusRepository.save(readStatusToUpdate);

        return new ReadStatusResponse(readStatusToUpdate);

    }

    @Override
    public void deleteReadStatus(UUID readStatusId) {
        ReadStatus readStatus = readStatusRepository.findReadStatusById(readStatusId)
                .orElseThrow(() -> new ReadStatusException(ReadStatusErrorCode.READ_STATUS_NOT_FOUND));

        Channel channel = channelRepository.findChannelById(readStatus.getChannelId())
                .orElseThrow(() -> new ChannelException(ChannelErrorCode.CHANNEL_NOT_FOUND));

        User user = userRepository.findUserById(readStatus.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        removeReadStatusesFromChannel(channel, readStatusId);
        removeReadStatusesFromUser(user, readStatusId);

        channelRepository.save(channel);
        userRepository.save(user);
        readStatusRepository.deleteById(readStatusId);
    }

    private void removeReadStatusesFromChannel(Channel channel, UUID readStatusId) {
        channel.getReadStatuses().stream()
                .filter(rs -> rs.getId().equals(readStatusId))
                .findFirst()
                .ifPresentOrElse(
                        channel::removeUserReadStatus,
                        () -> {throw new ReadStatusException(ReadStatusErrorCode.READ_STATUS_NOT_FOUND);}
                );
    }

    private void removeReadStatusesFromUser(User user, UUID readStatusId) {
        user.getReadStatuses().stream()
                .filter(rs -> rs.getId().equals(readStatusId))
                .findFirst()
                .ifPresentOrElse(
                        user::removeReadStatus,
                        () -> {throw new ReadStatusException(ReadStatusErrorCode.READ_STATUS_NOT_FOUND);}
                );
    }
}
