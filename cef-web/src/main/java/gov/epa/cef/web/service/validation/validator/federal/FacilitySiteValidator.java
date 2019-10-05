package gov.epa.cef.web.service.validation.validator.federal;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import org.springframework.stereotype.Component;

@Component
public class FacilitySiteValidator extends BaseValidator<FacilitySite> {

    @Override
    public boolean validate(ValidatorContext context, FacilitySite facilitySite) {

        return super.validate(context, facilitySite);
    }
}
