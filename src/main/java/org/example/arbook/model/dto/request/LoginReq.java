package org.example.arbook.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Data Transfer Object for user login requests.
 */
@Builder
public record LoginReq(
        @NotBlank(message = "Phone number cannot be empty")
        @Pattern(regexp = "^\\+998(90|91|93|94|50|55|87|88|97|95|99|77|98|33)\\d{7}$",
                message = "Phone number is in an invalid format, e.g., +998901234567")
        String phoneNumber,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
        String password
) {
}