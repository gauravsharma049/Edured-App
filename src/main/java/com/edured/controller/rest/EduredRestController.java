package com.edured.controller.rest;

import com.edured.model.course_materials.*;
import com.edured.model.users.Student;
import com.edured.model.users.Teacher;
import com.edured.services.course_materials.*;
import com.edured.services.users.StudentService;
import com.edured.services.users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/rest")
@RestController
public class EduredRestController {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseServices courseService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    TopicServices topicService;
    @Autowired
    LessonServices lessonService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    CommentService commentService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome boy";
    }
    @GetMapping("/student")
    public List<Student> student(){
        return studentService.getAllStudent();
    }
    @PostMapping("/student")
    public String addStudent(@RequestBody Student student){
        studentService.addStudent(student);
        return "added";
    }
    @DeleteMapping("/delete/s/{id}")
    public String deleteStudent(@PathVariable("id") long id){
        studentService.deleteStudent(id);
        return "deleted";
    }

    @GetMapping("/course/{id}")
    public Course getCourseById(@PathVariable("id") long id){
        return courseService.getCourseById(id);
    }

    @PostMapping("/course")
    public Course addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }
    @GetMapping("/course")
    public List<Course> getAllCourse(){
        return courseService.getAllCourses();
    }
    @PostMapping("/teacher")
    public Teacher addTeacher(@RequestBody Teacher teacher){
        teacher.getUser().setRole("ROLE_TEACHER");
        teacher.getUser().setUsername(teacher.getUser().getEmail());
        teacher.getUser().setPassword(passwordEncoder.encode(teacher.getUser().getPassword()));
        return  teacherService.addTeacher(teacher);
    }
    @GetMapping("/teacher")
    public List<Teacher> getAllTeacher(){
        return  teacherService.getAllTeachers();
    }
    @GetMapping("/topic")
    public List<Topic> getAllTopic(){
        return topicService.getAllTopic();
    }
    @PostMapping("/topic")
    public Topic addTopic(@RequestBody Topic topic){
        return  topicService.addTopic(topic);
    }
    @GetMapping("/article")
    public List<Article> getArticles(){
        return articleService.getAllArticles();
    }
    @PostMapping("/article")
    public Article addArticle(@RequestBody Article article){
        return articleService.addArticle(article);
    }
    @GetMapping("/comment")
    public List<Comment> getComments(){
        return commentService.getAllComments();
    }
    @PostMapping("/comment")
    public Comment addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
    @GetMapping("/lesson")
    public List<Lesson> getAllLesson(){
        return lessonService.getAllLessons();
    }
    @GetMapping("/lesson/c/{id}")
    public List<Lesson> getLessonByCourseId(@PathVariable("id") long id){
        return lessonService.getLessonByCourseId(id);
    }
    @PostMapping("/lesson")
    public Lesson addLesson(@RequestBody Lesson lesson){
        return  lessonService.addLesson(lesson);
    }
    @GetMapping("/lesson/{id}")
    public Teacher getTeacher(@PathVariable("id") long id){
//        System.out.println(courseService.getAllCourses());
        System.out.println(courseService.getCourseById(4).getTeacher());
        return courseService.getCourseById(lessonService.getLessonById(id).getCourse().getId()).getTeacher();
    }
}
