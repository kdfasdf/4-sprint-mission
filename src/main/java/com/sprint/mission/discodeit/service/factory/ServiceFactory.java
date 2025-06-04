package com.sprint.mission.discodeit.service.factory;

import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

public class ServiceFactory {

    private static ServiceFactory instance;

    private ServiceFactory() {}

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public UserService createUserService() {
        return JCFUserService.getInstance();
    }

    public MessageService createMessageService() {
        return JCFMessageService.getInstance();
    }

    public ChannelService createChannelService() {
        return JCFChannelService.getInstance();
    }

}
