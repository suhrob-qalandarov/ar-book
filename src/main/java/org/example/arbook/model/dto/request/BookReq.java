package org.example.arbook.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
@Schema(description = "Request DTO for creating a book")
public record BookReq(

        @NotBlank(message = "Title can not be blank")
        @Schema(description = "The text of the book", example = "The Great Gatsby")
        String title,

        @NotBlank(message = "Description can not be blank")
        @Schema(description = "A brief description of the book", example = "A classic novel about the American Dream")
        String description,

        @NotNull(message = "Total pages cannot be null")
        @Positive(message = "Total pages must be positive and greater than 0")
        @Schema(description = "Total number of pages in the book", example = "180")
        Integer totalPages,

        @NotNull(message = "Total languages cannot be null")
        @Positive(message = "Total languages must be positive and greater than 0")
        @Schema(description = "Total number of languages the book is available in", example = "2")
        Integer totalLanguages,

        @NotNull(message = "Category ID cannot be null")
        @Positive(message = "Category ID must be positive and greater than 0")
        @Schema(description = "ID of the book's category", example = "1")
        Long categoryId,

        @NotNull(message = "Attachment ID cannot be null")
        @Positive(message = "Attachment ID must be positive and greater than 0")
        @Schema(description = "ID of the book's attachment (e.g., cover image)", example = "1")
        Long attachmentId
) {
}
