package co.istad.dara.account_service.application.dto.update;

import co.istad.dara.common.domain.valueobject.AccountId;
import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.common.domain.valueobject.Money;
import co.istad.dara.common.domain.valueobject.TransactionId;

public record DepositMoneyRequest(
        CustomerId customerId,
        Money amount,
        String remark
) {
}
