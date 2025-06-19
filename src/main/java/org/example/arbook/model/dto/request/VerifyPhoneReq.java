package org.example.arbook.model.dto.request;

import lombok.Builder;

@Builder
public record VerifyPhoneReq(String phoneNumber, String code) {
}
