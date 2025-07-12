package org.example.arbook.model.dto.response;

import lombok.Builder;
import org.example.arbook.model.dto.response.order.AcceptedOrderRes;
import org.example.arbook.model.dto.response.order.OrderRes;

import java.util.List;

@Builder
public record AdminOrderDashboardRes (
        Long pendingCount,
        Long acceptedCount,
        List<OrderRes> pendingOrders,
        List<AcceptedOrderRes> acceptedOrders
) {
}
