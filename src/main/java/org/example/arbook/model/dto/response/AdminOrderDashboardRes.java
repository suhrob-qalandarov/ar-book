package org.example.arbook.model.dto.response;

import lombok.Builder;
import org.example.arbook.model.dto.response.order.AcceptedOrderRes;
import org.example.arbook.model.dto.response.order.OrderRes;

import java.util.List;

@Builder
public record AdminOrderDashboardRes (
        Long pendingCount,
        Long acceptedCount,
        Long declinedCount,
        List<OrderRes> pendingOrders,
        List<AcceptedOrderRes> acceptedOrders,
        List<OrderRes> declinedOrders
) {
}
