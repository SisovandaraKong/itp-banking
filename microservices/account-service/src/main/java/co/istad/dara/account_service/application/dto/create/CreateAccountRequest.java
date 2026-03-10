package co.istad.dara.account_service.application.dto.create;

import co.istad.dara.common.domain.valueobject.AccountTypeCode;
import co.istad.dara.common.domain.valueobject.Money;
import co.istad.dara.common.domain.valueobject.BranchId;
import co.istad.dara.common.domain.valueobject.CustomerId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
        @NotNull
        CustomerId customerId,
        @NotNull
        BranchId branchId,
        @NotBlank
        String accountNumber,
        @NotBlank
        String accountHolder,
        @NotNull
        Money balance,
        @NotNull
        AccountTypeCode accountType
) {
}
