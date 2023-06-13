package com.edured.controller;

import com.edured.model.users.Admin;
import com.edured.model.users.Teacher;
import com.edured.services.users.AdminService;
import com.edured.services.users.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    TeacherService teacherService;
    @Autowired
    AdminService adminService;
    @PostMapping("/teacher")
    public Teacher addTeacher(@ModelAttribute("teacher") Teacher teacher){
        teacher.getUser().setRole("ROLE_TEACHER");
        teacher.getUser().setUsername(teacher.getUser().getEmail());
        teacher.getUser().setPassword(passwordEncoder.encode(teacher.getUser().getPassword()));
        return  teacherService.addTeacher(teacher);
    }
    @PostMapping("/admin")
    public Admin addAdmin(@ModelAttribute("admin") Admin admin){
        admin.getUser().setPassword(passwordEncoder.encode(admin.getUser().getPassword()));
        return  adminService.addAdmin(admin);
    }
    
}
