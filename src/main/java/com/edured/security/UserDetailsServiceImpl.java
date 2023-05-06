package com.edured.security;


import com.edured.model.users.EduredUser;
import com.edured.repository.users.EduredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    EduredUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EduredUser user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Username not found !!");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return customUserDetails;
    }

}
