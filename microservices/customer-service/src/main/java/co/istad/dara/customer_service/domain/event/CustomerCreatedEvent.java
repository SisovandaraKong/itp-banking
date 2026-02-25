package co.istad.dara.customer_service.domain.event;

import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.common.domain.valueobject.CustomerSegmentId;
import co.istad.dara.customer_service.domain.valueobject.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CustomerCreatedEvent (
        CustomerId customerId,
        CustomerName name,
        CustomerEmail email,
        CustomerGender gender,
        String phoneNumber,
        LocalDate dob,
        Kyc kyc,
        Address address,
        Contact contact,
        CustomerSegmentId customerSegmentId
) {
}
