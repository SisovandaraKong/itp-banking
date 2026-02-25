package co.istad.dara.customer_service.domain.event;

import co.istad.dara.common.domain.valueobject.CustomerId;
import lombok.Builder;

@Builder
public record CustomerPhoneNumberChangedEvent(
        CustomerId customerId,
        String phoneNumber
) {
}
