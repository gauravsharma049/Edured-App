package com.edured.repository.users;

import com.edured.model.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    public Teacher findByUserId(long id);
}
