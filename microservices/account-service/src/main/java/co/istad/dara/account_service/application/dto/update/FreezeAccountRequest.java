package co.istad.dara.account_service.application.dto.update;

import co.istad.dara.account_service.domain.valueobject.AccountStatus;
import co.istad.dara.common.domain.valueobject.CustomerId;

public record FreezeAccountRequest(
        CustomerId customerId,
        String remark,
        String requestedBy
) {
}
