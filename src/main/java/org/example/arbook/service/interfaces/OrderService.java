package org.example.arbook.service.interfaces;

import org.example.arbook.model.dto.request.OrderReq;
import org.example.arbook.model.dto.response.AdminOrderDashboardRes;
import org.example.arbook.model.dto.response.OrderRes;
import org.example.arbook.model.entity.QrCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<QrCode> createOrderAndGetQrCodes(OrderReq orderReq);

    AdminOrderDashboardRes getOrders();

    OrderRes getOrderRes(Long orderId);

    OrderRes getOrderResByName(String orderName);

    OrderRes acceptOrder(Long orderId);

    OrderRes declineOrder(Long orderId);
}
