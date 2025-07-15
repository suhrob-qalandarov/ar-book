package org.example.arbook.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.arbook.repository.OrderItemRepository;
import org.example.arbook.service.interfaces.admin.AdminOrderItemService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderItemServiceImpl implements AdminOrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public Integer updateOrderItemAmount(Long itemId, Integer amount) {
        return orderItemRepository.updateAmountById(itemId, amount);
    }

    @Override
    public void deactivateOrderItem(Long itemId) {
        orderItemRepository.toggleIsActiveById(itemId);
    }
}
