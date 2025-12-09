package com.ssafy.newstagram.api.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RefreshTokenUtil {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REFRESH_TOKEN_PREFIX = "RT:";

    public void save(String email, String refreshToken, long ttl){
        redisTemplate.opsForValue().set(
                REFRESH_TOKEN_PREFIX + email,
                refreshToken,
                ttl,
                TimeUnit.MILLISECONDS
        );
    }

    public void delete(String email){
        String key = REFRESH_TOKEN_PREFIX + email;
        redisTemplate.delete(key);
    }

    public String getRefreshToken(String email){
        String key = REFRESH_TOKEN_PREFIX + email;
        return (String) redisTemplate.opsForValue().get(key);
    }
}
