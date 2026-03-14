package co.istad.dara.account_service.applicationservice.dto.update;

import co.istad.dara.common.domain.valueobject.AccountId;
import lombok.Builder;

@Builder
public record FreezeAccountResponse(
        AccountId accountId,
        String message
) {
}
