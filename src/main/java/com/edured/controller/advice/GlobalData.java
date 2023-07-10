package com.edured.controller.advice;

import com.edured.dto.EduredUserDto;
import com.edured.services.course_materials.CourseServices;
import com.edured.services.users.EduredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalData {
    @Autowired
    private EduredUserService userService;
    @Autowired
    private CourseServices courseService;
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        try{
            // model.addAttribute("student", new Student());
            model.addAttribute("student", new EduredUserDto());
            model.addAttribute("footerCourse", courseService.getAllCourses());
            String userName = principal.getName();
            // System.out.println("USERNAME: " + userName);
            model.addAttribute("loggedinuser", userService.getUserByEmail(userName));

        }
        catch(Exception e){
//            model.addAttribute("student", new Student());
            model.addAttribute("loggedinuser", "anonymous");
            model.addAttribute("adminSize", userService.getUserByRole("ROLE_ADMIN").size());
        }

    }
}
