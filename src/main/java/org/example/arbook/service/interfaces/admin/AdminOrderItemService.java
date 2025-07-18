package org.example.arbook.service.interfaces.admin;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AdminOrderItemService {

    Integer updateOrderItemAmount(UUID itemId, Integer amount);

    void deactivateOrderItem(UUID itemId);
}
