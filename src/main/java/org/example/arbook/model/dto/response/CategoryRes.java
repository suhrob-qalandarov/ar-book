package org.example.arbook.model.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryRes(UUID id, String name) {
}
