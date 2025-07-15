package org.example.arbook.service.interfaces.admin;

import org.springframework.stereotype.Service;

@Service
public interface AdminOrderItemService {

    Integer updateOrderItemAmount(Long itemId, Integer amount);

    void deactivateOrderItem(Long itemId);
}
