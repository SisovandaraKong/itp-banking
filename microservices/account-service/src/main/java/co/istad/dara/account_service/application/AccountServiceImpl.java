package co.istad.dara.account_service.application;

import co.istad.dara.account_service.application.dto.create.CreateAccountRequest;
import co.istad.dara.account_service.application.dto.create.CreateAccountResponse;
import co.istad.dara.account_service.application.dto.update.*;
import co.istad.dara.account_service.application.mapper.AccountApplicationMapper;
import co.istad.dara.account_service.domain.command.CreateAccountCommand;
import co.istad.dara.account_service.domain.command.DepositMoneyCommand;
import co.istad.dara.account_service.domain.command.FreezeAccountCommand;
import co.istad.dara.account_service.domain.command.WithdrawMoneyCommand;
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
        log.info("CreateAccountCommand Result: {}", result);

        return CreateAccountResponse.builder()
                .accountId(createAccountCommand.accountId().getValue())
                .message("Account created successfully")
                .build();
    }

    @Override
    public DepositResponse depositMoney(UUID accountId, DepositMoneyRequest depositMoneyRequest) {
        log.info("DepositMoneyRequest: {}", depositMoneyRequest);

        DepositMoneyCommand depositMoneyCommand = DepositMoneyCommand.builder()
                .customerId(depositMoneyRequest.customerId())
                .accountId(new AccountId(accountId))
                .transactionId(new TransactionId(UUID.randomUUID()))
                .amount(depositMoneyRequest.amount())
                .remark(depositMoneyRequest.remark())
                .build();

        AccountId accId = commandGateway.sendAndWait(depositMoneyCommand);
        log.info("DepositMoneyCommand Id: {}", accId);

        return DepositResponse.builder()
                .accountId(depositMoneyCommand.accountId())
                .amount(depositMoneyCommand.amount())
                .message("Deposit " + depositMoneyRequest.amount().amount() + depositMoneyCommand.amount().currency() +
                 " Successfully")
                .build();
    }

    @Override
    public WithdrawResponse withdrawMoney(UUID accountId, WithdrawMoneyRequest withdrawMoneyRequest) {
        log.info("WithdrawMoneyRequest: {}", withdrawMoneyRequest);

        WithdrawMoneyCommand withdrawMoneyCommand = WithdrawMoneyCommand.builder()
                .accountId(new AccountId(accountId))
                .customerId(withdrawMoneyRequest.customerId())
                .transactionId(new TransactionId(UUID.randomUUID()))
                .amount(withdrawMoneyRequest.amount())
                .remark(withdrawMoneyRequest.remark())
                .build();

        AccountId accId = commandGateway.sendAndWait(withdrawMoneyCommand);
        log.info("WithdrawMoneyCommand Id: {}", accId);

        return WithdrawResponse.builder()
                .accountId(withdrawMoneyCommand.accountId())
                .amount(withdrawMoneyCommand.amount())
                .message("Withdraw " + withdrawMoneyCommand.amount().amount() + withdrawMoneyCommand.amount().currency() +
                        " Successfully")
                .build();
    }

    @Override
    public FreezeAccountResponse freezeAccount(UUID accountId, FreezeAccountRequest freezeAccountRequest) {
        log.info("FreezeAccountRequest: {}", freezeAccountRequest);

        FreezeAccountCommand freezeAccountCommand = FreezeAccountCommand.builder()
                .accountId(new AccountId(accountId))
                .customerId(freezeAccountRequest.customerId())
                .remark(freezeAccountRequest.remark())
                .requestBy(freezeAccountRequest.requestedBy())
                .build();

        AccountId accId = commandGateway.sendAndWait(freezeAccountCommand);
        log.info("FreezeAccountCommand Id: {}", accId);

        return FreezeAccountResponse.builder()
                .accountId(freezeAccountCommand.accountId())
                .message("This account frozen successfully")
                .build();
    }

}
