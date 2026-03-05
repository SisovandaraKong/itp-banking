package co.istad.dara.account_service.application.dto.client;

import java.util.Map;
import java.util.UUID;

public record CustomerResponse(
        UUID customerId
) {
}