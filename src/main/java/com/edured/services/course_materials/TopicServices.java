package com.edured.services.course_materials;

import com.edured.model.course_materials.Topic;
import com.edured.repository.course_materials.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServices {
    @Autowired
    TopicRepository topicRepository;

    public Topic addTopic(Topic topic){
        return topicRepository.save(topic);
    }

    public Topic getTopicById(long id){
        return topicRepository.findById(id).get();
    }

    public List<Topic> getAllTopic(){
        return topicRepository.findAll();
    }

    public void deleteTopic(long id){
        topicRepository.delete(topicRepository.findById(id).get());
    }

    public Topic getTopicByName(String topicName) {
        return topicRepository.findByName(topicName);
    }

    public List<Topic> getTopicByLessonId(long id){
        return topicRepository.findByLessonId(id);
    }

    public Topic getTopicBySlug(String slug) {
        return topicRepository.findBySlug(slug);
    }

    public List<Topic> getTopicByLessonSlug(String slug) {
        return topicRepository.findByLessonSlug(slug);
    }

    public void updateTopicViewCount(long topicId){
        topicRepository.updateViewCount(topicRepository.findById(topicId).get().getViewCount()+1, topicId);
    }

    public List<Topic> getTopicsByTeacerId(long id){
        return topicRepository.findByLessonCourseTeacherIdOrderByViewCountDesc(id);
    }
}
