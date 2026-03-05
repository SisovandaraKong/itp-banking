package co.istad.dara.account_service.domain.validate;

import co.istad.dara.account_service.domain.exception.AccountException;


public class AccountValidate {
    public static void validateAccountNumber(String accountNumber){
        if (accountNumber == null){
            throw new AccountException("Account number cannot be null");
        }
        if (!accountNumber.matches("^\\d{9}$")){
            throw new AccountException("Account number is invalid");
        }
    }
}
