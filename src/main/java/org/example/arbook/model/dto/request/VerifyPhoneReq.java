package org.example.arbook.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record VerifyPhoneReq(
        @NotBlank(message = "Phone number cannot be empty")
        @Pattern(regexp = "^\\+998(90|91|93|94|50|55|87|88|97|95|99|77|98|33)\\d{7}$",
                message = "Phone number is in an invalid format, e.g., +998901234567")
        String phoneNumber,

        @NotBlank(message = "SMS code cannot be empty")
        @Pattern(regexp = "^\\d{6}$", message = "SMS code must be a 6 digit number")
        String code
) {
}
