package com.edured.services.users;


import com.edured.dto.EduredUserDto;
import com.edured.model.users.EduredUser;
import com.edured.repository.users.EduredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class EduredUserService {
    @Autowired
    EduredUserRepository userRepository;

    public List<EduredUser> getUserByRole(String role){
        return userRepository.findByRole(role);
    }
    public EduredUser getUserById(long id){
        return userRepository.findById(id).get();
    }
    public EduredUser getUserByEmail(String email){return userRepository.findByEmail(email);}
    public List<EduredUser> getAllUsers(){
        return userRepository.findAll();
    }
    public EduredUser getUserFromDto(EduredUserDto userDto){
        EduredUser user = new EduredUser();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public EduredUser getLoggedInUser(Principal principal){
        try{
            String username = principal.getName();
            return getUserByEmail(username);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}