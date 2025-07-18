package org.example.arbook.model.dto.response;

import lombok.Builder;
import org.example.arbook.model.enums.ContentType;

import java.util.UUID;

@Builder
public record AttachmentRes(
        UUID id,
        String fileUrl,
        ContentType contentType
) {
}
