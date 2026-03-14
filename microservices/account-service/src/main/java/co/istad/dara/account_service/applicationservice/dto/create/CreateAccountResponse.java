package co.istad.dara.account_service.applicationservice.dto.create;

import lombok.Builder;

import java.util.UUID;


@Builder
public record CreateAccountResponse(
        UUID accountId,
        String message
) {
}
