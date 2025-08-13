package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponse;
import com.sprint.mission.discodeit.entity.UserStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-13T13:10:38+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class UserStatusMapperImpl implements UserStatusMapper {

    @Override
    public UserStatusResponse toResponse(UserStatus userStatus) {
        if ( userStatus == null ) {
            return null;
        }

        UserStatus userStatus1 = null;

        userStatus1 = userStatus;

        UserStatusResponse userStatusResponse = new UserStatusResponse( userStatus1 );

        return userStatusResponse;
    }
}
