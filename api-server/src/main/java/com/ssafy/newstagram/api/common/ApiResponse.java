package com.ssafy.newstagram.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;
    private ErrorDetail error;

    public static <T> ApiResponse<T> success(String code, T result) {
        return new ApiResponse<>(true, code, "요청에 성공하였습니다.", result, null);
    }

    public static ApiResponse<?> successNoData(String code) {
        return new ApiResponse<>(true, code, "요청에 성공하였습니다.", null, null);
    }

    public static ApiResponse<?> error(String code, String message, ErrorDetail error) {
        return new ApiResponse<>(false, code, message, null, error);
    }
}
