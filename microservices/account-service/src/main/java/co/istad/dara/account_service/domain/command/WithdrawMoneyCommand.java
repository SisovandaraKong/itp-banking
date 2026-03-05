package co.istad.dara.account_service.domain.command;

import co.istad.dara.common.domain.valueobject.AccountId;
import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.common.domain.valueobject.Money;
import co.istad.dara.common.domain.valueobject.TransactionId;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.ZonedDateTime;

@Builder
public record WithdrawMoneyCommand(
        @TargetAggregateIdentifier
        AccountId accountId,
        CustomerId customerId,
        TransactionId transactionId,
        Money amount,
        String remark
) {
}
