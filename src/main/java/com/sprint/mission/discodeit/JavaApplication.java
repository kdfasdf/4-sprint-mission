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
        // 단순 조회
        UserService userService = serviceFactory.createUserService();
        User firstUser = new User("firstUser", "firstUser", "firstUser", "firstUser");
        userService.createUser(firstUser);
        log.info("\n단순 조회\n{}\n", userService.findUserById(firstUser.getId()).get());

        // 다건 조회
        User secondUser = new User("secondUser", "secondUser", "secondUser", "secondUser");
        userService.createUser(secondUser);
        List<User> users = userService.findUsers();
        String formattedUsers = formatList(users);
        log.info("\n다건 조회\n{}\n", formattedUsers);

        // 수정
        User updatedSecondUser = new User("updatedSecondUser", "updatedSecondUser", "updatedSecondUser", "updatedSecondUser");
        userService.updateUser(secondUser.getId(), updatedSecondUser);
        log.info("\n수정\n{}\n", userService.findUserById(secondUser.getId()).get());

        // 삭제
        userService.deleteUser(secondUser.getId());
        log.info("\n삭제\n{}\n", userService.findUsers());

        // Channel
        // 단순 조회
        ChannelService channelService = serviceFactory.createChannelService();
        Channel firstChannel = Channel.of("firstChannel", "firstChannel", firstUser);
        channelService.createChannel(firstChannel, firstUser);
        log.info("\n단순조회\n{}\n", channelService.findChannelById(firstChannel.getId()).get());

        User thirdUser = new User("thirdUser", "thirdUser", "thirdUser", "thirdUser");
        userService.createUser(thirdUser);
        channelService.addUser(firstChannel.getId(), thirdUser);
        log.info("\n참가자 추가 후 채널\n{}\n", channelService.findChannelById(firstChannel.getId()).get());
        log.info("\n채널 참가 후 유저\n{}\n", userService.findUserById(thirdUser.getId()).get());


        // 다건 조회
        Channel secondChannel = Channel.of("secondChannel","secondChannel", firstUser);
        channelService.createChannel(secondChannel, firstUser);
        List<Channel> channels = channelService.findChannels();
        String formattedChannels = formatList(channels);
        log.info("\n다건 조회\n{}\n", formattedChannels);

        // 수정 (이름만 수정)
        Channel updatedChannel = Channel.of("updateName",null, firstUser);
        channelService.updateChannel(secondChannel.getId(), updatedChannel);
        log.info("\n수정 - 이름필드\n{}\n", channelService.findChannelById(secondChannel.getId()).get());

        // 수정 (설명만 수정)
        updatedChannel = Channel.of(null, "updateDescription", firstUser);
        channelService.updateChannel(secondChannel.getId(), updatedChannel);
        log.info("\n수정 - 설명필드\n{}\n", channelService.findChannelById(secondChannel.getId()).get());

        // 수정 (2개 동시 수정)
        updatedChannel = Channel.of("updatedBoth", "updateBoth", firstUser);
        channelService.updateChannel(secondChannel.getId(), updatedChannel);
        log.info("\n수정\n{}\n", channelService.findChannelById(secondChannel.getId()).get());

        // 삭제
        channelService.deleteChannel(secondChannel.getId());
        log.info("\n삭제\n{}\n", channelService.findChannels());

        //Message
        // 단순 조회
        MessageService messageService = serviceFactory.createMessageService();
        Message firstMessage = new Message("firstMessage", firstChannel.getId(), firstUser.getId());
        messageService.createMessage(firstMessage, firstUser);
        log.info("\n단순 조회\n{}\n", messageService.findMessageById(firstMessage.getMessageId()).get());

        // 다건 조회
        Message secondMessage = new Message("secondMessage", firstChannel.getId(), firstUser.getId());
        messageService.createMessage(secondMessage, firstUser);
        List<Message> messages = messageService.findMessagesByChannelId(firstChannel.getId());
        String formattedMessages = formatList(messages);
        log.info("\n다건 조회\n{}\n", formattedMessages);

        // 수정
        messageService.updateContent(secondMessage.getMessageId(), "updatedSecondMessage");
        log.info("\n수정\n{}\n", messageService.findMessageById(secondMessage.getMessageId()).get());

        // 삭제
        messageService.deleteMessage(secondMessage.getMessageId());
        log.info("\n삭제\n{}\n", messageService.findMessagesByChannelId(firstChannel.getId()));

    }

    private static <T> String formatList(List<T> list) {
        return list.stream()
                .map(T::toString)
                .collect(Collectors.joining("\n"));
    }


}
