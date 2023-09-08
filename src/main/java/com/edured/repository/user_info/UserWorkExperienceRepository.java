package com.edured.repository.user_info;

import com.edured.model.userInfo.UserWorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWorkExperienceRepository extends JpaRepository<UserWorkExperience, Long> {
    
}