package gov.epa.cef.web.service.validation;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Preconditions;

import gov.epa.cef.web.service.dto.ValidationDetailDto;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class CefValidatorContext extends ValidatorContext {

    private final Set<ValidationFeature> features;

    private final ResourceBundle resourceBundle;

    private final ValidationRegistry validationRegistry;

    public CefValidatorContext(ValidationRegistry validationRegistry, String bundleName) {

        super();

        this.validationRegistry = validationRegistry;

        this.resourceBundle = ResourceBundle.getBundle(bundleName,
            Locale.getDefault(), Thread.currentThread().getContextClassLoader());

        Preconditions.checkNotNull(this.resourceBundle,
            "Unable to find resource bundle %s.", bundleName);

        this.features = new HashSet<>();

        this.result = new com.baidu.unbiz.fluentvalidator.ValidationResult();
    }

    public void addFederalError(String field, String code, Object... args) {

        addFederalError(field, code, null, args);
    }

    public void addFederalError(String field, String code, ValidationDetailDto value, Object... args) {

        // use bundle to create error message and set error code to error
        addError(createValidationError(field, code, value, args)
            .setErrorCode(ValidationResult.FEDERAL_ERROR_CODE));
    }

    public void addFederalWarning(String field, String code, Object... args) {

        addFederalWarning(field, code, null, args);
    }

    public void addFederalWarning(String field, String code, ValidationDetailDto value, Object... args) {

        // use bundle to create warning message and set error code to warning
        addError(createValidationError(field, code, value, args)
            .setErrorCode(ValidationResult.FEDERAL_WARNING_CODE));
    }

    public void addStateError(String field, String code, Object... args) {

        addStateError(field, code, null, args);
    }

    public void addStateError(String field, String code, ValidationDetailDto value, Object... args) {

        // use bundle to create error message and set error code to error
        addError(createValidationError(field, code, value, args)
            .setErrorCode(ValidationResult.STATE_ERROR_CODE));
    }

    public void addStateWarning(String field, String code, Object... args) {

        addStateWarning(field, code, null, args);
    }

    public void addStateWarning(String field, String code, ValidationDetailDto value, Object... args) {

        // use bundle to create warning message and set error code to warning
        addError(createValidationError(field, code, value, args)
            .setErrorCode(ValidationResult.STATE_WARNING_CODE));
    }

    public CefValidatorContext disable(@NotNull ValidationFeature feature) {

        this.features.remove(feature);
        return this;
    }

    public CefValidatorContext disable(ValidationFeature[] features) {

        if (features != null) {
            this.features.removeAll(Arrays.asList(features));
        }

        return this;
    }

    public CefValidatorContext enable(ValidationFeature[] features) {

        if (features != null) {
            this.features.addAll(Arrays.asList(features));
        }

        return this;
    }

    public CefValidatorContext enable(@NotNull ValidationFeature feature) {

        this.features.add(feature);
        return this;
    }

    public ValidationRegistry getValidationRegistry() {

        return validationRegistry;
    }

    public boolean isAllEnabled(ValidationFeature feature, ValidationFeature... others) {

        List<ValidationFeature> features = new ArrayList<>();
        features.add(feature);
        if (others != null) {
            features.addAll(Arrays.asList(others));
        }

        return this.features.containsAll(features);
    }

    public boolean isEnabled(@NotNull ValidationFeature feature) {

        return this.features.contains(feature);
    }

    public boolean isNotEnabled(@NotNull ValidationFeature feature) {

        return isEnabled(feature) == false;
    }

    public CefValidatorContext resetFeatures() {

        this.features.clear();
        return this;
    }

    private ValidationError createValidationError(String field, String code, ValidationDetailDto value, Object... args) {

        if (this.resourceBundle.containsKey(code) == false) {
            String msg = String.format("Validation Message Key %s does not exist in %s.properties file.",
                code, this.resourceBundle.getBaseBundleName());
            throw new IllegalArgumentException(msg);
        }

        ValidationError result = new ValidationError().setField(field).setInvalidValue(value);

        String msg = this.resourceBundle.getString(code);
        if (args != null && args.length > 0) {

            result.setErrorMsg(MessageFormat.format(msg, args));

        } else {

            result.setErrorMsg(msg);
        }

        return result;
    }
}
