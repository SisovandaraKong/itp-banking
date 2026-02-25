package co.istad.dara.customer_service.domain.command;

import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.common.domain.valueobject.CustomerSegmentId;
import co.istad.dara.customer_service.domain.valueobject.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;

public record CreateCustomerCommand(
        @TargetAggregateIdentifier
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
