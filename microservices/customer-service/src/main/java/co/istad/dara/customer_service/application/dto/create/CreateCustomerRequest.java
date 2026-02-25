package co.istad.dara.customer_service.application.dto.create;

import co.istad.dara.common.domain.valueobject.CustomerSegmentId;
import co.istad.dara.customer_service.domain.valueobject.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateCustomerRequest(
        @NotNull
        CustomerName name,

        @NotNull
        CustomerEmail email,

        @NotNull
        LocalDate dob,

        @NotNull
        CustomerGender gender,

        @NotNull
        Kyc kyc,

        @NotNull
        Address address,

        @NotNull
        Contact contact,

        @NotBlank
        String phoneNumber,

        @NotNull
        CustomerSegmentId customerSegmentId
) {
}
