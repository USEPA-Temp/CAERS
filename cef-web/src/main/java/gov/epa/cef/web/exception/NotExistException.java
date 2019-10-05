package gov.epa.cef.web.exception;

public class NotExistException extends ApplicationException {

    public NotExistException(String entityType, long id) {

        super(ApplicationErrorCode.E_NOT_FOUND,
            String.format("Entity {%s} with ID %d was not found.", entityType, id));
    }
}
