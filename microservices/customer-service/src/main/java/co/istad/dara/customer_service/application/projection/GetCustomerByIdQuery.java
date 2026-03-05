package co.istad.dara.customer_service.application.projection;

import java.util.UUID;

public record GetCustomerByIdQuery(
        UUID customerId
) {
}