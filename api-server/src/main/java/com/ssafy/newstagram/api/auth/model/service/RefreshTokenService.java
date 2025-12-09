package com.ssafy.newstagram.api.auth.model.service;

import com.ssafy.newstagram.api.auth.jwt.RefreshTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenUtil refreshTokenUtil;

    @Value("${jwt.refresh-expiration}")
    private long REFRESH_TOKEN_TTL;

    public void issue(String email, String refreshToken){
        refreshTokenUtil.save(email, refreshToken, REFRESH_TOKEN_TTL);
    }
    public String getRefreshToken(String email){
        return refreshTokenUtil.getRefreshToken(email);
    }

    public void delete(String email){
        refreshTokenUtil.delete(email);
    }
}
