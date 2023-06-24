package com.edured.services.users;


import com.edured.model.users.Teacher;
import com.edured.repository.users.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    public Teacher addTeacher(Teacher teacher){
        teacher.getUser().setRegisteredDate(new Date().toString());
        return teacherRepository.save(teacher);
    }

    public Teacher getTeacherById(long id){
        return teacherRepository.findById(id).get();
    }

    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }

    public void deleteTeacher(long id){
        teacherRepository.delete(teacherRepository.findById(id).get());
    }

    public Teacher findByUserId(long id) {
        Teacher teacher = teacherRepository.findByUserId(id);
        return teacher;
    }

}
