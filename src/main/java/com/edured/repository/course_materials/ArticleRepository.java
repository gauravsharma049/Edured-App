package com.edured.repository.course_materials;

import com.edured.model.course_materials.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    public Article findBySlug(String slug);
    List<Article> findByContentContaining(String content);
    public List<Article> findByStatus(boolean b);
    public List<Article> findByDeleted(boolean b);
    public List<Article> findByStatusAndWriterUsername(boolean b, String username);
    public List<Article> findByDeletedAndWriterUsername(boolean b, String username);
    public List<Article> findByWriterUsername(String username);
}
