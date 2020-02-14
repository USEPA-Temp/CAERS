package gov.epa.cef.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BulkReportValidationException extends ApplicationException {

    private final List<String> errors;

    public BulkReportValidationException(List<String> errors) {

        super(ApplicationErrorCode.E_INVALID_ARGUMENT, "Bulk Report failed validation.");

        this.errors = new ArrayList<>();
        if (errors != null) {
            this.errors.addAll(errors);
        }
    }

    public Collection<String> getErrors() {

        return Collections.unmodifiableCollection(this.errors);
    }
}
