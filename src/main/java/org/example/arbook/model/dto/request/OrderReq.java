package org.example.arbook.model.dto.request;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record OrderReq(
        String name,
        UUID userId,
        List<OrderItemReq> orderItemReqList
) {
}
