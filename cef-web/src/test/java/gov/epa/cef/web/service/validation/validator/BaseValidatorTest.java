package gov.epa.cef.web.service.validation.validator;

import gov.epa.cef.web.config.TestCategories;
import gov.epa.cef.web.service.validation.CefValidatorContext;

import org.junit.experimental.categories.Category;

@Category(TestCategories.FastTest.class)
abstract class BaseValidatorTest {

    protected CefValidatorContext createContext() {

        return new CefValidatorContext(null, "validation/emissionsreport");
    }
}
