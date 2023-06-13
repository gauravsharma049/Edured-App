package com.edured.services.course_materials;

import com.edured.model.course_materials.Article;
import com.edured.repository.course_materials.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleBySlug(String slug) {
        return articleRepository.findBySlug(slug);
    }

    public List<Article> getPublishedArticles() {
        return articleRepository.findByStatus(true);
    }
    public List<Article> getPublishedArticles(String username) {
        return articleRepository.findByStatusAndWriterUsername(true, username);
    }

    public List<Article> getPendingArticles() {
        List<Article> articles = articleRepository.findByStatus(false);

        Iterator<Article> iterator = articles.iterator();

        while (iterator.hasNext()) {
            Article article = iterator.next();
            if (article.isDeleted()) {
                iterator.remove();
            }
        }
        return articles;
    }
    public List<Article> getPendingArticles(String username) {
        List<Article> articles = articleRepository.findByStatusAndWriterUsername(false, username);

        Iterator<Article> iterator = articles.iterator();

        while (iterator.hasNext()) {
            Article article = iterator.next();
            if (article.isDeleted()) {
                iterator.remove();
            }
        }
        return articles;
    }

    public List<Article> getDeletedArticles() {
        return articleRepository.findByDeleted(true);
    }
    public List<Article> getDeletedArticles(String username) {
        return articleRepository.findByDeletedAndWriterUsername(true, username);
    }
}
