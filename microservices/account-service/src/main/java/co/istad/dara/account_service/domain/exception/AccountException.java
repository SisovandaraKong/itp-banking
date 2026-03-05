package co.istad.dara.account_service.domain.exception;

import co.istad.dara.common.exception.DomainException;

public class AccountException extends DomainException {

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
