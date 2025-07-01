package org.example.arbook.model.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.arbook.model.enums.BookStatus;

public record BookPageStatusChangeReq(
        @NotNull(message = "Status must not be null")
        BookStatus status
) {
}
