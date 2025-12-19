package com.ssafy.newstagram.api.survey.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SurveyRequestDto {
    private List<Long> categoryIds;
}
