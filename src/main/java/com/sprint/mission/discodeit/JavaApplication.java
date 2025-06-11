package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.factory.ServiceFactory;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaApplication {

    private static final Logger log = LoggerFactory.getLogger(JavaApplication.class);
    private static final ServiceFactory serviceFactory = ServiceFactory.getInstance();

    public static void main(String[] args) {
        // User
        UserService userService = serviceFactory.createFileUserService();
        User firstUser = new User("firstUser", "firstUser", "firstUser", "firstUser");
        User secondUser = new User("secondUser", "secondUser", "secondUser", "secondUser");
        User updatedSecondUser = new User("updatedSecondUser", "updatedSecondUser", "updatedSecondUser", "updatedSecondUser");

        // 단순 조회
        testFindUser(firstUser, userService);

        // 다건 조회
        testFindUsers(secondUser, userService);

        // 수정
        testUpdateUser(secondUser, updatedSecondUser, userService);

        // 삭제
        testDeleteUser(userService, secondUser);

        // Channel
        ChannelService channelService = serviceFactory.createFileChannelService();
        Channel firstChannel = Channel.of("firstChannel", "firstChannel", firstUser);
        User thirdUser = new User("thirdUser", "thirdUser", "thirdUser", "thirdUser");
        Channel secondChannel = Channel.of("secondChannel","secondChannel", firstUser);

        // 단순 조회
        testFindChannel(channelService, firstChannel, firstUser);

        // 참가자 추가
        testAddUserToChannel(userService, thirdUser, channelService, firstChannel);

        // 다건 조회
        testFindChannels(channelService, secondChannel, firstUser);

        // 수정 (이름만 수정)
        Channel updatedChannel = Channel.of("updateName",null, firstUser);
        testUpdateChannelName(channelService, secondChannel, updatedChannel);

        // 수정 (설명만 수정)
        updatedChannel = Channel.of(null, "updateDescription", firstUser);
        testUpdateChannelDescription(channelService, secondChannel, updatedChannel);

        // 수정 (2개 동시 수정)
        updatedChannel = Channel.of("updatedBoth", "updateBoth", firstUser);
        testUpdateChannelNameAndDescription(channelService, secondChannel, updatedChannel);

        // 삭제
        testDeleteChannel(channelService, secondChannel);

        //Message
        MessageService messageService = serviceFactory.createFileMessageService();
        Message firstMessage = new Message("firstMessage", firstChannel.getId(), firstUser.getId());
        Message secondMessage = new Message("secondMessage", firstChannel.getId(), firstUser.getId());

        // 단순 조회
        testFindMessage(messageService, firstMessage, firstUser);

        // 다건 조회
        testFindMessages(messageService, secondMessage, firstUser, firstChannel);

        // 수정
        testUpdateMessage(messageService, secondMessage);

        // 삭제
        testDeleteMessage(messageService, secondMessage, firstChannel);

    }

    private static void testFindUser(User user, UserService userService) {
        userService.createUser(user);
        log.info("\n단순 조회\n{}\n", userService.findUserById(user.getId()).get());
    }

    private static void testFindUsers(User user, UserService userService) {
        userService.createUser(user);
        List<User> users = userService.findUsers();
        String formattedUsers = formatList(users);
        log.info("\n다건 조회\n{}\n", formattedUsers);
    }

    private static void testUpdateUser(User user, User updatedUser, UserService userService) {
        userService.updateUser(user.getId(), updatedUser);
        log.info("\n수정\n{}\n", userService.findUserById(user.getId()).get());
    }

    private static void testDeleteUser(UserService userService, User secondUser) {
        userService.deleteUser(secondUser.getId());
        log.info("\n삭제\n{}\n", userService.findUsers());
    }

    private static void testFindChannel(ChannelService channelService, Channel channel, User user) {
        channelService.createChannel(channel, user);
        log.info("\n단순조회\n{}\n", channelService.findChannelById(channel.getId()).get());
    }

    private static void testFindChannels(ChannelService channelService, Channel channel, User user) {
        channelService.createChannel(channel, user);
        List<Channel> channels = channelService.findChannels();
        String formattedChannels = formatList(channels);
        log.info("\n다건 조회\n{}\n", formattedChannels);
    }

    private static void testAddUserToChannel(UserService userService, User user, ChannelService channelService,
                                             Channel channel) {
        userService.createUser(user);
        channelService.addUser(channel.getId(), user);
        log.info("\n참가자 추가 후 채널\n{}\n", channelService.findChannelById(channel.getId()).get());
        log.info("\n채널 참가 후 유저\n{}\n", userService.findUserById(user.getId()).get());
    }

    private static void testUpdateChannelDescription(ChannelService channelService, Channel channel, Channel updatedChannel) {
        channelService.updateChannel(channel.getId(), updatedChannel);
        log.info("\n수정 - 설명필드\n{}\n", channelService.findChannelById(channel.getId()).get());
    }

    private static void testUpdateChannelName(ChannelService channelService, Channel channel, Channel updatedChannel) {
        channelService.updateChannel(channel.getId(), updatedChannel);
        log.info("\n수정 - 이름필드\n{}\n", channelService.findChannelById(channel.getId()).get());
    }

    private static void testUpdateChannelNameAndDescription(ChannelService channelService, Channel channel, Channel updatedChannel) {
        channelService.updateChannel(channel.getId(), updatedChannel);
        log.info("\n수정\n{}\n", channelService.findChannelById(channel.getId()).get());
    }

    private static void testDeleteChannel(ChannelService channelService, Channel channel) {
        channelService.deleteChannel(channel.getId());
        log.info("\n삭제\n{}\n", channelService.findChannels());
    }

    private static void testFindMessage(MessageService messageService, Message message, User user) {
        messageService.createMessage(message, user);
        log.info("\n단순 조회\n{}\n", messageService.findMessageById(message.getMessageId()).get());
    }

    private static void testFindMessages(MessageService messageService, Message message, User user,
                                         Channel channel) {
        messageService.createMessage(message, user);
        List<Message> messages = messageService.findMessagesByChannelId(channel.getId());
        String formattedMessages = formatList(messages);
        log.info("\n다건 조회\n{}\n", formattedMessages);
    }

    private static void testUpdateMessage(MessageService messageService, Message message) {
        messageService.updateContent(message.getMessageId(), "updatedSecondMessage");
        log.info("\n수정\n{}\n", messageService.findMessageById(message.getMessageId()).get());
    }

    private static void testDeleteMessage(MessageService messageService, Message message, Channel channel) {
        messageService.deleteMessage(message.getMessageId());
        log.info("\n삭제\n{}\n", messageService.findMessagesByChannelId(channel.getId()));
    }

    private static <T> String formatList(List<T> list) {
        return list.stream()
                .map(T::toString)
                .collect(Collectors.joining("\n"));
    }


}
