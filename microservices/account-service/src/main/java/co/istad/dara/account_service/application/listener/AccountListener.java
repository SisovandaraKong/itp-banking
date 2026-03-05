package co.istad.dara.account_service.application.listener;

import co.istad.dara.account_service.application.mapper.AccountApplicationMapper;
import co.istad.dara.account_service.data.entity.AccountEntity;
import co.istad.dara.account_service.data.entity.AccountTypeEntity;
import co.istad.dara.account_service.data.repository.AccountRepository;
import co.istad.dara.account_service.data.repository.AccountTypeRepository;
import co.istad.dara.account_service.data.repository.BranchRepository;
import co.istad.dara.account_service.domain.event.AccountCreatedEvent;
import co.istad.dara.account_service.domain.event.AccountFrozenEvent;
import co.istad.dara.account_service.domain.event.MoneyDepositedEvent;
import co.istad.dara.account_service.domain.event.MoneyWithdrawnEvent;
import co.istad.dara.account_service.domain.valueobject.AccountStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
@ProcessingGroup("account-group")
public class AccountListener {
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final BranchRepository branchRepository;
    private final AccountApplicationMapper accountApplicationMapper;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("on AccountCreatedEvent: {}", accountCreatedEvent);

        AccountEntity accountEntity = accountApplicationMapper
                .accountCreatedEventToAccountEntity(accountCreatedEvent);

        AccountTypeEntity accountTypeEntity = accountTypeRepository.findByAccountTypeCode(accountCreatedEvent.accountTypeCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account type code not found"));
        accountEntity.setAccountType(accountTypeEntity);

        accountRepository.save(accountEntity);
    }

    @EventHandler
    public void on(MoneyDepositedEvent moneyDepositedEvent){
        log.info("on MoneyDepositedAccount: {}", moneyDepositedEvent);

        AccountEntity accountEntity = accountRepository.findById(moneyDepositedEvent.accountId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountEntity.setBalance(moneyDepositedEvent.newBalance());
        accountEntity.setUpdatedAt(moneyDepositedEvent.createdAt());

        accountRepository.save(accountEntity);
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent moneyWithdrawnEvent){
        log.info("on MoneyWithdrawnEvent: {}", moneyWithdrawnEvent);

        AccountEntity accountEntity = accountRepository.findById(moneyWithdrawnEvent.accountId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountEntity.setBalance(moneyWithdrawnEvent.newBalance());
        accountEntity.setUpdatedAt(moneyWithdrawnEvent.createdAt());

        accountRepository.save(accountEntity);
    }

    @EventHandler
    public void on(AccountFrozenEvent accountFrozenEvent){
        log.info("on AccountFrozenEvent: {}", accountFrozenEvent);

        AccountEntity accountEntity = accountRepository.findById(accountFrozenEvent.accountId().getValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        accountEntity.setAccountStatus(accountFrozenEvent.newStatus());
        accountEntity.setUpdatedAt(accountFrozenEvent.createdAt());
        accountEntity.setUpdatedBy(accountFrozenEvent.requestBy());

        accountRepository.save(accountEntity);
    }
}
