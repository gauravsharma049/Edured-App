package com.edured.controller;

import com.edured.model.course_materials.Article;
import com.edured.model.users.EduredUser;
import com.edured.services.course_materials.ArticleService;
import com.edured.services.users.EduredUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/a")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired private EduredUserService userService;
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
    public String addArticle(@ModelAttribute("article")Article article,Principal principal, HttpServletRequest request){
        try{
            String userName = principal.getName();
            EduredUser writer = userService.getUserByEmail(userName);
            article.setWriter(writer);
        }
        catch(Exception e){
        }

        articleService.addArticle(article);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
}
