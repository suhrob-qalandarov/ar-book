package org.example.arbook.model.dto.response.order;

import lombok.Builder;
import org.example.arbook.model.dto.response.UserRes;

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
