package co.istad.dara.account_service.domain.aggregate;

import co.istad.dara.account_service.domain.command.CreateAccountCommand;
import co.istad.dara.account_service.domain.command.DepositMoneyCommand;
import co.istad.dara.account_service.domain.event.AccountCreatedEvent;
import co.istad.dara.account_service.domain.event.MoneyDepositedEvent;
import co.istad.dara.account_service.domain.exception.AccountException;
import co.istad.dara.account_service.domain.validate.AccountValidate;
import co.istad.dara.account_service.domain.valueobject.AccountStatus;
import co.istad.dara.account_service.domain.valueobject.AccountTypeCode;
import co.istad.dara.common.domain.valueobject.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;


import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Aggregate
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    AccountId accountId;
    String accountNumber;
    String accountHolder;
    CustomerId customerId;
    AccountTypeCode accountTypeCode;
    BranchId branchId;
    Money balance;
    AccountStatus accountStatus;
    ZonedDateTime createdAt;
    String createdBy;
    ZonedDateTime updatedAt;
    String updatedBy;

    void validateInitMoney(Money initMoney){
        Money money = new Money(BigDecimal.valueOf(10), Currency.USD);
        if (initMoney.isLessThanOrEqual(money)){
            throw new AccountException("Create account need to init 10$");
        }
    }

    void savingAccountTypeFirst(AccountTypeCode accountTypeCode){
        if (!accountTypeCode.equals(AccountTypeCode.SAVING)){
            throw new AccountException("First create account should be saving");
        }
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand){
        log.info("Aggregate on create account command: {}",createAccountCommand);

        // Validate account number
        AccountValidate.validateAccountNumber(createAccountCommand.accountNumber());

        // Validate balance
        validateInitMoney(createAccountCommand.initialBalance());

        // Validate account type
        savingAccountTypeFirst(createAccountCommand.accountTypeCode());

        // Create event
        AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent.builder()
                .accountId(createAccountCommand.accountId())
                .accountNumber(createAccountCommand.accountNumber())
                .accountHolder(createAccountCommand.accountHolder())
                .customerId(createAccountCommand.customerId())
                .accountTypeCode(createAccountCommand.accountTypeCode())
                .accountStatus(AccountStatus.ACTIVE)
                .createdBy("ADMIN")
                .createdAt(ZonedDateTime.now())
                .branchId(createAccountCommand.branchId())
                .initialBalance(createAccountCommand.initialBalance())
                .build();

        AggregateLifecycle.apply(accountCreatedEvent);
    }

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        this.accountId = accountCreatedEvent.accountId();
        this.accountHolder = accountCreatedEvent.accountHolder();
        this.accountNumber = accountCreatedEvent.accountNumber();
        this.customerId = accountCreatedEvent.customerId();
        this.accountTypeCode = accountCreatedEvent.accountTypeCode();
        this.accountStatus = accountCreatedEvent.accountStatus();
        this.branchId = accountCreatedEvent.branchId();
        this.balance = accountCreatedEvent.initialBalance();
        this.createdAt = accountCreatedEvent.createdAt();
        this.createdBy = accountCreatedEvent.createdBy();
    }

    @CommandHandler
    public AccountId handler(DepositMoneyCommand depositMoneyCommand){
        log.info("DepositMoneyCommand: {}", depositMoneyCommand);

        MoneyDepositedEvent moneyDepositedEvent = MoneyDepositedEvent.builder()
                .accountId(depositMoneyCommand.accountId())
                .build();

        return this.accountId;
    }

}
