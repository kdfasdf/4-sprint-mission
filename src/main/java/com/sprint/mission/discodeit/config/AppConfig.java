package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileBinaryContentRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileReadStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.file.FileUserStatusRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFBinaryContentRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFReadStatusRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserStatusRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import com.sprint.mission.discodeit.service.basic.BasicBinaryContentService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicReadStatusService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.basic.BasicUserStatusService;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public UserStatusRepository jcfUserStatusRepository() {
        return new JCFUserStatusRepository();
    }

    @Bean
    public BinaryContentRepository jcfBinaryContentRepository() {
        return new JCFBinaryContentRepository();
    }

    @Bean
    public ReadStatusRepository jcfReadStatusRepository() {
        return new JCFReadStatusRepository();
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
    public UserStatusRepository fileUserStatusRepository() {
        return new FileUserStatusRepository();
    }

    @Bean
    public BinaryContentRepository fileBinaryContentRepository() {
        return new FileBinaryContentRepository();
    }

    @Bean
    public ReadStatusRepository fileReadStatusRepository() {
        return new FileReadStatusRepository();
    }

    @Bean
    public UserService basicUserFileService(
            @Qualifier("fileUserRepository") UserRepository fileUserRepository,
            @Qualifier("fileUserStatusRepository") UserStatusRepository fileUserStatusRepository,
            @Qualifier("fileBinaryContentRepository") BinaryContentRepository fileBinaryContentRepository) {
        return new BasicUserService(fileUserRepository, fileUserStatusRepository, fileBinaryContentRepository);
    }

    @Bean
    public UserService basicUserJCFService(
            @Qualifier("jcfUserRepository") UserRepository jcfUserRepository,
            @Qualifier("jcfUserStatusRepository") UserStatusRepository jcfUserStatusRepository,
            @Qualifier("jcfBinaryContentRepository") BinaryContentRepository jcfBinaryContentRepository) {
        return new BasicUserService(jcfUserRepository, jcfUserStatusRepository, jcfBinaryContentRepository);
    }

    @Bean
    public ChannelService basicChannelFileService(
            @Qualifier("fileChannelRepository") ChannelRepository fileChannelRepository,
            @Qualifier("fileUserRepository") UserRepository fileUserRepository,
            @Qualifier("fileMessageRepository") MessageRepository fileMessageRepository,
            @Qualifier("fileReadStatusRepository") ReadStatusRepository fileReadStatusRepository) {
        return new BasicChannelService(fileChannelRepository, fileUserRepository, fileMessageRepository, fileReadStatusRepository);
    }

    @Bean
    public ChannelService basicChannelJCFService(
            @Qualifier("jcfChannelRepository") ChannelRepository jcfChannelRepository,
            @Qualifier("jcfUserRepository") UserRepository jcfUserRepository,
            @Qualifier("jcfMessageRepository") MessageRepository jcfMessageRepository,
            @Qualifier("jcfReadStatusRepository") ReadStatusRepository jcfReadStatusRepository) {
        return new BasicChannelService(jcfChannelRepository, jcfUserRepository, jcfMessageRepository , jcfReadStatusRepository);
    }

    @Bean
    public MessageService basicMessageFileService(
            @Qualifier("fileMessageRepository") MessageRepository fileMessageRepository,
            @Qualifier("fileChannelRepository") ChannelRepository fileChannelRepository,
            @Qualifier("fileUserRepository") UserRepository fileUserRepository,
            @Qualifier("fileBinaryContentRepository") BinaryContentRepository fileBinaryContentRepository) {
        return new BasicMessageService(fileMessageRepository, fileChannelRepository, fileUserRepository, fileBinaryContentRepository);
    }

    @Bean
    public MessageService basicMessageJCFService(
            @Qualifier("jcfMessageRepository") MessageRepository jcfMessageRepository,
            @Qualifier("jcfChannelRepository") ChannelRepository jcfChannelRepository,
            @Qualifier("jcfUserRepository") UserRepository jcfUserRepository,
            @Qualifier("jcfBinaryContentRepository") BinaryContentRepository jcfBinaryContentRepository) {
        return new BasicMessageService(jcfMessageRepository, jcfChannelRepository, jcfUserRepository, jcfBinaryContentRepository);
    }

    @Bean
    public UserStatusService basicUserStatusFileService(
            @Qualifier("fileUserStatusRepository") UserStatusRepository fileUserStatusRepository,
            @Qualifier("fileUserRepository") UserRepository fileUserRepository) {
        return new BasicUserStatusService(fileUserStatusRepository, fileUserRepository);
    }

    @Bean
    public UserStatusService basicUserStatusJCFService(
            @Qualifier("jcfUserStatusRepository") UserStatusRepository jcfUserStatusRepository,
            @Qualifier("jcfUserRepository") UserRepository jcfUserRepository) {
        return new BasicUserStatusService(jcfUserStatusRepository, jcfUserRepository);
    }

    @Bean
    public ReadStatusService basicReadStatusFileService(
            @Qualifier("fileReadStatusRepository") ReadStatusRepository fileReadStatusRepository,
            @Qualifier("fileChannelRepository") ChannelRepository fileChannelRepository,
            @Qualifier("fileUserRepository") UserRepository fileUserRepository) {
        return new BasicReadStatusService(fileReadStatusRepository, fileChannelRepository, fileUserRepository);
    }

    @Bean
    public ReadStatusService basicReadStatusJCFService(
            @Qualifier("jcfReadStatusRepository") ReadStatusRepository jcfReadStatusRepository,
            @Qualifier("jcfChannelRepository") ChannelRepository jcfChannelRepository,
            @Qualifier("jcfUserRepository") UserRepository jcfUserRepository) {
        return new BasicReadStatusService(jcfReadStatusRepository, jcfChannelRepository, jcfUserRepository);
    }

    @Bean
    public BinaryContentService basicBinaryContentFileService(
            @Qualifier("fileBinaryContentRepository") BinaryContentRepository fileBinaryContentRepository) {
        return new BasicBinaryContentService(fileBinaryContentRepository);
    }

    @Bean
    public BinaryContentService basicBinaryContentJCFService(
            @Qualifier("jcfBinaryContentRepository") BinaryContentRepository jcfBinaryContentRepository) {
        return new BasicBinaryContentService(jcfBinaryContentRepository);
    }
}
