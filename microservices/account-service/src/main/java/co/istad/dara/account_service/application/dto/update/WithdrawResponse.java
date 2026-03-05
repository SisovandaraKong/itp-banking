package co.istad.dara.account_service.application.dto.update;

import co.istad.dara.common.domain.valueobject.AccountId;
import co.istad.dara.common.domain.valueobject.Money;
import lombok.Builder;

@Builder
public record WithdrawResponse(
        AccountId accountId,
        Money amount,
        String message
) {
}
