package com.ssafy.newstagram.api.auth.model.service;

import com.ssafy.newstagram.api.auth.model.dto.EmailFindRequestDto;
import com.ssafy.newstagram.api.auth.model.dto.EmailFindVerifyRequestDto;
import com.ssafy.newstagram.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService{
    private final UserRepository userRepository;
    private final SmsService smsService;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    private final String EMAIL_FIND_CODE_PREFIX = "email-find:";
    private static final int MAX_ATTEMPTS = 5;


    @Override
    public void requestVerificationCode(EmailFindRequestDto dto, long expirationMs) {
        String phoneNumber = dto.getPhoneNumber();
        Optional<Long> userId = userRepository.findIdByPhoneNumber(phoneNumber);

        if(userId.isEmpty()){
            return;
        }

        String key = generateKey(phoneNumber);
        String code = generateCode();
        redisTemplate.opsForHash().put(key, "code", code);
        redisTemplate.opsForHash().put(key, "attempts", MAX_ATTEMPTS);
        redisTemplate.opsForHash().put(key, "userId", userId.get());

        redisTemplate.expire(key, expirationMs, TimeUnit.MILLISECONDS);

        String msg = "[Newstagram] 인증번호: " + code;
        smsService.send(phoneNumber, msg);
    }


    private String generateKey(String phoneNumber){
        return EMAIL_FIND_CODE_PREFIX + phoneNumber;
    }
    private String generateCode(){
        int number = random.nextInt((int) Math.pow(10, CODE_LENGTH));
        return String.format("%0" + CODE_LENGTH + "d", number);
    }
}
