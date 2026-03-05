package co.istad.dara.account_service.rest.exception;



import co.istad.dara.account_service.rest.message.BasedError;
import co.istad.dara.account_service.rest.message.BasedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        return new ResponseEntity<>(basedErrorResponse, exception.getStatusCode());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        BasedError<String> basedError = new BasedError<>();
        basedError.setCode(HttpStatus.BAD_REQUEST.toString());
        basedError.setDescription(exception.getMostSpecificCause().getMessage());

        BasedErrorResponse basedErrorResponse = new BasedErrorResponse();
        basedErrorResponse.setError(basedError);
        return new ResponseEntity<>(basedErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
