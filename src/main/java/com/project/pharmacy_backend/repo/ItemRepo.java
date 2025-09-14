package com.project.pharmacy_backend.repo;

import com.project.pharmacy_backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories

public interface ItemRepo extends JpaRepository<Item,Long> {
    boolean existsByItemName(String itemName);
    Long countByActiveStateTrue();
    List<Item> findByStockQuantityLessThanEqualAndActiveStateTrue(int threshold);
    Long countByStockQuantityLessThanEqual(int threshold);
}
