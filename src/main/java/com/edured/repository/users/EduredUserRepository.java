package com.edured.repository.users;

import com.edured.model.users.EduredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EduredUserRepository extends JpaRepository<EduredUser,Long> {

    public EduredUser findByEmail(String email);
    public List<EduredUser> findByRole(String role);
}
