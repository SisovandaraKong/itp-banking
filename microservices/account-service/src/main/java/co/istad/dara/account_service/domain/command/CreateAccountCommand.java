package co.istad.dara.account_service.domain.command;

import co.istad.dara.account_service.domain.valueobject.AccountTypeCode;
import co.istad.dara.common.domain.valueobject.Money;
import co.istad.dara.common.domain.valueobject.AccountId;
import co.istad.dara.common.domain.valueobject.BranchId;
import co.istad.dara.common.domain.valueobject.CustomerId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateAccountCommand (
        @TargetAggregateIdentifier
        AccountId accountId,
        String accountNumber,
        String accountHolder,
        CustomerId customerId,
        AccountTypeCode accountTypeCode,
        BranchId branchId,
        Money initialBalance
){

}
