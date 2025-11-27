package com.ssafy.newstagram.api.article.repository;

import com.ssafy.newstagram.api.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
