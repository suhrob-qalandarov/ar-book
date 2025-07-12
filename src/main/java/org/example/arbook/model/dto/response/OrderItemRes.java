package org.example.arbook.model.dto.response;

import lombok.Builder;

@Builder
public record OrderItemRes(
        Integer amount,
        AdminBookRes adminBookRes
) {
}
