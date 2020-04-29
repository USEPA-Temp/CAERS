package gov.epa.cef.web.exception;

public class VirusScanException extends ApplicationException {

    public VirusScanException(String errorMessage) {

        super(ApplicationErrorCode.E_VALIDATION, errorMessage);
    }
}
