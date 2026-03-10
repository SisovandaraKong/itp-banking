package dara.istad.co.account_query_service.applicationservie.dto;

import java.util.UUID;

public record AccountQueryResponse(
        UUID accountId,
        String accountNo
) {
}
