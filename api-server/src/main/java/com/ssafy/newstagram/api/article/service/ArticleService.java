package com.ssafy.newstagram.api.article.service;

import com.ssafy.newstagram.api.article.dto.ArticleDto;
import com.ssafy.newstagram.api.article.entity.Article;
import com.ssafy.newstagram.api.article.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ArticleService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    public ArticleDto getArticleDto(Long articleId) {
        String redisArticlePrefix = "article:";
        String ArticleDtoKey = redisArticlePrefix+articleId;

        // 1) Redis 조회
        ArticleDto cachedDto = (ArticleDto) redisTemplate.opsForValue().get(ArticleDtoKey);
        if (cachedDto != null) {
            return cachedDto; // 캐시 HIT
        }

        // 2) Postgres 조회
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        // 3) DTO 변환
        ArticleDto dto = article.toArticleDto();

        // 4) Redis 저장 (TTL 예: 1시간)
        redisTemplate.opsForValue().set(ArticleDtoKey, dto);

        return dto;
    }

}


