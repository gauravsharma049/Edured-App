package com.edured.repository.course_materials;

import com.edured.model.course_materials.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    public Topic findByName(String topicName);
    public List<Topic> findByLessonId(long id);
    public Topic findBySlug(String slug);
    public List<Topic> findByLessonSlug(String slug);

    List<Topic> findByContentContaining(String content);
    public List<Topic> findByLessonCourseTeacherIdOrderByViewCountDesc(long id);

    @Transactional
    @Modifying
    @Query("UPDATE Topic t SET t.viewCount = :updatedViewCount WHERE t.id = :topicId")
    public void updateViewCount(@Param("updatedViewCount") long viewCount, @Param("topicId") long topicId);
}