package co.istad.dara.account_service.applicationservice.ports.input.service;

import co.istad.dara.account_service.applicationservice.dto.create.CreateAccountRequest;
import co.istad.dara.account_service.applicationservice.dto.create.CreateAccountResponse;
import co.istad.dara.account_service.applicationservice.dto.update.*;

import java.util.UUID;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);
    DepositResponse depositMoney(UUID accountId, DepositMoneyRequest depositMoneyRequest);
    WithdrawResponse withdrawMoney(UUID accountId, WithdrawMoneyRequest withdrawMoneyRequest);
    FreezeAccountResponse freezeAccount(UUID accountId, FreezeAccountRequest freezeAccountRequest);
}
