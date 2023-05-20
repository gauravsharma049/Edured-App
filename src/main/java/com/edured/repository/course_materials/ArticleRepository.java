package com.edured.repository.course_materials;

import com.edured.model.course_materials.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    public Article findBySlug(String slug);
    List<Article> findByContentContaining(String content);
}
