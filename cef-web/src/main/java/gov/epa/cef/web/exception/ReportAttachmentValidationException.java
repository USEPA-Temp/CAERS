package gov.epa.cef.web.exception;

import gov.epa.cef.web.service.dto.bulkUpload.WorksheetError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ReportAttachmentValidationException extends ApplicationException {

    private final List<WorksheetError> errors;

    public ReportAttachmentValidationException(List<WorksheetError> errors) {

        super(ApplicationErrorCode.E_VALIDATION, "Document upload failed.");

        this.errors = new ArrayList<>();
        if (errors != null) {
            this.errors.addAll(errors);
        }
    }

    public Collection<WorksheetError> getErrors() {

        return Collections.unmodifiableCollection(this.errors);
    }
    
}
