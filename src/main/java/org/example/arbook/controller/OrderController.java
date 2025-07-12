package org.example.arbook.controller;

import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.OrderReq;
import org.example.arbook.model.dto.response.order.OrderRes;
import org.example.arbook.service.interfaces.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.arbook.util.ApiConstants.*;

/**
 * Controller for handling order endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + ORDER)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderRes> createOrder(@RequestBody OrderReq orderReq) {
        OrderRes order = orderService.createOrder(orderReq);
        return ResponseEntity.ok(order);
    }
}
