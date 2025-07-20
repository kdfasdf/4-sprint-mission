package com.sprint.mission.discodeit.dto;

//@Getter
//@JsonPropertyOrder({"code", "message", "data"})
//public class ApiResponse<T> {
//
//    private final int code;
//    private final String message;
//    private T data;
//
//    public static <T> ApiResponse<T> onSuccess(T data) {
//        return new ApiResponse<>(HttpStatus.OK, data);
//    }
//
//    public static <T> ApiResponse<T> onFailure(T data) {
//        return new ApiResponse<>(HttpStatus.BAD_REQUEST, data);
//    }
//
//    private ApiResponse(HttpStatus httpStatus, T data) {
//        this.code = httpStatus.value();
//        this.message = httpStatus.name();
//        this.data = data;
//    }
//}
