package com.edured.repository.course_materials;

import com.edured.model.course_materials.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
    public List<Lesson> findByCourseCourseName(String courseName);
    public List<Lesson> findByCourseId(long id);
    public Lesson findBySlug(String slug);
    public List<Lesson> findByCourseSlug(String slug);
}
