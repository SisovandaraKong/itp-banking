package co.istad.dara.common.domain.valueobject;


import co.istad.dara.common.exception.DomainException;

import java.math.BigDecimal;

public record Money(
        BigDecimal amount,
        Currency currency
) {

    public boolean isLessThanOrEqual(Money initMoney){
        checkCurrency(initMoney.currency);
        return this.amount.compareTo(initMoney.amount) <= 0;
    }

    public void checkCurrency(Currency currency){
        if (this.currency != currency){
            throw new DomainException("Currency is invalid");
        }
    }
}
