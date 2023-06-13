package com.edured.services.course_materials;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edured.home.LoggedInUserInfo;
import com.edured.model.course_materials.Comment;
import com.edured.repository.course_materials.CommentRepository;
import com.edured.repository.users.EduredUserRepository;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    EduredUserRepository userRepository;
    @Autowired
    LoggedInUserInfo loggedInUserInfo;

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment addComment(Comment comment){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

//        comment.setCommentUser(userRepository.findById(8l).get());
        comment.setCommentTime(date);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByTopicId(long id){
        return commentRepository.findByTopicId(id);
    }
}
