package gov.epa.cef.web.service.validation.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.google.common.base.Preconditions;
import gov.epa.cef.web.service.validation.CefValidatorContext;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class BaseValidator<T> extends ValidatorHandler<T> {

    public static final String CefContextKey = "__CEF_VALIDATOR_CONTEXT__";

    private final ResourceBundle resourceBundle;

    public BaseValidator(String bundleName) {

        this.resourceBundle = ResourceBundle.getBundle(bundleName,
            Locale.getDefault(), Thread.currentThread().getContextClassLoader());

        Preconditions.checkNotNull(this.resourceBundle,
            "Unable to find resource bundle %s.", bundleName);
    }

    protected CefValidatorContext getCefValidatorContext(ValidatorContext context) {

        return context.getAttribute(CefContextKey, CefValidatorContext.class);
    }

    protected ValidationError error(String field, String code, Object... args) {

        // set error code to a positive number to indicate error
        return createValidationError(field, code, args).setErrorCode(422);
    }

    protected ValidationError warning(String field, String code, Object... args) {

        // set error code to a negative number to indicate warning
        return createValidationError(field, code, args).setErrorCode(-300);
    }

    private ValidationError createValidationError(String field, String code, Object... args) {

        if (this.resourceBundle.containsKey(code) == false) {
            String msg = String.format("Validation Message Key %s does not exist in %s.properties file.",
                code, this.resourceBundle.getBaseBundleName());
            throw new IllegalArgumentException(msg);
        }

        ValidationError result = new ValidationError().setField(field);

        String msg = this.resourceBundle.getString(code);
        if (args != null && args.length > 0) {

            result.setErrorMsg(MessageFormat.format(msg, args));

        } else {

            result.setErrorMsg(msg);
        }

        return result;
    }
}
