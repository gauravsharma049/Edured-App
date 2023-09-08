package com.edured.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edured.dto.EduredUserDto;
import com.edured.model.userInfo.AboutUser;
import com.edured.model.userInfo.UserEducation;
import com.edured.model.userInfo.UserWorkExperience;
import com.edured.model.users.Admin;
import com.edured.model.users.EduredUser;
import com.edured.model.users.Student;
import com.edured.model.users.Teacher;
import com.edured.services.user_info.UserInfoService;
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
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/signup")
    public String SignUp(@Valid @ModelAttribute("student") EduredUserDto userDto, BindingResult bindingResult,
            RedirectAttributes attributes, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("student", userDto);
            return "index";
        }
        try {
            EduredUser user = userService.getUserFromDto(userDto);
            user.setRole("ROLE_STUDENT");
            user.setUsername(user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(user.getEmail() + "\n" + user.getName() + "\n"
                    + user.getPassword());
            Student student = new Student();
            student.setUser(user);
            studentService.addStudent(student);
            attributes.addFlashAttribute("success", "Successfully Registered!");
            session.setAttribute("message", "signup-success");
            return "redirect:/";
        } catch (DataIntegrityViolationException e) {
            session.setAttribute("failed", "user with email id " + userDto.getEmail() + " already exixts..");
            session.setAttribute("message", "signup-failed");
            System.out.println("user with email id " + userDto.getEmail() + " already exists..");
            return "index";
        } catch (Exception e) {
            attributes.addFlashAttribute("failed", e.getLocalizedMessage());
            session.setAttribute("message", "signup-failed");
            System.out.println(e.getLocalizedMessage());
            return "index";
        }
    }
    // @PostMapping("/signup")
    // public String SignUp(@ModelAttribute("student") Student student) {
    // student.getUser().setRole("ROLE_STUDENT");
    // student.getUser().setUsername(student.getUser().getEmail());
    // student.getUser().setPassword(passwordEncoder.encode(student.getUser().getPassword()));
    // System.out.println(student.getUser().getEmail() + "\n" +
    // student.getUser().getName() + "\n"
    // + student.getUser().getPassword());
    // studentService.addStudent(student);
    // return "redirect:/";
    // }

    @GetMapping("/teacher-signup")
    public String teacherSignUp(Model model, Principal principal) {
        String role;
        try {
            role = userService.getUserByEmail(principal.getName()).getRole();
        } catch (Exception e) {
            role = "";
        }

        if(!role.equals("ROLE_ADMIN")){
            return "redirect:/";
        }
        // model.addAttribute("teacher", new Teacher());
        model.addAttribute("title", "Teacher SignUp");
        model.addAttribute("teacher", new EduredUserDto());
        return "teacher-signup";
    }

    @PostMapping("/teacher-signup")
    public String teacherSignUp(@Valid @ModelAttribute("teacher") EduredUserDto userDto, BindingResult bindingResult,
            RedirectAttributes attributes, HttpSession session) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "teacher-signup";
        }
        try {
            EduredUser user = userService.getUserFromDto(userDto);
            user.setRole("ROLE_TEACHER");
            user.setUsername(user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacherService.addTeacher(teacher);
            attributes.addFlashAttribute("success", "Successfully Registered!");
            session.setAttribute("message", "signup-success");
            return "redirect:/";
        } catch (DataIntegrityViolationException e) {
            session.setAttribute("failed", "user with email id " + userDto.getEmail() + " already exixts..");
            session.setAttribute("message", "signup-failed");
            System.out.println("user with email id " + userDto.getEmail() + " already exists..");
            return "teacher-signup";
        } catch (Exception e) {
            attributes.addFlashAttribute("failed", e.getLocalizedMessage());
            session.setAttribute("message", "signup-failed");
            System.out.println(e.getLocalizedMessage());
            return "teacher-signup";
        }
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
        try {
            role = userService.getUserByEmail(principal.getName()).getRole();
        } catch (Exception e) {
            role = "";
        }
        if (userService.getUserByRole("ROLE_ADMIN").size() == 0 || role.equals("ROLE_ADMIN")) {
            admin.getUser().setRole("ROLE_ADMIN");
            admin.getUser().setUsername(admin.getUser().getEmail());
            admin.getUser().setPassword(passwordEncoder.encode(admin.getUser().getPassword()));
            adminService.addAdmin(admin);
            if (role.equals("ROLE_ADMIN")) {
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

    @PostMapping("/work")
    public String saveUpdateUserWorkExperience(
            @ModelAttribute("workExperience") UserWorkExperience userWorkExperience, Principal principal, HttpServletRequest request) {
        System.out.println(userWorkExperience.getCompanyName());
        System.out.println(userWorkExperience.getPosition());
        System.out.println(userWorkExperience.getStartDate());
        System.out.println(userWorkExperience.getEndDate());
        System.out.println(userWorkExperience.getDescription());
        userInfoService.updateUserWorkExperience(userWorkExperience, principal);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @PostMapping("/education")
    public String saveUpdateUserEducation(@ModelAttribute("education") UserEducation userEducation,Principal principal, HttpServletRequest request) {
        System.out.println(userEducation.getSchoolName());
        System.out.println(userEducation.getDegree());
        System.out.println(userEducation.getStartDate());
        System.out.println(userEducation.getEndDate());
        System.out.println(userEducation.getDescription());
        userInfoService.updateUserEducation(userEducation, principal);
        String referer = request.getHeader("Referer");
        String currentUrl = request.getRequestURL().toString();
        System.out.println("current url: "+currentUrl);
        System.out.println("referer: "+referer);
        return "redirect:"+ referer;
    }
}
