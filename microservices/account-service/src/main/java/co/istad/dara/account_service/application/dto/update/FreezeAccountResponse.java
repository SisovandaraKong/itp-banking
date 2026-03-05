package co.istad.dara.account_service.application.dto.update;

import co.istad.dara.account_service.domain.valueobject.AccountStatus;
import co.istad.dara.common.domain.valueobject.AccountId;
import lombok.Builder;

@Builder
public record FreezeAccountResponse(
        AccountId accountId,
        String message
) {
}
