package com.xxx.emsp.account.exception;

import com.xxx.emsp.account.dto.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handle(MethodArgumentNotValidException e) {
        StringJoiner joiner = new StringJoiner(";");
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            joiner.add(error.getDefaultMessage());
        }
        log.error("Param validation failed: {}", joiner.toString());
        return handleException(joiner.toString());
    }

    private ApiResponse<?> handleException(String msg) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception ex) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}