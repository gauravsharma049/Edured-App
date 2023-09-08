package com.edured.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edured.controller.home.LoggedInUserInfo;
import com.edured.model.course_materials.Comment;
import com.edured.model.course_materials.Lesson;
import com.edured.model.course_materials.Topic;
import com.edured.services.course_materials.CommentService;
import com.edured.services.course_materials.CourseServices;
import com.edured.services.course_materials.TopicServices;
import com.edured.services.util.AdvertisementService;
import com.edured.services.util.UtilityClass;

@Controller
@RequestMapping("/t")
public class CourseController {
    @Autowired
    private CourseServices courseService;
    @Autowired
    private TopicServices topicService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private LoggedInUserInfo loggedInUserInfo;
    @Autowired 
    private AdvertisementService advertisementService;

    @GetMapping("/{c-slug}")
    public String getPage(@PathVariable("c-slug") String slug){
        try{
            Lesson lesson = courseService.getCourseBySlug(slug).getLessons().stream().findFirst().get();
            List<Topic> topics = lesson.getTopics();
            UtilityClass.sortById(topics);
            Topic topic = lesson.getTopics().stream().findFirst().get();
            String redirectSlug = topic.getSlug();
            return "redirect:/t/"+slug+"/"+redirectSlug;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "courseIntro";
        }
    }
    
    @GetMapping("/{c-slug}/{t-slug}")
    public String getCoursePage(@PathVariable("c-slug") String courseSlug, @PathVariable("t-slug") String topicSlug, Model model, HttpServletRequest request){
        boolean status=false;
        Topic topic;
        try{
            topic = topicService.getTopicBySlug(topicSlug);
            status = topic.getLesson().getCourse().getSlug().equalsIgnoreCase(courseSlug);

            if (status){
                model.addAttribute("topic", topic);
                model.addAttribute("comments", commentService.getCommentsByTopicId(topic.getId()));
                String referer = request.getHeader("Referer");
                String currentUrl = request.getRequestURL().toString();
                System.out.println("referer: "+referer);
                System.out.println("currentUrl: "+currentUrl);
                if(referer != null && !referer.equalsIgnoreCase(currentUrl)){
                    topicService.updateTopicViewCount(topic.getId());
                }
                Comment comment = new Comment();
                comment.setTopic(topic);
                model.addAttribute("comment", comment);
                model.addAttribute("title", topic.getName());
                List<Lesson> lessons = courseService.getCourseBySlug(courseSlug).getLessons();
                for(Lesson l:lessons){
                    List<Topic> topics = l.getTopics();
                    UtilityClass.sortById(topics);
                    l.setTopics(topics);
                }
                model.addAttribute("lessons", lessons);
                model.addAttribute("ad", advertisementService.getTwoAdvertisements());
                return "lessons";
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "notfound";
    }

    @PostMapping("/add-comment")
    public String addCommentOnTopic(@ModelAttribute("comment") Comment comment, HttpServletRequest request, Principal principal){
        comment.setCommentUser(loggedInUserInfo.loginUser(principal));
        commentService.addComment(comment);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

}
