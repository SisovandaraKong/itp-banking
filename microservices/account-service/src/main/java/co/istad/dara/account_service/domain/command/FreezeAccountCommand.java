package co.istad.dara.account_service.domain.command;

import co.istad.dara.common.domain.valueobject.AccountId;
import co.istad.dara.common.domain.valueobject.CustomerId;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
public record FreezeAccountCommand(
        @TargetAggregateIdentifier
        AccountId accountId,
        CustomerId customerId,
        String remark,
        String requestBy
) {
}
