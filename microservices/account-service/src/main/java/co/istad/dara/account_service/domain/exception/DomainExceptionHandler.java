package co.istad.dara.account_service.domain.exception;

import co.istad.dara.common.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class DomainExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> domainExceptionHandler(DomainException exception){
        return new ResponseEntity<>(ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .description(exception.getMessage())
                .time(ZonedDateTime.now())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
