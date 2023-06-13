package com.edured.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edured.model.users.Admin;
import com.edured.model.users.Student;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Student findByUserId(long id);
}
