package co.istad.dara.account_service.domain.event;

import co.istad.dara.common.domain.valueobject.AccountStatus;
import co.istad.dara.common.domain.valueobject.AccountId;
import co.istad.dara.common.domain.valueobject.CustomerId;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record AccountFrozenEvent(
        AccountId accountId,
        CustomerId customerId,
        AccountStatus previousStatus,
        AccountStatus newStatus,
        String reason,
        String requestBy,
        ZonedDateTime createdAt
) {
}
