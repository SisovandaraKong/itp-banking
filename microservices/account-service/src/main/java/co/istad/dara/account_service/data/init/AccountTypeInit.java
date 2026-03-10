package co.istad.dara.account_service.data.init;

import co.istad.dara.account_service.data.entity.AccountTypeEntity;
import co.istad.dara.account_service.data.repository.AccountTypeRepository;
import co.istad.dara.common.domain.valueobject.AccountTypeCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountTypeInit {

    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    public void initAccountType(){
        if (accountTypeRepository.count() == 0){

            AccountTypeEntity saving = new AccountTypeEntity();
            saving.setAccountTypeId(UUID.randomUUID());
            saving.setAccountTypeCode(AccountTypeCode.SAVING);

            AccountTypeEntity payroll = new AccountTypeEntity();
            payroll.setAccountTypeId(UUID.randomUUID());
            payroll.setAccountTypeCode(AccountTypeCode.PAYROLL);

            AccountTypeEntity loan = new AccountTypeEntity();
            loan.setAccountTypeId(UUID.randomUUID());
            loan.setAccountTypeCode(AccountTypeCode.LOAN);

            AccountTypeEntity checking = new AccountTypeEntity();
            checking.setAccountTypeId(UUID.randomUUID());
            checking.setAccountTypeCode(AccountTypeCode.CHECKING);

            accountTypeRepository.saveAll(List.of(saving,payroll,loan,checking));
        }
    }

}
