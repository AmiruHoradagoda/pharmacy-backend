package com.project.pharmacy_backend.repo;

import com.project.pharmacy_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double sumTotalAmount();

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate < :date")
    Double sumTotalAmountBefore(@Param("date") Date date);

    Long countByOrderDateBefore(Date date);

    List<Order> findByOrderDateAfter(Date date);

    List<Order> findByOrderDateBetween(Date startDate, Date endDate);
}
