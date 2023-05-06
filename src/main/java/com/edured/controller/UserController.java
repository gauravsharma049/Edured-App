package com.edured.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.edured.model.users.Student;
import com.edured.services.users.StudentService;

@Controller
public class UserController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    StudentService studentService;
    @PostMapping("/signup")
    public String SignUp(@ModelAttribute("student")Student student){
        student.getUser().setRole("ROLE_STUDENT");
        student.getUser().setUsername(student.getUser().getEmail());
        student.getUser().setPassword(passwordEncoder.encode(student.getUser().getPassword()));
        System.out.println(student.getUser().getEmail()+"\n"+student.getUser().getName()+"\n"+student.getUser().getPassword());
        studentService.addStudent(student);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(){
        return "redirect:/logout";
    }

}

