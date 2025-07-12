package org.example.arbook.controller.admin.order;

import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.response.AdminOrderDashboardRes;
import org.example.arbook.service.interfaces.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.arbook.util.ApiConstants.*;

/**
 * Admin Controller for handling orders endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + ADMIN + ORDERS)
public class AdminOrdersController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<AdminOrderDashboardRes> getDashboardOrders() {
        AdminOrderDashboardRes orders = orderService.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
