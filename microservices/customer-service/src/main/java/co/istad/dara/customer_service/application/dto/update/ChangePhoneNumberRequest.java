package co.istad.dara.customer_service.application.dto.update;

import jakarta.validation.constraints.NotBlank;

public record ChangePhoneNumberRequest(
        @NotBlank
        String phoneNumber
) {
}
