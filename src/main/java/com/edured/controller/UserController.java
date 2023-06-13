package com.edured.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.edured.model.users.Admin;
import com.edured.model.users.Student;
import com.edured.model.users.Teacher;
import com.edured.services.users.AdminService;
import com.edured.services.users.EduredUserService;
import com.edured.services.users.StudentService;
import com.edured.services.users.TeacherService;

@Controller
public class UserController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    EduredUserService userService;
    @Autowired
    AdminService adminService;

    @PostMapping("/signup")
    public String SignUp(@ModelAttribute("student") Student student) {
        student.getUser().setRole("ROLE_STUDENT");
        student.getUser().setUsername(student.getUser().getEmail());
        student.getUser().setPassword(passwordEncoder.encode(student.getUser().getPassword()));
        System.out.println(student.getUser().getEmail() + "\n" + student.getUser().getName() + "\n"
                + student.getUser().getPassword());
        studentService.addStudent(student);
        return "redirect:/";
    }

    @GetMapping("/teacher-signup")
    public String teacherSignUp(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teacher-signup";
    }

    @PostMapping("/teacher-signup")
    public String teacherSignUp(@ModelAttribute("teacher") Teacher teacher) {
        teacher.getUser().setRole("ROLE_TEACHER");
        teacher.getUser().setUsername(teacher.getUser().getEmail());
        teacher.getUser().setPassword(passwordEncoder.encode(teacher.getUser().getPassword()));
        teacherService.addTeacher(teacher);
        return "redirect:/";
    }

    @GetMapping("/admin-signup")
    public String adminSignUp(Model model) {
        if (userService.getUserByRole("ROLE_ADMIN").size() > 0) {
            return "redirect:/";
        }

        model.addAttribute("admin", new Admin());
        return "admin-signup";
    }

    @PostMapping("/admin-signup")
    public String adminSignUp(@ModelAttribute("admin") Admin admin, Principal principal) {
        String role;
        try{
            role = userService.getUserByEmail(principal.getName()).getRole();
        }
        catch(Exception e){
            role = "";
        }
        if (userService.getUserByRole("ROLE_ADMIN").size() == 0 || role.equals("ROLE_ADMIN")) {
            admin.getUser().setRole("ROLE_ADMIN");
            admin.getUser().setUsername(admin.getUser().getEmail());
            admin.getUser().setPassword(passwordEncoder.encode(admin.getUser().getPassword()));
            adminService.addAdmin(admin);
            if(role.equals("ROLE_ADMIN")){
                return "redirect:/home/users";
            }
            return "redirect:/home/dashboard";
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/logout";
    }

}
