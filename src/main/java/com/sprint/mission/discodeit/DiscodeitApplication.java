package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.channel.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponse;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class DiscodeitApplication {

    private static ChannelService channelService;
    private static UserService userService;
    private static MessageService messageService;
    private static ReadStatusService readStatusService;
    private static UserStatusService userStatusService;
    private static BinaryContentService binaryContentService;

    private static UUID firstUserId;
    private static UUID secondUserId;

    private static UUID firstChannelId;
    private static UUID secondChannelId;
    private static UUID thirdChannelId;

    private static UUID firstMessageId;
    private static UUID secondMessageId;

    private static UUID firstReadStatusId;
    private static UUID secondReadStatusId;

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(DiscodeitApplication.class, args);

        log.info("\n===========FileService Test===========\n");
        setUpFileService();
        testCycle();

        log.info("\n===========JCFService Test===========\n");
        setUpJCFService();
        testCycle();
    }

    private static void setUpJCFService() {

        channelService = context.getBean("basicChannelJCFService",ChannelService.class);

        userService = context.getBean("basicUserJCFService", UserService.class);

        messageService = context.getBean("basicMessageJCFService", MessageService.class);

        readStatusService = context.getBean("basicReadStatusJCFService", ReadStatusService.class);

        userStatusService = context.getBean("basicUserStatusJCFService", UserStatusService.class);

        binaryContentService = context.getBean("basicBinaryContentJCFService", BinaryContentService.class);

    }

    private static void setUpFileService() {
        channelService = context.getBean("basicChannelFileService", ChannelService.class);

        userService = context.getBean("basicUserFileService", UserService.class);

        messageService = context.getBean("basicMessageFileService", MessageService.class);

        readStatusService = context.getBean("basicReadStatusFileService", ReadStatusService.class);

        userStatusService = context.getBean("basicUserStatusFileService", UserStatusService.class);

        binaryContentService = context.getBean("basicBinaryContentFileService", BinaryContentService.class);


    }

    private static void testCycle() {
        //User
        // 단순 조회
        testFindUser(userService);

        // 다건 조회
        testFindUsers(userService);

        // 수정
        testUpdateUser(userService);

        // 삭제
        testDeleteUser(userService, secondUserId);

        //Channel
        // 단순 조회
        testFindChannel(channelService);

        // 다건 조회
        testFindPrivateAndPublicChannelsByUserId(channelService);


        // 수정 (이름만 수정)
        testUpdateChannelName(channelService);

        // 수정 (설명만 수정)
        testUpdateChannelDescription(channelService);

        // 수정 (2개 동시 수정)
        testUpdateChannelNameAndDescription(channelService);

        // 삭제
        testDeleteChannel(channelService);

        //Message
        // 단순 조회
        testFindMessage(messageService);

        // 다건 조회
        testFindMessages(messageService);

        // 수정
        testUpdateMessage(messageService);

        // 삭제
        testDeleteMessage(messageService);

        //ReadStatus
        // 단순 조회
        testFindReadStatus(readStatusService);

        // 다건 조회
        testFindReadStatusesFromUser(readStatusService);

        // 수정
        testUpdateReadStatus(readStatusService);

        // 삭제
        testDeleteReadStatus(readStatusService);

        //UserStatus
        // 단순 조회
        testFindUserStatus(userStatusService);

        //수정
        testUpdateUserStatus(userStatusService);

        //BinaryContent
        // 단순 조회
        testFindBinaryContent(binaryContentService);

    }

    private static void testFindUser(UserService userService) {
        UserCreateRequest firstUserCreateRequest = UserCreateRequest.of("firstUser", "first@User.com", "1-1-1", "firstUser");
        userService.createUser(firstUserCreateRequest.toServiceRequest());
        firstUserId = userService.findUsers().get(0).getId();
        log.info("\n단순 조회\n{}\n", userService.findUserById(firstUserId));
    }

    private static void testFindUsers(UserService userService) {
        UserCreateRequest secondUserCreateRequest = UserCreateRequest.of("secondUser", "second@User.com", "2-2-2", "secondUser");
        userService.createUser(secondUserCreateRequest.toServiceRequest());
        secondUserId = userService.findUsers().stream()
                .filter(user -> !user.getId().equals(firstUserId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("하나만 저장된 상태"))
                .getId();

        List<UserResponse> users = userService.findUsers();
        String formattedUsers = formatList(users);
        log.info("\n다건 조회\n{}\n", formattedUsers);
    }

    private static void testUpdateUser(UserService userService) {
        UserUpdateRequest secondUserUpdateRequest = UserUpdateRequest.of(secondUserId,"secondUserUpdate", "second@UserUpdate.com", "5-5-5", "secondUserUpdate");
        UserResponse response = userService.updateUser(secondUserUpdateRequest.toServiceRequest());
        log.info("\n수정\n{}\n", response);
    }

    private static void testDeleteUser(UserService userService, UUID secondUserId) {
        userService.deleteUser(secondUserId);
        log.info("\n삭제\n{}\n", userService.findUsers());
    }

    private static void testFindChannel(ChannelService channelService) {
        ChannelCreateRequest firstChannelCreateRequest = new ChannelCreateRequest("firstChannel", "firstChannel", firstUserId, "CHANNEL-100");
        channelService.createPublicChannel(firstChannelCreateRequest.toServiceRequest());
        firstChannelId = channelService.findAllChannelsByUserId(firstChannelCreateRequest.getHostId()).get(0).getId();
        log.info("\n단순조회\n{}\n", channelService.findChannelById(firstChannelId));
    }

    private static void testFindPrivateAndPublicChannelsByUserId(ChannelService channelService) {
        PrivateChannelCreateRequest secondChannelCreateRequest = new PrivateChannelCreateRequest(firstUserId, "CHANNEL-200");
        channelService.createPrivateChannel(secondChannelCreateRequest.toServiceRequest());
        secondChannelId = channelService.findAllChannelsByUserId(firstUserId).stream()
                .filter(channel -> !channel.getId().equals(firstChannelId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("하나만 저장된 상태"))
                .getId();

        List<ChannelResponse> channels = channelService.findAllChannelsByUserId(firstUserId);
        String formattedChannels = formatList(channels);
        log.info("\n다건 조회\n{}\n", formattedChannels);
    }


    private static void testUpdateChannelName(ChannelService channelService) {
        ChannelUpdateRequest updateRequest = ChannelUpdateRequest.builder()
                .channelId(firstChannelId)
                .channelName("updateName")
                .build();
        ChannelResponse response = channelService.updateChannel(updateRequest.toServiceRequest());
        log.info("\n수정 - 이름필드\n{}\n", response);
    }

    private static void testUpdateChannelDescription(ChannelService channelService) {
        ChannelUpdateRequest updateRequest = ChannelUpdateRequest.builder()
                .channelId(firstChannelId)
                .channelName("updateDescription")
                .build();
        ChannelResponse response = channelService.updateChannel(updateRequest.toServiceRequest());
        log.info("\n수정 - 이름필드\n{}\n", response);
    }

    private static void testUpdateChannelNameAndDescription(ChannelService channelService) {
        ChannelUpdateRequest updateRequest = ChannelUpdateRequest.builder()
                .channelId(firstChannelId)
                .channelName("updateBoth")
                .description("updateBoth")
                .build();
        ChannelResponse response = channelService.updateChannel(updateRequest.toServiceRequest());
        log.info("\n수정\n{}\n", response);
    }

    private static void testDeleteChannel(ChannelService channelService) {
        channelService.deleteChannel(secondChannelId);
        log.info("\n삭제\n{}\n", channelService.findAllChannelsByUserId(firstUserId));
    }

    private static void testFindMessage(MessageService messageService) {
        MessageCreateRequest request = new MessageCreateRequest("firstMessage", firstChannelId, firstUserId, null);
        messageService.createMessage(request.toServiceRequest());
        firstMessageId = messageService.findMessagesByChannelId(firstChannelId).get(0).getMessageId();
        log.info("\n단순 조회\n{}\n", messageService.findMessagesByChannelId(firstChannelId));
    }

    private static void testFindMessages(MessageService messageService) {
        MessageCreateRequest request = new MessageCreateRequest("firstMessage", firstChannelId, firstUserId, null);
        messageService.createMessage(request.toServiceRequest());
        secondMessageId = messageService.findMessagesByChannelId(firstChannelId).get(1).getMessageId();
        List<MessageResponse> response = messageService.findMessagesByChannelId(firstChannelId);
        String formattedMessages = formatList(response);
        log.info("\n다건 조회\n{}\n", formattedMessages);
    }

    private static void testUpdateMessage(MessageService messageService) {
        MessageUpdateRequest updateRequest = MessageUpdateRequest.builder()
                .messageId(firstMessageId)
                .channelId(firstChannelId)
                .userId(firstUserId)
                .content("updatedFirstMessage")
                .build();
        messageService.updateContent(updateRequest.toServiceRequest());
        log.info("\n수정\n{}\n", messageService.findMessageById(firstMessageId));
    }

    private static void testDeleteMessage(MessageService messageService) {
        messageService.deleteMessage(secondMessageId);
        log.info("\n삭제\n{}\n", messageService.findMessagesByChannelId(firstChannelId));
    }

    private static void testFindReadStatus(ReadStatusService readStatusServiceService) {
        firstReadStatusId = readStatusServiceService.findReadStatusByUserIdAndChannelId(firstUserId, firstChannelId).getId();
        log.info("\n단순조회\n{}\n", readStatusServiceService.findReadStatusById(firstReadStatusId));
        List<ReadStatusResponse> response = readStatusServiceService.findAllByUserId(firstUserId);
        String formattedReadStatuses = formatList(response);
        log.info("\n다건조회\n{}\n", formattedReadStatuses);
    }

    private static void testFindReadStatusesFromUser(ReadStatusService readStatusServiceService) {
        ChannelCreateRequest thirdChannelCreateRequest = new ChannelCreateRequest("thirdChannel", "thirdChannel", firstUserId, "CHANNEL-100");
        channelService.createPublicChannel(thirdChannelCreateRequest.toServiceRequest());
        thirdChannelId = channelService.findAllChannelsByUserId(firstUserId).get(1).getId();

        secondReadStatusId = readStatusServiceService.findReadStatusByUserIdAndChannelId(firstUserId, thirdChannelId).getId();

        List<ReadStatusResponse> response = readStatusServiceService.findAllByUserId(firstUserId);
        String formattedReadStatuses = formatList(response);
        log.info("\n다건조회\n{}\n", formattedReadStatuses);
    }

    private static void testUpdateReadStatus(ReadStatusService readStatusServiceService) {
        ReadStatusUpdateRequest updateRequest = new ReadStatusUpdateRequest(firstReadStatusId, firstUserId, firstChannelId);
        readStatusServiceService.updateReadStatus(updateRequest.toServiceRequest());
        log.info("\n수정\n{}\n", readStatusServiceService.findReadStatusById(firstReadStatusId));
    }

    private static void testDeleteReadStatus(ReadStatusService readStatusServiceService) {
        readStatusServiceService.deleteReadStatus(secondReadStatusId);
        log.info("\n삭제\n{}\n", readStatusServiceService.findAllByUserId(firstUserId));
    }

    private static void testFindUserStatus(UserStatusService userStatusService) {
        UserStatusResponse response = userStatusService.findUserStatusByUserId(firstUserId);
        log.info("\n단순조회\n{}\n", response);
    }

    private static void testUpdateUserStatus(UserStatusService userStatusService) {
        UserStatusUpdateRequest updateRequest = new UserStatusUpdateRequest(firstUserId);
        UserStatusResponse response = userStatusService.updateUserStatus(updateRequest.toServiceRequest());
        log.info("\n수정\n{}\n", response);
    }

    private static void testFindBinaryContent(BinaryContentService binaryContentService) {
        UserResponse userResponse = userService.findUserById(firstUserId);
        BinaryContentResponse response = binaryContentService.findById(userResponse.getProfile().getId());
        log.info("\n단순조회\n{}\n", response);
    }

    private static <T> String formatList(List<T> list) {
        return list.stream()
                .map(T::toString)
                .collect(Collectors.joining("\n"));
    }

}
