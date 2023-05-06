package com.edured.repository.users;

import com.edured.model.users.Admin;
import com.edured.model.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Student findByUserId(long id);
}
