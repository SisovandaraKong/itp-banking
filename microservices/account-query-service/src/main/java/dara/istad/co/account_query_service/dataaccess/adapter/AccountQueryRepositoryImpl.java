package dara.istad.co.account_query_service.dataaccess.adapter;

import dara.istad.co.account_query_service.applicationservie.ports.output.repository.AccountQueryRepository;
import dara.istad.co.account_query_service.dataaccess.entity.AccountEntity;
import dara.istad.co.account_query_service.dataaccess.mapper.AccountDataAccessMapper;
import dara.istad.co.account_query_service.dataaccess.repository.AccountReactiveRepository;
import dara.istad.co.account_query_service.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountQueryRepositoryImpl implements AccountQueryRepository {

    private final AccountReactiveRepository accountReactiveRepository;
    private final AccountDataAccessMapper accountDataAccessMapper;

    @Override
    public Mono<Account> save(Account account) {

        AccountEntity accountEntity = accountDataAccessMapper
                .accountToAccountEntity(account);

        return accountReactiveRepository.save(accountEntity)
                .map(accountDataAccessMapper::accountEntityToAccount);
    }

    @Override
    public Mono<Account> findById(UUID accountId) {
        return accountReactiveRepository.findById(accountId)
                .map(accountDataAccessMapper::accountEntityToAccount);
    }
}
