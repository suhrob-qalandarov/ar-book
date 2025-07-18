package org.example.arbook.model.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderItemReq(
        UUID bookId,
        Integer amount
) {
}
