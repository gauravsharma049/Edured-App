package com.edured.services.users;


import com.edured.model.users.EduredUser;
import com.edured.repository.users.EduredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EduredUserService {
    @Autowired
    EduredUserRepository userRepository;

    public List<EduredUser> getUserByRole(String role){
        return userRepository.findByRole(role);
    }
    public EduredUser getUserByEmail(String email){return userRepository.findByEmail(email);}
}