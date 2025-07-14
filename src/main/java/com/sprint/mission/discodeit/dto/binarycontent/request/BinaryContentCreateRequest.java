package com.sprint.mission.discodeit.dto.binarycontent.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BinaryContentCreateRequest {

    @NotBlank(message = "fileName는 null이거나 공백이면 안됨")
    String fileName;

//    @NotBlank(message = "fileType는 null이거나 공백이면 안됨")
//    String fileType;

    @NotNull(message = "contentType는 null이면 안됨")
    private String contentType;

    @NotNull(message = "data는 null이면 안됨")
    byte[] bytes;

    public BinaryContentCreateServiceRequest toServiceRequest() {
        return BinaryContentCreateServiceRequest.builder()
                .fileName(fileName)
                .contentType(contentType)
                .bytes(bytes)
                .build();
    }
}
