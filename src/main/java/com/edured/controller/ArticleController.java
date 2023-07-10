package com.edured.controller;

import com.edured.model.course_materials.Article;
import com.edured.model.users.EduredUser;
import com.edured.services.course_materials.ArticleService;
import com.edured.services.users.EduredUserService;
import com.edured.services.util.AdvertisementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/a")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired 
    private EduredUserService userService;
    @GetMapping("/{slug}")
    public String getArticlePage(@PathVariable("slug") String slug, Model model){
        try {
            Article article = articleService.getArticleBySlug(slug);
            System.out.println("status is " + article.isStatus());
            if(article != null && article.isStatus()){
                model.addAttribute("title", "Write Article");
                model.addAttribute("article", article);
                model.addAttribute("ad", advertisementService.getAdvertisements(4));
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
    public String addArticle(@ModelAttribute("article")Article article,Principal principal, HttpServletRequest request, RedirectAttributes attributes){
        try{
            String userName = principal.getName();
            EduredUser writer = userService.getUserByEmail(userName);
            article.setWriter(writer);
            attributes.addFlashAttribute("success", "Article successfully submitted for review!");
        }
        catch(Exception e){
            attributes.addFlashAttribute("failed", "Something Went Wrong!");
        }

        articleService.addArticle(article);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
}
