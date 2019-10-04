package gov.epa.cef.web.service.validation;

import com.baidu.unbiz.fluentvalidator.ResultCollector;
import com.baidu.unbiz.fluentvalidator.ValidationError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private final List<ValidationError> errors;

    private final List<ValidationError> stateErrors;

    private final List<ValidationError> stateWarnings;

    private final List<ValidationError> warnings;

    ValidationResult() {

        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.stateErrors = new ArrayList<>();
        this.stateWarnings = new ArrayList<>();
    }

    public void addError(ValidationError error) {

        this.errors.add(error);
    }

    public void addStateError(ValidationError error) {

        this.stateErrors.add(error);
    }

    public void addStateWarning(ValidationError warning) {

        this.stateWarnings.add(warning);
    }

    public void addWarning(ValidationError warning) {

        this.warnings.add(warning);
    }

    public Collection<ValidationError> getErrors() {

        return Collections.unmodifiableCollection(this.errors);
    }

    public void setErrors(Collection<ValidationError> errors) {

        this.errors.clear();
        if (errors != null) {
            this.errors.addAll(errors);
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

    public Collection<ValidationError> getWarnings() {

        return Collections.unmodifiableCollection(this.warnings);
    }

    public void setWarnings(Collection<ValidationError> warnings) {

        this.warnings.clear();
        if (warnings != null) {
            this.warnings.addAll(warnings);
        }
    }

    ResultCollector<ValidationResult> federalCollector() {

        return validationResult -> {

            validationResult.getErrors().forEach(error -> {

                if (error.getErrorCode() < 0) {

                    this.warnings.add(error);

                } else {

                    this.errors.add(error);
                }
            });

            return this;
        };
    }

    ResultCollector<ValidationResult> stateCollector() {

        return validationResult -> {

            validationResult.getErrors().forEach(error -> {

                if (error.getErrorCode() < 0) {

                    this.stateWarnings.add(error);

                } else {

                    this.stateErrors.add(error);
                }
            });

            return this;
        };
    }
}
