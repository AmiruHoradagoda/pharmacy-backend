package com.project.pharmacy_backend.repo;

import com.project.pharmacy_backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User,String> {
    // Add this method to find users by email
    Optional<User> findByEmail(String email);

    // You might also want these for flexibility
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName = :roleName")
    Page<User> findAllCustomers(@Param("roleName") String roleName, Pageable pageable);


}
