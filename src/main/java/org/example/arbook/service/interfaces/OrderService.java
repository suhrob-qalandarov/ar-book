package org.example.arbook.service.interfaces;

import org.example.arbook.model.dto.request.OrderReq;
import org.example.arbook.model.dto.response.order.AcceptedOrderRes;
import org.example.arbook.model.dto.response.AdminOrderDashboardRes;
import org.example.arbook.model.dto.response.order.OrderRes;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OrderService {

    OrderRes createOrder(OrderReq orderReq);

    AdminOrderDashboardRes getOrders();

    OrderRes getOrderRes(UUID orderId);

    OrderRes getOrderResByName(String orderName);

    AcceptedOrderRes acceptOrderAndGetQrCodes(UUID orderId);

    OrderRes declineOrder(UUID orderId);

    AcceptedOrderRes getAcceptedOrderRes(UUID orderId);

    OrderRes pendOrder(UUID orderId);

    AcceptedOrderRes blockOrder(UUID orderId);

    AcceptedOrderRes unblockOrder(UUID orderId);

    AcceptedOrderRes setImageToAcceptedOrder(UUID orderId, UUID imageId);
}
