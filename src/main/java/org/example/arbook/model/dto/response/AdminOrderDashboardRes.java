package org.example.arbook.model.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AdminOrderDashboardRes (
        Long pendingCount,
        Long acceptedCount,
        List<OrderRes> pendingOrders,
        List<OrderRes> acceptedOrders
) {
}
