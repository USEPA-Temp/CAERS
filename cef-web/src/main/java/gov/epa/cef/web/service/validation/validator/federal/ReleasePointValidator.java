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

    private static final String FUGITIVE_RELEASE_POINT_CODE = "1"; 
    
    @Override
    public boolean validate(ValidatorContext validatorContext, ReleasePoint releasePoint) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);
        
        if (!releasePoint.getTypeCode().getCode().equals(FUGITIVE_RELEASE_POINT_CODE)) {
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
	        
	        // Exit Gas Flow Rate OR Exit Gas Velocity is required.
	        if ((releasePoint.getExitGasFlowRate() == null && releasePoint.getExitGasVelocity() == null)) {
	        	result = false;
	        	
	        	context.addFederalError(
                		ValidationField.RP_GAS_RELEASE.value(),
                		"releasePoint.release.required",
                		createValidationDetails(releasePoint));
	        }
        }
              
        if ((releasePoint.getExitGasFlowRate() != null && releasePoint.getExitGasFlowUomCode() == null) ||
        	(releasePoint.getExitGasFlowRate() == null && releasePoint.getExitGasFlowUomCode() != null)) {
       
        	// Exit Gas Flow Rate and Exit Gas Flow Rate UoM must be reported together.
        	result = false;
        	context.addFederalError(
                  ValidationField.RP_GAS_FLOW.value(),
                  "releasePoint.exitGasFlowRate.required",
                  createValidationDetails(releasePoint));
          
        } else if (releasePoint.getExitGasFlowUomCode() != null && (
        	!"ACFS".contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
        	!"ACFM".contentEquals(releasePoint.getExitGasFlowUomCode().getCode()))) {
        		
        	// Exit Gas Flow Rate UoM must be in ACFS or ACFM.
        	result = false;
        	context.addFederalError(
                  ValidationField.RP_GAS_FLOW.value(),
                  "releasePoint.exitGasFlowRate.required",
                  createValidationDetails(releasePoint));
        }
        
        // Exit Gas Flow Rate Ranges
        if (releasePoint.getExitGasFlowUomCode() != null && releasePoint.getExitGasFlowRate() != null) {
        	if (FUGITIVE_RELEASE_POINT_CODE.contentEquals(releasePoint.getTypeCode().getCode())) {
        		if ("ACFS".contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
        				(releasePoint.getExitGasFlowRate() < 0 || releasePoint.getExitGasFlowRate() > 200000)) {
        			
        				result = false;
        				context.addFederalError(
			                  ValidationField.RP_GAS_FLOW.value(),
			                  "releasePoint.exitGasFlowRate.fugitiveACFS.range",
			                  createValidationDetails(releasePoint));
			        	
	        	} else if ("ACFM".contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
	        			(releasePoint.getExitGasFlowRate() < 0 || releasePoint.getExitGasFlowRate() > 12000000)) {
		        		result = false;
			        	context.addFederalError(
			                  ValidationField.RP_GAS_FLOW.value(),
			                  "releasePoint.exitGasFlowRate.fugitiveACFM.range",
			                  createValidationDetails(releasePoint));
		        	
	        	}
        	} else {
        		if ("ACFS".contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
        				(releasePoint.getExitGasFlowRate() < 0.00000001 || releasePoint.getExitGasFlowRate() > 200000)) {
        			
        				result = false;
        				context.addFederalError(
			                  ValidationField.RP_GAS_FLOW.value(),
			                  "releasePoint.exitGasFlowRate.stackACFS.range",
			                  createValidationDetails(releasePoint));
			        	
        		} else if ("ACFM".contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
	        			(releasePoint.getExitGasFlowRate() < 0.00000001 || releasePoint.getExitGasFlowRate() > 12000000)) {
		        		
        				result = false;
			        	context.addFederalError(
			                  ValidationField.RP_GAS_FLOW.value(),
			                  "releasePoint.exitGasFlowRate.stackACFM.range",
			                  createValidationDetails(releasePoint));
	        	}
        	}
        }
        
     // Exit Gas Velocity Ranges
        if (releasePoint.getExitGasVelocityUomCode() != null && releasePoint.getExitGasVelocity() != null) {
        	if (FUGITIVE_RELEASE_POINT_CODE.contentEquals(releasePoint.getTypeCode().getCode())) {
        		if ("FPS".contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
        				(releasePoint.getExitGasVelocity() < 0 || releasePoint.getExitGasVelocity() > 400)) {
        			
        				result = false;
        				context.addFederalError(
			                  ValidationField.RP_GAS_VELOCITY.value(),
			                  "releasePoint.exitGasVelocity.fugitiveFPS.range",
			                  createValidationDetails(releasePoint));
			        	
	        	} else if ("FPM".contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
	        			(releasePoint.getExitGasVelocity() < 0 || releasePoint.getExitGasVelocity() > 24000)) {
	        		
		        		result = false;
			        	context.addFederalError(
			                  ValidationField.RP_GAS_VELOCITY.value(),
			                  "releasePoint.exitGasVelocity.fugitiveFPM.range",
			                  createValidationDetails(releasePoint));
		        	
	        	}
        	} else {
        		if ("FPS".contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
        				(releasePoint.getExitGasVelocity() < 0.001 || releasePoint.getExitGasVelocity() > 1500)) {
        			
        				result = false;
        				context.addFederalError(
			                  ValidationField.RP_GAS_VELOCITY.value(),
			                  "releasePoint.exitGasVelocity.stackFPS.range",
			                  createValidationDetails(releasePoint));
			        	
        		} else if ("FPM".contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
	        			(releasePoint.getExitGasVelocity() < 0.060 || releasePoint.getExitGasVelocity() > 90000)) {
		        		
        				result = false;
			        	context.addFederalError(
			                  ValidationField.RP_GAS_VELOCITY.value(),
			                  "releasePoint.exitGasVelocity.stackFPM.range",
			                  createValidationDetails(releasePoint));
	        	}
        	}
        }
        
        if ((releasePoint.getExitGasVelocity() != null && releasePoint.getExitGasVelocityUomCode() == null) ||
          	(releasePoint.getExitGasVelocity() == null && releasePoint.getExitGasVelocityUomCode() != null)) {
         
          	// Exit Gas Velocity and Exit Gas Velocity UoM must be reported together.
          	result = false;
          	context.addFederalError(
                    ValidationField.RP_GAS_VELOCITY.value(),
                    "releasePoint.exitGasVelocity.required",
                    createValidationDetails(releasePoint));
            
          } else if (releasePoint.getExitGasVelocityUomCode() != null && (
          	!"FPS".contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
          	!"FPM".contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()))) {
          		
          	// Exit Gas Velocity UoM must be in FPM or FPS.
          	result = false;
          	context.addFederalError(
                    ValidationField.RP_GAS_VELOCITY.value(),
                    "releasePoint.exitGasVelocity.required",
                    createValidationDetails(releasePoint));
          }
        
        return result;
    }

    private ValidationDetailDto createValidationDetails(ReleasePoint source) {

        String description = MessageFormat.format("Release Point: {0}", source.getReleasePointIdentifier());

        ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getReleasePointIdentifier(), EntityType.RELEASE_POINT, description);
        return dto;
    }
}
