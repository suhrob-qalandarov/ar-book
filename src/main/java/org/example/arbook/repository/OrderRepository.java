package org.example.arbook.repository;

import org.example.arbook.model.entity.Order;
import org.example.arbook.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatus(OrderStatus status);

    Order findByName(String name);

    @Query(value = """
        WITH updated AS (
            UPDATE orders
            SET status = 'ACCEPTED'
            WHERE id = :orderId
            RETURNING *
        )
        SELECT * FROM updated
        """, nativeQuery = true)
    Order acceptAndReturn(@Param("orderId") Long orderId);

    @Query(value = """
        WITH updated AS (
            UPDATE orders
            SET status = 'DECLINED'
            WHERE id = :orderId
            RETURNING *
        )
        SELECT * FROM updated
        """, nativeQuery = true)
    Order declineAndReturn(@Param("orderId") Long orderId);
}