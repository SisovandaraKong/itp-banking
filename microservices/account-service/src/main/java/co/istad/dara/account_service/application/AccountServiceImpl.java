package co.istad.dara.account_service.application;

import co.istad.dara.account_service.application.dto.create.CreateAccountRequest;
import co.istad.dara.account_service.application.dto.create.CreateAccountResponse;
import co.istad.dara.account_service.application.dto.update.DepositMoneyRequest;
import co.istad.dara.account_service.application.dto.update.DepositResponse;
import co.istad.dara.account_service.application.mapper.AccountApplicationMapper;
import co.istad.dara.account_service.domain.command.CreateAccountCommand;
import co.istad.dara.account_service.domain.command.DepositMoneyCommand;
import co.istad.dara.common.domain.valueobject.AccountId;
import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.common.domain.valueobject.TransactionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{
    private final AccountApplicationMapper accountApplicationMapper;
    private final CommandGateway commandGateway;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        CreateAccountCommand createAccountCommand = accountApplicationMapper
                .createAccountRequestToCreateAccountCommand(new AccountId(UUID.randomUUID()), createAccountRequest);
        log.info("CreateAccountCommand: {}", createAccountCommand);

        AccountId result = commandGateway.sendAndWait(createAccountCommand);
        log.info("CommandGateway Result: {}", result);

        return CreateAccountResponse.builder()
                .accountId(createAccountCommand.accountId().getValue())
                .message("Account created successfully")
                .build();
    }

    @Override
    public DepositResponse depositMoney(AccountId accountId, DepositMoneyRequest depositMoneyRequest) {
        log.info("DepositMoneyRequest: {}", depositMoneyRequest);

        DepositMoneyCommand depositMoneyCommand = DepositMoneyCommand.builder()
                .customerId(depositMoneyRequest.customerId())
                .accountId(accountId)
                .transactionId(new TransactionId(UUID.randomUUID()))
                .amount(depositMoneyRequest.amount())
                .remark(depositMoneyRequest.remark())
                .build();
        return DepositResponse.builder()
                .accountId(depositMoneyCommand.accountId())
                .amount(depositMoneyCommand.amount())
                .message("Deposit " + depositMoneyRequest.amount() + depositMoneyCommand.amount().currency() +
                 "Successfully")
                .build();
    }

}
