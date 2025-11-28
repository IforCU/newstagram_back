package com.ssafy.newstagram.api.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.newstagram.api.dto.UserInteractionLogsDto;
import com.ssafy.newstagram.api.service.KafkaProducerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    /**
     * Kafka에서 어떤 TOPIC으로 전송될지 TOPIC 이름을 지정합니다.
     * [서버].[대분류].[도메인].[이벤트]
     */
    private static final String URL_CLICK_TOPIC_NAME = "api.log.user.url.clicked";

    private final KafkaProducerService producerService;
    private final ObjectMapper objectMapper;

    public LoggingAspect(KafkaProducerService producerService, ObjectMapper objectMapper) {
        this.producerService = producerService;
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(com.ssafy.newstagram.api.annotation.LogToKafka)")
    public Object apiLogUserUrlClicked(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Long articledId = findArticleIdFromArgs(joinPoint);
        Long userId = getUserIdFromSecurity();

        UserInteractionLogsDto logDto = UserInteractionLogsDto.builder()
                .interaction_type("URL_CLICK")
                .session_id(request.getSession().getId())
                .user_agent(request.getSession().getId())
                .ip_address(request.getRemoteAddr())
                .user_id(userId)
                .article_id(articledId)
                .created_at(LocalDateTime.now())
                .build();

        // Kafka에 Message 전송을 위해 JSON String 변환
        String jsonMessage = objectMapper.writeValueAsString(logDto);
        producerService.sendMessage(URL_CLICK_TOPIC_NAME, jsonMessage);
        return joinPoint.proceed();
    }

    private Long getUserIdFromSecurity() {
    }

    private Long findArticleIdFromArgs(ProceedingJoinPoint joinPoint) {
    }
}
