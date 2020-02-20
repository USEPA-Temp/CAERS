package gov.epa.cef.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class AppValidationException extends ApplicationException {

    public AppValidationException(String message) {

        super(ApplicationErrorCode.E_VALIDATION, message);
    }

}
