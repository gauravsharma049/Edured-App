package com.edured.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edured.model.course_materials.Article;
import com.edured.model.course_materials.Course;
import com.edured.model.course_materials.Lesson;
import com.edured.model.course_materials.Topic;
import com.edured.services.course_materials.CourseServices;
import com.edured.services.course_materials.SearchResultService;
import com.edured.services.users.EduredUserService;
import com.edured.services.users.TeacherService;

@Controller
public class HomeController {
    @Autowired
    EduredUserService userService;
    @Autowired
    CourseServices courseService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    SearchResultService searchResultService;
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("title", "Edured - learn tutorials and earn knowledge for free!");
//        System.out.println(courseService.getAllCourses());
        return "index";
    }
    
    @GetMapping("/searchResults")
    public String searchResults(HttpServletRequest request, Model model){
        String keyword = request.getParameter("search");
        System.out.println(keyword);
        if(keyword !=null){
            List<Article> articles = searchResultService.searchArticle(keyword);
            List<Course> courses = searchResultService.searchCourse(keyword);
            List<Topic> topics = searchResultService.searchTutorial(keyword);
            Map<String, List<?>> results = new HashMap<>();
            results.put("articles", articles);
            results.put("courses", courses);
            results.put("tutorials", topics);

            for(Article article : articles){
                String content = article.getContent().substring(0, Math.min(article.getContent().length(), 500))+"...";
                article.setContent(content);
            }
            model.addAttribute("results", articles);
            model.addAttribute("allResult", results);
            return "SearchResults";
        }
        else {
            return "notfound";
        }

    }
    @GetMapping("/lessons")
    public String lessons(Model model){
        model.addAttribute("title", "Lessons");
        return "lessons";
    }

    @GetMapping("/contact-us")
    public String contact(Model model){
        model.addAttribute("title", "Contact us");
        return "contact";
    }
    @GetMapping("/blog")
    public String blog(Model model){
        model.addAttribute("title", "edured blog");
        return "blog";
    }
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About us");
        return "about";
    }
    @GetMapping("/not-found")
    public String notfound(Model model){
        model.addAttribute("title", "Page not found");
        return "notfound";
    }

    @GetMapping("/dashboard/{username}")
    public String dashboard(@PathVariable("username") String username, Model model){
        model.addAttribute("role", username);

        if(userService.getUserByEmail(username).getRole().equalsIgnoreCase("role_student")){
            model.addAttribute("title", "Student Dashboard");
            model.addAttribute("userdetail", userService.getUserByEmail(username));
            return "studentdashboard";
        }
        else if(userService.getUserByEmail(username).getRole().equalsIgnoreCase("role_teacher")){
            model.addAttribute("title", "Teacher Dashboard");
            model.addAttribute("userdetail", userService.getUserByEmail(username));
            model.addAttribute("course", new Course());
            model.addAttribute("lesson", new Lesson());
            model.addAttribute("teachers", teacherService.getAllTeachers());
            model.addAttribute("courses", courseService.getAllCourses());
            return "teacher_dashboard";
        }
        else if(userService.getUserByEmail(username).getRole().equalsIgnoreCase("role_admin")){
            model.addAttribute("title", "Admin Dashboard");
            model.addAttribute("userdetail", userService.getUserByEmail(username));
            return "admin";
        }
        else{
            model.addAttribute("title", "Page not found");
            return "notfound";
        }

    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("courses", courseService.getAllCourses());
        return "teacher_dashboard";
    }


    @GetMapping("/abcd")
    public String abcd(){
        return "abcd";
    }

}
