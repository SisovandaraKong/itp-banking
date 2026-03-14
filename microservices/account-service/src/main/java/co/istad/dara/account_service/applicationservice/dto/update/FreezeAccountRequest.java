package co.istad.dara.account_service.applicationservice.dto.update;

import co.istad.dara.common.domain.valueobject.CustomerId;

public record FreezeAccountRequest(
        CustomerId customerId,
        String remark,
        String requestedBy
) {
}
