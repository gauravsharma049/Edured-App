package com.edured.services.users;

import com.edured.model.users.Admin;
import com.edured.repository.users.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin addAdmin(Admin admin){
        admin.getUser().setRole("ROLE_ADMIN");
        admin.getUser().setUsername(admin.getUser().getEmail());
        return adminRepository.save(admin);
    }
}
