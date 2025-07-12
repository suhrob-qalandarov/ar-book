package org.example.arbook.model.dto.response.order;

import lombok.Builder;
import org.example.arbook.model.dto.response.AdminBookRes;

import java.util.List;

@Builder
public record AcceptedOrderItemRes(
        Integer amount,
        AdminBookRes adminBookRes,
        List<String> qrCodes
) {
}
