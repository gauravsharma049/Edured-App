package com.edured.home;

import com.edured.model.users.EduredUser;
import com.edured.model.users.Student;
import com.edured.services.users.EduredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
public class LoggedInUserInfo {
    @Autowired
    EduredUserService userService;
    public EduredUser loginUser(Principal principal) {
        try{
            String userName = principal.getName();
            System.out.println("USERNAME: " + userName);
            return userService.getUserByEmail(userName);
        }
        catch(Exception e){
           return null;
        }

    }
}
