package com.edured.controller.advice;

import com.edured.model.users.Student;
import com.edured.model.users.Teacher;
import com.edured.services.users.EduredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalData {
    @Autowired
    EduredUserService userService;
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        try{
            model.addAttribute("student", new Student());
            
            String userName = principal.getName();
            System.out.println("USERNAME: " + userName);
            model.addAttribute("loggedinuser", userService.getUserByEmail(userName));

        }
        catch(Exception e){
//            model.addAttribute("student", new Student());
            model.addAttribute("loggedinuser", "anonymous");
        }

    }
}
