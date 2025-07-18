package org.example.arbook.repository;

import jakarta.transaction.Transactional;
import org.example.arbook.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    List<OrderItem> findAllByIsActiveIsTrue();

    @Modifying
    @Transactional
    @Query("UPDATE OrderItem o SET o.isActive = NOT o.isActive, o.amount = 0 WHERE o.id = :id")
    int toggleIsActiveById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE OrderItem o SET o.amount = :newAmount WHERE o.id = :id")
    int updateAmountById(@Param("id") UUID id, @Param("newAmount") Integer newAmount);
}