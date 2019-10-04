package gov.epa.cef.web.service.validation.validator;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.service.validation.ValidationFeature;

public class FacilitySiteValidator extends BaseValidator<FacilitySite> {

    public FacilitySiteValidator() {

        super("validation/emissionsreport.properties");
    }

    @Override
    public boolean accept(ValidatorContext context, FacilitySite facilitySite) {

        return facilitySite != null
            && getCefValidatorContext(context).isEnabled(ValidationFeature.Federal);
    }

    @Override
    public boolean validate(ValidatorContext context, FacilitySite facilitySite) {

        return super.validate(context, facilitySite);
    }
}
