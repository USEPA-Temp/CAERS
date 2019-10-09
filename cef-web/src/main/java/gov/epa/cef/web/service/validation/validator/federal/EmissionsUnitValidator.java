package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;

@Component
public class EmissionsUnitValidator extends BaseValidator<EmissionsUnit> {

    @Override
    public void compose(FluentValidator validator,
                        ValidatorContext validatorContext,
                        EmissionsUnit emissionsUnit) {

        ValidationRegistry registry = getCefValidatorContext(validatorContext).getValidationRegistry();

        // add more validators as needed
        validator.onEach(emissionsUnit.getEmissionsProcesses(),
            registry.findOneByType(EmissionsProcessValidator.class));
    }
}
