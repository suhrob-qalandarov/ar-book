package org.example.arbook.model.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderReq(
        String name,
        Long userId,
        List<OrderItemReq> orderItemReqList
) {
}
