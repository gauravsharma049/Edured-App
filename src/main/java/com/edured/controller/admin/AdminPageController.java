package com.edured.controller.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edured.model.course_materials.Article;
import com.edured.model.course_materials.Topic;
import com.edured.model.users.EduredUser;
import com.edured.services.course_materials.ArticleService;
import com.edured.services.course_materials.TopicServices;
import com.edured.services.files.FileService;
import com.edured.services.users.EduredUserService;
import com.edured.services.users.StudentService;
import com.edured.services.users.TeacherService;

@Controller
@RequestMapping("/home")
public class AdminPageController {
    @Autowired
    private EduredUserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private FileService fileService;
    @Autowired
    private TopicServices topicService;
    @Value("${project.image}")
    private String path;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "Edured");
        EduredUser user = userService.getLoggedInUser(principal);
        String userRole = user.getRole();
        if(userRole.equals("ROLE_TEACHER")){
            List<Topic> topics = topicService.getTopicsByTeacerId(teacherService.findByUserId(user.getId()).getId());
            model.addAttribute("topics", topics);
            model.addAttribute("maxViewCount", topics.get(0).getViewCount());
        }
        return "admin/index.html";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("title", "Users");
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users.html";
    }

    @GetMapping("/gallery")
    public String galleryPage(Model model){
        model.addAttribute("images", fileService.getImageFile());
        return "admin/gallery";
    }

    @PostMapping("/upload/files")
    public String uploadFiles(@RequestParam("files") MultipartFile file, RedirectAttributes attributes){
        System.out.println(file.getOriginalFilename()+" " + file.getContentType());
        try{
            fileService.uploadImage(path, file);
            attributes.addFlashAttribute("success", "File uploaded successfully!");
        }catch(Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "File upload failed!");
        }
        return "redirect:/home/gallery";
    }

    @GetMapping("/delete/image/{imageName}")
    public String deleteImage(@PathVariable("imageName") String imageName, RedirectAttributes attributes){
        try{
            fileService.deleteImageResource(path, imageName);
            attributes.addFlashAttribute("success", "Image deleted successfully!");
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            attributes.addFlashAttribute("failed", "Image deletion failed!");
        }
        catch(Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Image deletion failed!");
        }
        return "redirect:/home/gallery";
    }

    @GetMapping("/pending/{slug}")
    public String viewPendingPost(@PathVariable("slug") String slug, Model model, Principal principal) {
        try {
            Article article = articleService.getArticleBySlug(slug);

            if (article != null) {
                model.addAttribute("article", article);
                System.out.println(article.toString());
                System.out.println(article.getWriter());
                String writer = principal.getName();
                EduredUser user = userService.getUserByEmail(writer);

                if (article.getWriter().getUsername().equals(writer) || user.getRole().equals("ROLE_ADMIN")) {
                    return "article";
                }

                return "notfound";

            } else {
                return "notfound";
            }
        } catch (Exception e) {
            return "notfound";
        }
    }

    @GetMapping("/approve/article/{slug}")
    public String approveArticle(@PathVariable("slug") String slug, RedirectAttributes attributes,
            Principal principal) {
        try {
            EduredUser user = userService.getUserByEmail(principal.getName());
            if (user.getRole().equals("ROLE_ADMIN")) {
                Article article = articleService.getArticleBySlug(slug);
                article.setStatus(true);
                article.setDeleted(false);
                articleService.addArticle(article);
                attributes.addFlashAttribute("success", "Article approved successfully!");
            } else {
                attributes.addFlashAttribute("failed", "You are not authorized to make this request!");
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("failed", "Something went wrong!");
        }
        return "redirect:/home/posts";
    }

    @GetMapping("/posts")
    public String getPosts(Model model, Principal principal) {
        model.addAttribute("title", "Posts - tutorials and articles");
        List<Article> publishedArticles;
        List<Article> pendingArticles;
        List<Article> deletedArticles;
        try {
            String username = principal.getName();
            if (userService.getUserByEmail(username).getRole().equals("ROLE_ADMIN")) {
                publishedArticles = articleService.getPublishedArticles();
                pendingArticles = articleService.getPendingArticles();
                deletedArticles = articleService.getDeletedArticles();
            } else {
                publishedArticles = articleService.getPublishedArticles(username);
                pendingArticles = articleService.getPendingArticles(username);
                deletedArticles = articleService.getDeletedArticles(username);
            }
        } catch (Exception e) {
            publishedArticles = null;
            pendingArticles = null;
            deletedArticles = null;
        }

        for (Article article : publishedArticles) {
            String content = article.getContent().substring(0, Math.min(article.getContent().length(), 100)) + "...";
            article.setContent(content);
        }
        for (Article article : pendingArticles) {
            String content = article.getContent().substring(0, Math.min(article.getContent().length(), 100)) + "...";
            article.setContent(content);
        }
        for (Article article : deletedArticles) {
            String content = article.getContent().substring(0, Math.min(article.getContent().length(), 100)) + "...";
            article.setContent(content);
        }
        Map<String, Integer> articleCount = new HashMap<>();
        articleCount.put("publishedArticle", publishedArticles.size());
        articleCount.put("pendingArticle", pendingArticles.size());
        articleCount.put("deletedArticle", deletedArticles.size());
        model.addAttribute("articleCount", articleCount);
        model.addAttribute("articles", publishedArticles);
        model.addAttribute("pendingArticles", pendingArticles);
        model.addAttribute("deletedArticles", deletedArticles);
        return "admin/posts.html";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, RedirectAttributes attributes, Principal principal) {
        try {
            String username = principal.getName();
            if (userService.getUserByEmail(username).getRole().equals("ROLE_ADMIN")) {
                EduredUser user = userService.getUserById(id);
                String userRole = user.getRole();

                if (userRole.equals("ROLE_TEACHER")) {
                    articleService.deleteArticlesOfUser(user.getEmail());
                    teacherService.deleteTeacher(
                            teacherService.findByUserId(id).getId());
                    attributes.addFlashAttribute("success", "Successfully deleted user "+ user.getEmail()+"!!");
                } else if (userRole.equals("ROLE_STUDENT")) {
                    studentService.deleteStudent(
                            studentService.findByUserId(id).getStudentId());
                    attributes.addFlashAttribute("success", "Successfully deleted user "+ user.getEmail()+"!!");
                }
            }
            else{
                attributes.addFlashAttribute("failed", "you are not the authorized user to make this request!");
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("failed", "Something went wrong!");
        }
        return "redirect:/home/users";
    }
}
