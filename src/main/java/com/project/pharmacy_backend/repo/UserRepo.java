package com.project.pharmacy_backend.repo;

import com.project.pharmacy_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User,String> {
    //Email is unique but if the email repeat ,in that case we use list of User
    User findByEmailEquals(String userName);

    boolean existsByEmail(String s);
}
