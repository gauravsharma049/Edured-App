package com.edured.repository.course_materials;

import com.edured.model.course_materials.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    public Course findByCourseName(String courseName);

    public Course findBySlug(String slug);
}
