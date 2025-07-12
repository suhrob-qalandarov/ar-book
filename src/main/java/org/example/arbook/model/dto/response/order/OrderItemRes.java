package org.example.arbook.model.dto.response.order;

import lombok.Builder;
import org.example.arbook.model.dto.response.AdminBookRes;

@Builder
public record OrderItemRes(
        Integer amount,
        AdminBookRes adminBookRes
) {
}
