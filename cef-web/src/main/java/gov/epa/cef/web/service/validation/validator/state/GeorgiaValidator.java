package gov.epa.cef.web.service.validation.validator.state;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.validation.ValidationFeature;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import gov.epa.cef.web.service.validation.validator.IEmissionsReportValidator;
import org.springframework.stereotype.Component;

@Component
public class GeorgiaValidator
    extends BaseValidator<EmissionsReport>
    implements IEmissionsReportValidator {

    @Override
    public boolean accept(ValidatorContext context, EmissionsReport emissionsReport) {

        return getCefValidatorContext(context).isEnabled(ValidationFeature.Georgia);
    }
}
