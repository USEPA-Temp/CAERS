package gov.epa.cef.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class FacilityAccessException extends ApplicationException {

    public FacilityAccessException(Collection<String> programIds) {

        super(ApplicationErrorCode.E_AUTHORIZATION,
            String.format("Access to Facility Program ID(s) [%s] is denied.", String.join(", ", programIds)));
    }
}
