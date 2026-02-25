package co.istad.dara.customer_service.infrastucture.exception;


import co.istad.dara.customer_service.infrastucture.message.BasedError;
import co.istad.dara.customer_service.infrastucture.message.BasedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


@RestControllerAdvice
public class HandleServiceException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(ResponseStatusException exception){
        BasedError<String> basedError = new BasedError<>();
        basedError.setCode(exception.getStatusCode().toString());
        basedError.setDescription(exception.getReason());

        BasedErrorResponse basedErrorResponse = new BasedErrorResponse();
        basedErrorResponse.setError(basedError);
        return new ResponseEntity<>(basedErrorResponse,exception.getStatusCode());
    }

}
