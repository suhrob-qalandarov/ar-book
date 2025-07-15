package org.example.arbook.service.interfaces;

import org.example.arbook.model.dto.request.OrderReq;
import org.example.arbook.model.dto.response.order.AcceptedOrderRes;
import org.example.arbook.model.dto.response.AdminOrderDashboardRes;
import org.example.arbook.model.dto.response.order.OrderRes;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderRes createOrder(OrderReq orderReq);

    AdminOrderDashboardRes getOrders();

    OrderRes getOrderRes(Long orderId);

    OrderRes getOrderResByName(String orderName);

    AcceptedOrderRes acceptOrderAndGetQrCodes(Long orderId);

    OrderRes declineOrder(Long orderId);

    AcceptedOrderRes getAcceptedOrderRes(Long orderId);

    OrderRes pendOrder(Long orderId);
}
