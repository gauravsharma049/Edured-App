package com.edured.services.course_materials;

import com.edured.model.course_materials.Article;
import com.edured.model.course_materials.Course;
import com.edured.model.course_materials.Topic;
import com.edured.repository.course_materials.ArticleRepository;
import com.edured.repository.course_materials.CourseRepository;
import com.edured.repository.course_materials.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchResultService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> searchArticle(String content){
        return articleRepository.findByContentContaining(content);
    }
    public List<Topic> searchTutorial(String content){
        List<Topic> topics = topicRepository.findByContentContaining(content);
        for(Topic topic : topics){
            topic.setContent(topic.getContent().substring(0, Math.min(topic.getContent().length(), 500))+"...");
        }
        return topics;
    }

    public List<Course> searchCourse(String name){
        List<Course> courses = courseRepository.findByCourseNameContaining(name);
        for(Course course: courses){
            course.setTeacher(null);
        }
        return courses;
    }
}
