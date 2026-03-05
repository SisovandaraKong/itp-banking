package co.istad.dara.account_service.domain.exception;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ErrorResponse(
        String status,
        String code,
        String description,
        ZonedDateTime time
) {
}
