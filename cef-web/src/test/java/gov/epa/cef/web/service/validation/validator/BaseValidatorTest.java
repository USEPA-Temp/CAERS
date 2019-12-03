package gov.epa.cef.web.service.validation.validator;

import gov.epa.cef.web.config.TestCategories;
import gov.epa.cef.web.service.validation.CefValidatorContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.experimental.categories.Category;

import com.baidu.unbiz.fluentvalidator.ValidationError;

@Category(TestCategories.FastTest.class)
abstract class BaseValidatorTest {

    protected CefValidatorContext createContext() {

        return new CefValidatorContext(null, "validation/emissionsreport");
    }

    protected Map<String, List<ValidationError>> mapErrors(List<ValidationError> errors) {
        return errors.stream().collect(Collectors.groupingBy(ValidationError::getField));
    }
}
