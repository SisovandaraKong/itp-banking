package co.istad.dara.account_service.applicationservice.mapper;

import co.istad.dara.account_service.applicationservice.dto.create.CreateAccountRequest;

import co.istad.dara.account_service.domain.command.CreateAccountCommand;
import co.istad.dara.common.domain.event.AccountCreatedEvent;
import co.istad.dara.common.domain.valueobject.AccountId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountApplicationMapper {

    @Mapping(source = "createAccountRequest.accountType", target = "accountTypeCode")
    @Mapping(source = "createAccountRequest.balance", target = "initialBalance")
    CreateAccountCommand createAccountRequestToCreateAccountCommand(AccountId accountId, CreateAccountRequest createAccountRequest);

//    @Mapping(source = "accountId.value", target = "accountId")
//    @Mapping(source = "customerId.value", target = "customerId")
//    @Mapping(source = "branchId.value", target = "branchId")
//    @Mapping(source = "initialBalance", target = "balance")
//    AccountEntity accountCreatedEventToAccountEntity(AccountCreatedEvent accountCreatedEvent);
//

}
