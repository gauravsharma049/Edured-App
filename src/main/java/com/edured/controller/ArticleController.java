package com.edured.controller;

import com.edured.model.course_materials.Article;
import com.edured.services.course_materials.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/a")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @GetMapping("/{slug}")
    public String getArticlePage(@PathVariable("slug") String slug, Model model){
        try {
            Article article = articleService.getArticleBySlug(slug);
            if(article != null){
                model.addAttribute("article", article);
                return "article";
            }
            else {
                return "notfound";
            }
        }
        catch (Exception e){
            return "notfound";
        }
    }
    @GetMapping("/write-article")
    public String addArticleTemplate(Model model){
        model.addAttribute("article", new Article());
        return "WriteArticle";
    }
    @PostMapping("/write-article")
    public String addArticle(@ModelAttribute("article")Article article, HttpServletRequest request){
        articleService.addArticle(article);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
}
