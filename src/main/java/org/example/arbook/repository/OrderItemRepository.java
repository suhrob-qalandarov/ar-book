package org.example.arbook.repository;

import org.example.arbook.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByIsActiveIsTrue();

    @Modifying
    @Transactional
    @Query("UPDATE OrderItem o SET o.isActive = NOT o.isActive WHERE o.id = :id")
    int toggleIsActiveById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE OrderItem o SET o.amount = :newAmount WHERE o.id = :id")
    int updateAmountById(@Param("id") Long id, @Param("newAmount") Integer newAmount);
}