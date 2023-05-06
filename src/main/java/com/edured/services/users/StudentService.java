package com.edured.services.users;

import com.edured.model.users.Student;
import com.edured.repository.users.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public Student getStudentById(long id){
        return studentRepository.findById(id).get();
    }

    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public void deleteStudent(long id){
        studentRepository.delete(studentRepository.findById(id).get());
    }

    public Student findByUserId(long id) {
        return studentRepository.findByUserId(id);
    }
}
