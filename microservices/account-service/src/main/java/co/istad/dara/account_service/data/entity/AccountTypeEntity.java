package co.istad.dara.account_service.data.entity;

import co.istad.dara.account_service.domain.valueobject.AccountTypeCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account_types")
public class AccountTypeEntity {

    @Id
    private UUID accountTypeId;

    @Enumerated(EnumType.STRING)
    private AccountTypeCode accountTypeCode;

    @OneToMany(mappedBy = "accountType", cascade = CascadeType.ALL)
    private List<AccountEntity> accounts;


}
