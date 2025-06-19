package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserRepository jcfUserRepository() {
        return new JCFUserRepository();
    }

    @Bean
    public ChannelRepository jcfChannelRepository() {
        return new JCFChannelRepository();
    }

    @Bean
    public MessageRepository jcfMessageRepository() {
        return new JCFMessageRepository();
    }

    @Bean
    public UserRepository fileUserRepository() {
        return new FileUserRepository();
    }

    @Bean
    public ChannelRepository fileChannelRepository() {
        return new FileChannelRepository();
    }

    @Bean
    public MessageRepository fileMessageRepository() {
        return new FileMessageRepository();
    }

    @Bean
    public UserService basicUserFileService() {
        return new BasicUserService(fileUserRepository());
    }

    @Bean
    public UserService basicUserJCFService() {
        return new BasicUserService(jcfUserRepository());
    }

    @Bean
    public ChannelService basicChannelFileService() {
        return new BasicChannelService(fileChannelRepository(), fileUserRepository(), fileMessageRepository());
    }

    @Bean
    public ChannelService basicChannelJCFService() {
        return new BasicChannelService(jcfChannelRepository(), jcfUserRepository(), jcfMessageRepository());
    }

    @Bean
    public MessageService basicMessageFileService() {
        return new BasicMessageService(fileMessageRepository());
    }

    @Bean
    public MessageService basicMessageJCFService() {
        return new BasicMessageService(jcfMessageRepository());
    }

}
