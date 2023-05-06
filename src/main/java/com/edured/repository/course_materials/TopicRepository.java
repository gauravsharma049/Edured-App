package com.edured.repository.course_materials;

import com.edured.model.course_materials.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    public Topic findByName(String topicName);
    public List<Topic> findByLessonId(long id);
    public Topic findBySlug(String slug);
    public List<Topic> findByLessonSlug(String slug);
}