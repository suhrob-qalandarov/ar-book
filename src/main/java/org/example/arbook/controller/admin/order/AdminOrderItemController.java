package org.example.arbook.controller.admin.order;

import lombok.RequiredArgsConstructor;
import org.example.arbook.service.interfaces.admin.AdminOrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.example.arbook.util.ApiConstants.*;

/**
 * Admin Controller for handling order item endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + ADMIN + ORDER_ITEM)
public class AdminOrderItemController {

    AdminOrderItemService orderItemService;

    @PatchMapping("/quantity/{itemId}")
    public ResponseEntity<Integer> updateOrderItemQuantity(@PathVariable UUID itemId, @RequestParam Integer amount) {
        Integer updatedAmount = orderItemService.updateOrderItemAmount(itemId, amount);
        return ResponseEntity.ok(updatedAmount);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> updateOrderItemQuantity(@PathVariable UUID itemId) {
        orderItemService.deactivateOrderItem(itemId);
        return ResponseEntity.ok().build();
    }
}
