package com.edured.controller;

import com.edured.home.LoggedInUserInfo;
import com.edured.model.course_materials.Comment;
import com.edured.model.course_materials.Course;
import com.edured.model.course_materials.Lesson;
import com.edured.model.course_materials.Topic;
import com.edured.services.course_materials.CommentService;
import com.edured.services.course_materials.CourseServices;
import com.edured.services.course_materials.LessonServices;
import com.edured.services.course_materials.TopicServices;
import com.edured.services.users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/t")
public class CourseController {
    @Autowired
    CourseServices courseService;
    @Autowired
    TopicServices topicService;

    @Autowired
    CommentService commentService;
    @Autowired
    LoggedInUserInfo loggedInUserInfo;

    @GetMapping("/{c-slug}")
    public String getPage(@PathVariable("c-slug") String slug){
        try{
            Lesson lesson = courseService.getCourseBySlug(slug).getLessons().stream().findFirst().get();
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
    public String getCoursePage(@PathVariable("c-slug") String courseSlug, @PathVariable("t-slug") String topicSlug, Model model){
        boolean status=false;
        Topic topic;
        try{
            topic = topicService.getTopicBySlug(topicSlug);
            status = topic.getLesson().getCourse().getSlug().equalsIgnoreCase(courseSlug);

            if (status){
                model.addAttribute("topic", topic);
                model.addAttribute("comments", commentService.getCommentsByTopicId(topic.getId()));
                Comment comment = new Comment();
                comment.setTopic(topic);
                model.addAttribute("comment", comment);
                model.addAttribute("title", topic.getName());
                model.addAttribute("lessons", courseService.getCourseBySlug(courseSlug).getLessons());
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
