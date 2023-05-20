package com.edured.repository.course_materials;

import com.edured.model.course_materials.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByTopicId(long id);
}
