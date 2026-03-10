package dara.istad.co.account_query_service.applicationservie.mapper;

import co.istad.dara.common.domain.event.AccountCreatedEvent;
import dara.istad.co.account_query_service.applicationservie.dto.AccountQueryResponse;
import dara.istad.co.account_query_service.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountAppDataMapper {

    AccountQueryResponse accountToAccountQueryResponse(Account account);

    @Mapping(source = "accountId.value", target = "accountId")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "branchId.value", target = "branchId")
    @Mapping(source = "initialBalance", target = "money")
    Account accountCreatedEventToAccount(AccountCreatedEvent accountCreatedEvent);
}
