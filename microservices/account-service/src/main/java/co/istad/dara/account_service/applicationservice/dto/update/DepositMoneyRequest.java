package co.istad.dara.account_service.applicationservice.dto.update;

import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.common.domain.valueobject.Money;

public record DepositMoneyRequest(
        CustomerId customerId,
        Money amount,
        String remark
) {
}
