package com.xxx.emsp.account.dto.api;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * @author Tom
 */
@Getter
@Setter
@Builder
@ToString
public class ApiResponse<T> {

    private Integer code;
    private String message;

    private T data;

    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .build();
    }


    public static <T> ApiResponse<T> fail(Integer code, String message, T data) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> fail(Integer code, String message) {
        return fail(code, message, null);
    }


}
