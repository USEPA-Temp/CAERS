package gov.epa.cef.web.exception;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    final ApplicationErrorCode errorCode;

    @JsonCreator
    public ApplicationException(@JsonProperty("code") ApplicationErrorCode errorCode,
                                @JsonProperty("message") String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
    
    @JsonCreator
    public ApplicationException(@JsonProperty("code") ApplicationErrorCode errorCode,
                                @JsonProperty("message") String errorMessage, @JsonProperty("exception") Throwable t) {
        super(errorMessage, t);
        this.errorCode = errorCode;
    }

    public static ApplicationException asApplicationException(Exception e, ApplicationErrorCode code, String message) {
        if (e instanceof ApplicationException) {
            return ((ApplicationException) e);
        } else {
            ApplicationErrorCode aec = (code == null) ? ApplicationErrorCode.E_INTERNAL_ERROR : code;
            String msg = (StringUtils.isEmpty(message)) ? "An internal error occurred." : message;
            return new ApplicationException(aec, msg);
        }
    }

    public static ApplicationException asApplicationException(Exception e) {
        return asApplicationException(e, null, null);
    }

    public ApplicationErrorCode getErrorCode() {
        return errorCode;
    }
}
