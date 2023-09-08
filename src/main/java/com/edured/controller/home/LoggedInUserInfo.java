package com.edured.controller.home;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edured.model.users.EduredUser;
import com.edured.services.users.EduredUserService;

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
