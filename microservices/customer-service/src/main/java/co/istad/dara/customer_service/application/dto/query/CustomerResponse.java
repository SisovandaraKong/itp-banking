package co.istad.dara.customer_service.application.dto.query;

import co.istad.dara.common.domain.valueobject.CustomerSegmentId;
import co.istad.dara.customer_service.domain.valueobject.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerResponse (

        UUID customerId,

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
        CustomerSegmentResponse customerSegment
){
}
