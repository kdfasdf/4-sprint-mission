package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusCreateServiceRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-25T18:31:04+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class ReadStatusMapperImpl implements ReadStatusMapper {

    @Override
    public ReadStatus toEntity(ReadStatusCreateServiceRequest readStatusCreateServiceRequest, User user, Channel channel) {
        if ( readStatusCreateServiceRequest == null && user == null && channel == null ) {
            return null;
        }

        ReadStatus.ReadStatusBuilder readStatus = ReadStatus.builder();

        readStatus.user( user );
        readStatus.channel( channel );

        return readStatus.build();
    }

    @Override
    public ReadStatusResponse toResponse(ReadStatus readStatus) {
        if ( readStatus == null ) {
            return null;
        }

        ReadStatus readStatus1 = null;

        readStatus1 = readStatus;

        ReadStatusResponse readStatusResponse = new ReadStatusResponse( readStatus1 );

        return readStatusResponse;
    }
}
