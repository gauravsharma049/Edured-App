package com.edured.repository.user_info;

import com.edured.model.userInfo.UserEducation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEducationRepository extends JpaRepository<UserEducation, Long> {
    
}
