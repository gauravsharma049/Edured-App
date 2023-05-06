package com.edured.services.course_materials;


import com.edured.model.course_materials.Course;
import com.edured.repository.course_materials.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServices {
    @Autowired
    CourseRepository courseRepository;

    public Course addCourse(Course course){
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
