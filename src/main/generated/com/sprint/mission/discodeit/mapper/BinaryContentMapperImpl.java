package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-27T13:25:07+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class BinaryContentMapperImpl implements BinaryContentMapper {

    @Override
    public BinaryContentResponse toResponse(BinaryContent binaryContent) {
        if ( binaryContent == null ) {
            return null;
        }

        BinaryContent binaryContent1 = null;

        binaryContent1 = binaryContent;

        BinaryContentResponse binaryContentResponse = new BinaryContentResponse( binaryContent1 );

        return binaryContentResponse;
    }
}
