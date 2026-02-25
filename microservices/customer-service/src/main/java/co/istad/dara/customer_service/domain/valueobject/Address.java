package co.istad.dara.customer_service.domain.valueobject;

import java.util.UUID;

public record Address(
        UUID addressId,
        String line,
        String city,
        String zipCode,
        String country
) {
}
