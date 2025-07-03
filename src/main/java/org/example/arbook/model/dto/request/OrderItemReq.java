package org.example.arbook.model.dto.request;

import lombok.Builder;

@Builder
public record OrderItemReq(
        Long bookId,
        Integer amount
) {
}
