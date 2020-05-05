package gov.epa.cef.web.exception;

import gov.epa.cef.web.service.dto.bulkUpload.WorksheetError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BulkReportValidationException extends ApplicationException {

    private final List<WorksheetError> errors;

    public BulkReportValidationException(List<WorksheetError> errors) {

        super(ApplicationErrorCode.E_VALIDATION, "Bulk Report failed validation.");

        this.errors = new ArrayList<>();
        if (errors != null) {
            this.errors.addAll(errors);
        }
    }

    public Collection<WorksheetError> getErrors() {

        return Collections.unmodifiableCollection(this.errors);
    }
}
