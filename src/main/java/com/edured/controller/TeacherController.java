package com.edured.controller;

import com.edured.model.course_materials.Course;
import com.edured.model.course_materials.Lesson;
import com.edured.model.course_materials.Topic;
import com.edured.services.course_materials.CourseServices;
import com.edured.services.course_materials.LessonServices;
import com.edured.services.course_materials.TopicServices;
import com.edured.services.users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    LessonServices lessonService;
    @Autowired
    CourseServices courseService;
    @Autowired
    TopicServices topicService;
    @PostMapping("/add-course")
    public String addCourse(@ModelAttribute("course") Course course, Principal principal){
        courseService.addCourse(course, principal);
        return "redirect:/dashboard/"+teacherService.getTeacherById(course.getTeacher().getId()).getUser().getEmail();
    }

    @PostMapping("/add-lesson")
    public String addLesson(@ModelAttribute("lesson") Lesson lesson, HttpServletRequest request){
        lessonService.addLesson(lesson);

        String referer = request.getHeader("Referer");
        if (referer == null) {
            // Handle case where there is no Referer header
            String email = courseService.getCourseById(lesson.getCourse().getId()).getTeacher().getUser().getEmail();
            return "redirect:/dashboard/"+email;
        }
        return "redirect:" + referer;
    }
    @GetMapping("/write-tutorial")
    public String writeTutorial(Model model, Principal principal){
        model.addAttribute("title", "Write Tutorial");
        List<Course> c = courseService.getCourseByTeacherId(principal);
        model.addAttribute("courses", c);
        model.addAttribute("topic", new Topic());
        return "WriteTutorial";
    }
    @PostMapping("/add-tutorial")
    public String addTopic(@ModelAttribute("topic")Topic topic){
        System.out.println("lessonId:-"+topic.getLesson().getId()+"\ntopicName:"+topic.getName()+"\ntopicSlug:"+topic.getSlug()+"\ntopicContent:"+topic.getContent());
        topicService.addTopic(topic);
        return "redirect:/t/"+ lessonService.getLessonById(topic.getLesson().getId()).getCourse().getSlug()+"/"+topic.getSlug();
    }

}
