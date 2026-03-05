package co.istad.dara.account_service.application.dto.update;

import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.common.domain.valueobject.Money;

public record WithdrawMoneyRequest(
        CustomerId customerId,
        Money amount,
        String remark
) {
}
