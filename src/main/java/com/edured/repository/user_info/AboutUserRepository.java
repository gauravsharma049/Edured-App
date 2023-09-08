package com.edured.repository.user_info;

import com.edured.model.userInfo.AboutUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutUserRepository extends JpaRepository<AboutUser, Long> {
    
}
