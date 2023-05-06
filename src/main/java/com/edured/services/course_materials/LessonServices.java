package com.edured.services.course_materials;


import com.edured.model.course_materials.Lesson;
import com.edured.repository.course_materials.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServices {
    @Autowired
    LessonRepository lessonRepository;

    public Lesson addLesson(Lesson lesson){
        return lessonRepository.save(lesson);
    }
    public Lesson updateLesson(Lesson lesson){
        return lessonRepository.save(lesson);
    }
    public Lesson getLessonById(long id){
        return lessonRepository.findById(id).get();
    }

    public List<Lesson> getAllLessons(){
        return lessonRepository.findAll();
    }

    public void deleteLesson(long id){
        lessonRepository.delete(lessonRepository.findById(id).get());
    }

    public List<Lesson> getByCourseName(String courseName){
        return lessonRepository.findByCourseCourseName(courseName);
    }
    public List<Lesson> getLessonByCourseId(long id){
        return lessonRepository.findByCourseId(id);
    }

    public Lesson getLessonBySlug(String slug){
        return lessonRepository.findBySlug(slug);
    }

    public List<Lesson> getLessonsByCourseSlug(String slug) {
        return lessonRepository.findByCourseSlug(slug);
    }

}
