package org.example.arbook.controller.admin.order;

import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.response.OrderRes;
import org.example.arbook.service.interfaces.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.arbook.util.ApiConstants.*;

/**
 * Admin Controller for handling order endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + ADMIN + ORDER)
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderRes> getOrderRes(@PathVariable Long orderId) {
        OrderRes order = orderService.getOrderRes(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/{orderName}")
    public ResponseEntity<OrderRes> getOrderResByName(@PathVariable String orderName) {
        OrderRes order = orderService.getOrderResByName(orderName);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/accept/{orderId}")
    public ResponseEntity<OrderRes> acceptOrder(@PathVariable Long orderId) {
        OrderRes order = orderService.acceptOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/decline/{orderId}")
    public ResponseEntity<OrderRes> declineOrder(@PathVariable Long orderId) {
        OrderRes order = orderService.declineOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }
}
