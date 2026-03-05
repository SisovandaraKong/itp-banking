package co.istad.dara.account_service.rest;

import co.istad.dara.account_service.application.AccountService;
import co.istad.dara.account_service.application.dto.create.CreateAccountRequest;
import co.istad.dara.account_service.application.dto.create.CreateAccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
