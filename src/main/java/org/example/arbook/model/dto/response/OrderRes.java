package org.example.arbook.model.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderRes(
        Long id,
        String name,
        String status,
        UserRes userRes,
        List<OrderItemRes> orderItemResList
) {
}
