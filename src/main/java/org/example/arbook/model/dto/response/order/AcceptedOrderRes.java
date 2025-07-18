package org.example.arbook.model.dto.response.order;

import lombok.Builder;
import org.example.arbook.model.dto.response.UserRes;

import java.util.List;
import java.util.UUID;

@Builder
public record AcceptedOrderRes(
        UUID id,
        String name,
        String status,
        UserRes userRes,
        List<AcceptedOrderItemRes> orderItemList
) {
}
