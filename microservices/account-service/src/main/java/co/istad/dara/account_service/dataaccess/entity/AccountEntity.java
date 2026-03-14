//package co.istad.dara.account_service.dataaccess.entity;
//
//import co.istad.dara.common.domain.valueobject.AccountStatus;
//import co.istad.dara.common.domain.valueobject.AccountTypeCode;
//import co.istad.dara.common.domain.valueobject.Currency;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Version;
//import org.springframework.data.relational.core.mapping.Table;
//
//import java.math.BigDecimal;
//import java.time.ZonedDateTime;
//import java.util.UUID;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "accounts")
//public class AccountEntity {
//
//    @Id
//    private UUID accountId;
//    private UUID customerId;
//    private UUID branchId;
//
//    private String accountNumber;
//    private String accountHolder;
//
//    private BigDecimal balance;
//    private Currency currency;
//
//    private AccountStatus accountStatus;
//
//    private ZonedDateTime createdAt;
//    private String createdBy;
//    private ZonedDateTime updatedAt;
//    private String updatedBy;
//
//    private AccountTypeCode accountTypeCode;
//
//    @Version
//    private Long version;
//}
