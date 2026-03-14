package co.istad.dara.account_service.rest;

import co.istad.dara.account_service.applicationservice.ports.input.service.AccountService;
import co.istad.dara.account_service.applicationservice.dto.create.CreateAccountRequest;
import co.istad.dara.account_service.applicationservice.dto.create.CreateAccountResponse;
import co.istad.dara.account_service.applicationservice.dto.update.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateAccountResponse createAccount (@Valid @RequestBody CreateAccountRequest createAccountRequest){
        log.info("CreateAccountRequest: {}", createAccountRequest);
        return accountService.createAccount(createAccountRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{accountId}/deposit")
    public DepositResponse depositMoney (@PathVariable UUID accountId, @RequestBody DepositMoneyRequest depositMoneyRequest){
        log.info("DepositMoney: {}", depositMoneyRequest);

        return accountService.depositMoney(accountId,depositMoneyRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{accountId}/withdraw")
    public WithdrawResponse withdrawMoney (@PathVariable UUID accountId, @RequestBody WithdrawMoneyRequest withdrawMoneyRequest){
        log.info("WithdrawMoney: {}", withdrawMoneyRequest);

        return accountService.withdrawMoney(accountId, withdrawMoneyRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{accountId}/freeze")
    public FreezeAccountResponse freezeAccount (@PathVariable UUID accountId, @RequestBody FreezeAccountRequest freezeAccountRequest){
        log.info("FreezeAccountRequest: {}", freezeAccountRequest);

        return accountService.freezeAccount(accountId, freezeAccountRequest);
    }
}
