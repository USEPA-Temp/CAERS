package gov.epa.cef.web.service.validation.validator.federal;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

@Component
public class ReleasePointValidator extends BaseValidator<ReleasePoint> {

    @Override
    public boolean validate(ValidatorContext validatorContext, ReleasePoint releasePoint) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);
        
        // Release point validations for non fugitive type release point.
        if (!releasePoint.getTypeCode().getCode().equals("1")) {

	        if (releasePoint.getExitGasTemperature() == null) {
	        	
	            // Exit Gas Temperature is required.
	            result = false;
	            context.addFederalError(
	                    ValidationField.RP_GAS_TEMP.value(),
	                    "releasePoint.exitGasTemperature.required",
	                    createValidationDetails(releasePoint));
	            
	        } else if (releasePoint.getExitGasTemperature() < -30 || releasePoint.getExitGasTemperature() > 4000) {
	        	
	            // Exit Gas Temperature must be between -30 and 4000 degrees Fahrenheit.
	            result = false;
	            context.addFederalError(
	                    ValidationField.RP_GAS_TEMP.value(),
	                    "releasePoint.exitGasTemperature.range",
	                    createValidationDetails(releasePoint));
	        } 
	        
	        if ((releasePoint.getExitGasFlowRate() == null || releasePoint.getExitGasFlowUomCode() == null)) {
	        	
	            // Exit Gas Flow Rate and Exit Gas Flow Rate UoM.
	            result = false;
	            context.addFederalError(
	                    ValidationField.RP_GAS_FLOW.value(),
	                    "releasePoint.exitGasFlowRate.required",
	                    createValidationDetails(releasePoint));
	            
	        } else if (releasePoint.getExitGasFlowRate() != null && (releasePoint.getExitGasFlowRate() < 0.1 || releasePoint.getExitGasFlowRate() > 200000)) {
	        	
	            // Exit Gas Flow Rate must be between 0.1 and 200000 ACFS.
	            result = false;
	            context.addFederalError(
	                    ValidationField.RP_GAS_FLOW.value(),
	                    "releasePoint.exitGasFlowRate.range",
	                    createValidationDetails(releasePoint));
	        }
        }

        return result;
    }

    private ValidationDetailDto createValidationDetails(ReleasePoint source) {

        String description = MessageFormat.format("Release Point: {0}", source.getReleasePointIdentifier());

        ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getReleasePointIdentifier(), EntityType.RELEASE_POINT, description);
        return dto;
    }
}
