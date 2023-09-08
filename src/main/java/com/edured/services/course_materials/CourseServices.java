package com.edured.services.course_materials;


import com.edured.model.course_materials.Course;
import com.edured.model.users.EduredUser;
import com.edured.repository.course_materials.CourseRepository;
import com.edured.services.users.EduredUserService;
import com.edured.services.users.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class CourseServices {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EduredUserService userService;
    @Autowired 
    private TeacherService teacherService;

    public Course addCourse(Course course, Principal principal){
        course.setTeacher(teacherService.findByUserId(userService.getLoggedInUser(principal).getId()));
        return courseRepository.save(course);
    }
    public Course updateCourse(Course course){
        return courseRepository.save(course);
    }
    // public Course updateCourse(int id, Course course){
    //     Course course2 = courseRepository.findById(id).get();

    //     return courseRepository.save(course2);
    // }
    public Course getCourseById(long id){
        return courseRepository.findById(id).get();
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public List<Course> getCourseByTeacherId(Principal principal){
        EduredUser user = userService.getLoggedInUser(principal);
        if(user == null || !user.getRole().equals("ROLE_TEACHER")){
            return null;
        }
        
        long userId = user.getId();
        long teacherId = teacherService.findByUserId(userId).getId();
        List<Course> courses = courseRepository.findByTeacherId(teacherId);
        return courses;
    }

    public void deleteCourse(long id){
        courseRepository.delete(courseRepository.findById(id).get());
    }

    public Course getCourseByName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }

    public Course getCourseBySlug(String slug) {
        return courseRepository.findBySlug(slug);
    }
}
