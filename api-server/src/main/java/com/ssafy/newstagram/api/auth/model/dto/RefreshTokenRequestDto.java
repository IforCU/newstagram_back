package com.ssafy.newstagram.api.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefreshTokenRequestDto {
    @NotBlank(message = "리프레시 토큰 값은 필수입니다.")
    private String refreshToken;
}
