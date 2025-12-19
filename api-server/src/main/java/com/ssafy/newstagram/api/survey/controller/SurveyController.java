package com.ssafy.newstagram.api.survey.controller;

import com.ssafy.newstagram.api.common.BaseResponse;
import com.ssafy.newstagram.api.survey.model.dto.SurveyRequestDto;
import com.ssafy.newstagram.api.users.model.dto.CustomUserDetails;
import com.ssafy.newstagram.api.users.model.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
@Tag(name = "Survey API", description = "최초 로그인 시 콜드 스타트용 임베딩 데이터 설문")
public class SurveyController {

    private final UserService userService;

    @PostMapping("/submit")
    @Operation(
            summary = "사용자 설문 데이터 처리",
            description = ""
    )
    public ResponseEntity<?> submitSurvey(@RequestBody SurveyRequestDto request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.updateUserEmbedding(userDetails.getUserId(), request.getCategoryIds());
        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponse.success(
                        "SURVEY_200",
                        "관심사 설정 완료",
                        "관심사 설정 완료"
                )
        );
    }
}
