package com.sprint.mission.discodeit.exception;

import com.sprint.mission.discodeit.constant.ClientRequestErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(ClientRequestErrorCode.UKNOWN_ERROR));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(ErrorResponse.of(ex.getErrorCode()));
    }

    /**
     * @ModelAttribute 로 binding error 발생 시
     * 바인딩 필드 타입 불일치, 유효성 검사 길패
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        ErrorResponse errorResponse = ErrorResponse.of(ClientRequestErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return ResponseEntity.status(ClientRequestErrorCode.INVALID_INPUT_VALUE.getStatus())
                .body(errorResponse);
    }

    /**
     * JSON 파싱 에러 등
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ClientRequestErrorCode.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrorResponse.of(ClientRequestErrorCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(ex.getMessage());
        return ResponseEntity.status(ClientRequestErrorCode.INVALID_INPUT_VALUE.getStatus())
                .body(errorResponse);
    }

}
