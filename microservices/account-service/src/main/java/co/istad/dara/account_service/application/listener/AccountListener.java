package co.istad.dara.account_service.application.listener;

import co.istad.dara.account_service.application.mapper.AccountApplicationMapper;
import co.istad.dara.account_service.data.entity.AccountEntity;
import co.istad.dara.account_service.data.entity.AccountTypeEntity;
import co.istad.dara.account_service.data.repository.AccountRepository;
import co.istad.dara.account_service.data.repository.AccountTypeRepository;
import co.istad.dara.account_service.data.repository.BranchRepository;
import co.istad.dara.account_service.domain.event.AccountCreatedEvent;
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
}
