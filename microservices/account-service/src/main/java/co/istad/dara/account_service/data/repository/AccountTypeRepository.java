package co.istad.dara.account_service.data.repository;

import co.istad.dara.account_service.data.entity.AccountTypeEntity;
import co.istad.dara.common.domain.valueobject.AccountTypeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountTypeEntity, UUID> {
    Optional<AccountTypeEntity> findByAccountTypeCode(AccountTypeCode accountTypeCode);

    boolean existsByAccountTypeCode(AccountTypeCode accountTypeCode);
}
