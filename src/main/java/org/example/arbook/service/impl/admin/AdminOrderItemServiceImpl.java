package org.example.arbook.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.arbook.repository.OrderItemRepository;
import org.example.arbook.service.interfaces.admin.AdminOrderItemService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminOrderItemServiceImpl implements AdminOrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public Integer updateOrderItemAmount(UUID itemId, Integer amount) {
        return orderItemRepository.updateAmountById(itemId, amount);
    }

    @Override
    public void deactivateOrderItem(UUID itemId) {
        orderItemRepository.toggleIsActiveById(itemId);
    }
}
