package com.ssafy.newstagram.api.article.controller;

import com.ssafy.newstagram.api.article.dto.ArticleDto;
import com.ssafy.newstagram.api.article.dto.HotIssueSetDto;
import com.ssafy.newstagram.api.article.service.ArticleService;
import com.ssafy.newstagram.api.article.service.HotIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final HotIssueService hotIssueService;


    @GetMapping("/detail/{id}")
    public ArticleDto getArticleDto(@PathVariable Long id) {
        return articleService.getArticleDto(id);
    }

    @GetMapping("/hot-issues")
    public ResponseEntity<HotIssueSetDto> getHotIssues(
            @RequestParam(name = "periodType") String periodType,
            @RequestParam(name = "limitCount", defaultValue = "3") int limitCount
    ) {
        return ResponseEntity.ok(
                hotIssueService.getPeriodRecommendationsTopNPerRankingByPeriodType(
                        periodType,
                        limitCount
                )
        );
    }

}
