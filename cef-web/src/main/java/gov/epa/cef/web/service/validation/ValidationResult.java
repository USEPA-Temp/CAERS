package gov.epa.cef.web.service.validation;

import com.baidu.unbiz.fluentvalidator.ResultCollector;
import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private final List<ValidationError> federalErrors;

    private final List<ValidationError> federalWarnings;

    private final List<ValidationError> stateErrors;

    private final List<ValidationError> stateWarnings;

    static final int FEDERAL_ERROR_CODE = 1;

    static final int FEDERAL_WARNING_CODE = -1;

    static final int STATE_ERROR_CODE = 2;

    static final int STATE_WARNING_CODE = -2;

    public ValidationResult() {

        this.federalErrors = new ArrayList<>();
        this.federalWarnings = new ArrayList<>();
        this.stateErrors = new ArrayList<>();
        this.stateWarnings = new ArrayList<>();
    }

    public void addError(ValidationError error) {

        this.federalErrors.add(error);
    }

    public void addStateError(ValidationError error) {

        this.stateErrors.add(error);
    }

    public void addStateWarning(ValidationError warning) {

        this.stateWarnings.add(warning);
    }

    public void addWarning(ValidationError warning) {

        this.federalWarnings.add(warning);
    }

    public Collection<ValidationError> getFederalErrors() {

        return Collections.unmodifiableCollection(this.federalErrors);
    }

    public void setFederalErrors(Collection<ValidationError> federalErrors) {

        this.federalErrors.clear();
        if (federalErrors != null) {
            this.federalErrors.addAll(federalErrors);
        }
    }

    public Collection<ValidationError> getFederalWarnings() {

        return Collections.unmodifiableCollection(this.federalWarnings);
    }

    public void setFederalWarnings(Collection<ValidationError> federalWarnings) {

        this.federalWarnings.clear();
        if (federalWarnings != null) {
            this.federalWarnings.addAll(federalWarnings);
        }
    }

    public Collection<ValidationError> getStateErrors() {

        return Collections.unmodifiableCollection(this.stateErrors);
    }

    public void setStateErrors(Collection<ValidationError> stateErrors) {

        this.stateErrors.clear();
        if (stateErrors != null) {
            this.stateErrors.addAll(stateErrors);
        }
    }

    public Collection<ValidationError> getStateWarnings() {

        return Collections.unmodifiableCollection(this.stateWarnings);
    }

    public void setStateWarnings(Collection<ValidationError> stateWarnings) {

        this.stateWarnings.clear();
        if (stateWarnings != null) {
            this.stateWarnings.addAll(stateWarnings);
        }
    }

    public boolean hasAnyWarnings() {

        return this.hasFederalWarnings() && this.hasStateWarnings();
    }

    public boolean hasFederalWarnings() {

        return this.federalWarnings.size() > 0;
    }

    public boolean hasStateWarnings() {

        return this.stateWarnings.size() > 0;
    }

    @JsonIgnore
    public boolean isFederalValid() {

        return this.federalErrors.isEmpty();
    }

    @JsonIgnore
    public boolean isStateValid() {

        return this.stateErrors.isEmpty();
    }

    public boolean isValid() {

        return isFederalValid() && isStateValid();
    }

    public ResultCollector<ValidationResult> resultCollector() {

        return validationResult -> {

            if (validationResult.isSuccess() == false) {

                validationResult.getErrors().forEach(error -> {

                    switch (error.getErrorCode()) {
                        case FEDERAL_ERROR_CODE:
                            this.federalErrors.add(error);
                            break;
                        case FEDERAL_WARNING_CODE:
                            this.federalWarnings.add(error);
                            break;
                        case STATE_ERROR_CODE:
                            this.stateErrors.add(error);
                            break;
                        case STATE_WARNING_CODE:
                            this.stateWarnings.add(error);
                            break;
                    }
                });
            }

            return this;
        };
    }
}
