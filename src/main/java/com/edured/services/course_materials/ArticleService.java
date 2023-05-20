package com.edured.services.course_materials;

import com.edured.model.course_materials.Article;
import com.edured.repository.course_materials.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public Article addArticle(Article article){
        return articleRepository.save(article);
    }
    public List<Article> getAllArticles(){
        return articleRepository.findAll();
    }

    public Article getArticleBySlug(String slug) {
        return articleRepository.findBySlug(slug);
    }
}
