package co.istad.dara.account_service.application;

import co.istad.dara.account_service.application.dto.create.CreateAccountRequest;
import co.istad.dara.account_service.application.dto.create.CreateAccountResponse;
import co.istad.dara.account_service.application.dto.update.*;
import co.istad.dara.common.domain.valueobject.AccountId;

import java.util.UUID;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);
    DepositResponse depositMoney(UUID accountId, DepositMoneyRequest depositMoneyRequest);
    WithdrawResponse withdrawMoney(UUID accountId, WithdrawMoneyRequest withdrawMoneyRequest);
    FreezeAccountResponse freezeAccount(UUID accountId, FreezeAccountRequest freezeAccountRequest);
}
