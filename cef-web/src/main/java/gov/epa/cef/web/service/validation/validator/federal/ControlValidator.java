package gov.epa.cef.web.service.validation.validator.federal;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

@Component
public class ControlValidator extends BaseValidator<Control> {
	
	@Override
  public boolean validate(ValidatorContext validatorContext, Control control) {
		
		boolean result = true;
		
		CefValidatorContext context = getCefValidatorContext(validatorContext);
		
		//Add QA checks here
		
		return result;
	}
	
	private ValidationDetailDto createValidationDetails(Control source) {

    String description = MessageFormat.format("Control: {0}", source.getIdentifier());

    ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getIdentifier(), EntityType.CONTROL, description);
    return dto;
	}

}
