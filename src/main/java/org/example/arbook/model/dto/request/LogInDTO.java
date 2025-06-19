package org.example.arbook.model.dto.request;

import lombok.Value;

@Value
public class LogInDTO {
    private String phoneNumber;
    private String password;
}
