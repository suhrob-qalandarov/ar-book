package org.example.arbook.model.dto.response;

import lombok.Builder;

@Builder
public record CategoryRes(Long id, String name) {
}
