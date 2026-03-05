package co.istad.dara.account_service.application;

import co.istad.dara.account_service.application.dto.create.CreateAccountRequest;
import co.istad.dara.account_service.application.dto.create.CreateAccountResponse;
import co.istad.dara.account_service.application.dto.update.DepositMoneyRequest;
import co.istad.dara.account_service.application.dto.update.DepositResponse;
import co.istad.dara.common.domain.valueobject.AccountId;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);
    DepositResponse depositMoney(AccountId accountId, DepositMoneyRequest depositMoneyRequest);
}
