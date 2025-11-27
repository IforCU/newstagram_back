package com.ssafy.newstagram.api.article.entity;

import com.ssafy.newstagram.api.article.dto.ArticleDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String description;
    private String url;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    private String author;

    @Column(name = "published_at")
    private Timestamp publishedAt;

    @Column(name = "thumbnail_url")
    private Timestamp createAt;

    @Column(name = "thumbnail_url")
    private Timestamp updateAt;

    @Column(name = "feed_id")
    private Long feedId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "sources_id")
    private Long sourcesId;


    public ArticleDto toArticleDto() {
        return ArticleDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .description(description)
                .url(url)
                .thumbnailUrl(thumbnailUrl)
                .author(author)
                .createAt(createAt)
                .updateAt(updateAt)
                .category("test")
                .build();
    }
}
