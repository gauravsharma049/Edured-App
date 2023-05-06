package com.edured.repository.users;

import com.edured.model.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Student findByUserId(long id);
}
