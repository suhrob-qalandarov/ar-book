package org.example.arbook.model.dto.response;

import lombok.Builder;
import org.example.arbook.model.enums.ContentType;

@Builder
public record AttachmentRes(
        Long id,
        String fileUrl,
        ContentType contentType
) {
}
