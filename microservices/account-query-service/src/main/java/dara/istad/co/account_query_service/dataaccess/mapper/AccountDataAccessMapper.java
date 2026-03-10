package dara.istad.co.account_query_service.dataaccess.mapper;

import dara.istad.co.account_query_service.dataaccess.entity.AccountEntity;
import dara.istad.co.account_query_service.domain.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountDataAccessMapper {

    AccountEntity accountToAccountEntity(Account account);
    Account accountEntityToAccount(AccountEntity accountEntity);
}
