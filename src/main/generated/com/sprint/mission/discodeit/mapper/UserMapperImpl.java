package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateServiceRequest;
import com.sprint.mission.discodeit.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-27T13:25:07+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserCreateServiceRequest userCreateServiceRequest) {
        if ( userCreateServiceRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userCreateServiceRequest.getUsername() );
        user.email( userCreateServiceRequest.getEmail() );
        user.password( userCreateServiceRequest.getPassword() );

        return user.build();
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        User user1 = null;

        user1 = user;

        UserResponse userResponse = new UserResponse( user1 );

        return userResponse;
    }
}
