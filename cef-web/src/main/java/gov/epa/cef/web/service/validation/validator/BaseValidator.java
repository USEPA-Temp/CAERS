package gov.epa.cef.web.service.validation.validator;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import gov.epa.cef.web.service.validation.CefValidatorContext;

public abstract class BaseValidator<T> extends ValidatorHandler<T> {

    protected CefValidatorContext getCefValidatorContext(ValidatorContext context) {

        return (CefValidatorContext) context;
    }
}
