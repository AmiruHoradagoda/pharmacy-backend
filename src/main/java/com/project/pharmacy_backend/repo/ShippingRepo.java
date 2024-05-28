package com.project.pharmacy_backend.repo;

import com.project.pharmacy_backend.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ShippingRepo extends JpaRepository<ShippingAddress,Long> {
}
