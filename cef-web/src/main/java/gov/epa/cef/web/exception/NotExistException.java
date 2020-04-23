package gov.epa.cef.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.GONE)
public class NotExistException extends ApplicationException {

    public NotExistException(String entityType, long id) {

        super(ApplicationErrorCode.E_NOT_FOUND,
            String.format("Entity {%s} with ID %d was not found.", entityType, id));
    }
    
    public NotExistException(String entityType, String id) {

        super(ApplicationErrorCode.E_NOT_FOUND,
            String.format("Entity {%s} with ID %s was not found.", entityType, id));
    }
}
