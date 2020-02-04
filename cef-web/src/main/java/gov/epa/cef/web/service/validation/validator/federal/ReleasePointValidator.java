package gov.epa.cef.web.service.validation.validator.federal;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.repository.EisLatLongToleranceLookupRepository;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReleasePointValidator extends BaseValidator<ReleasePoint> {

    @Autowired
    private EisLatLongToleranceLookupRepository latLongToleranceRepo; 
    
    private static final String FUGITIVE_RELEASE_POINT_CODE = "1"; 
    private static final BigDecimal DEFAULT_TOLERANCE = new BigDecimal(0.003).setScale(6, RoundingMode.DOWN);
    
    @Override
    public boolean validate(ValidatorContext validatorContext, ReleasePoint releasePoint) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);
        
        // STACK RELEASE POINT CHECKS
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
	        
	        // Stack Height is required
	        if (releasePoint.getStackHeight() == null) {
	        	
	        	result = false;
	        	context.addFederalError(
	        			ValidationField.RP_STACK.value(),
	        			"releasePoint.stack.required",
	        			createValidationDetails(releasePoint),
	        			"Stack Height");
            
            // Stack Height range
	        } else if (releasePoint.getStackHeight() < 1 || releasePoint.getStackHeight() > 1300) {
	        	
	            result = false;
	            context.addFederalError(
	                    ValidationField.RP_STACK.value(),
	                    "releasePoint.stack.heightRange",
	                    createValidationDetails(releasePoint));
	        }
	        
	        // Stack Diameter is required      
	        if (releasePoint.getStackDiameter() == null) {
	        	
	        	result = false;
	        	context.addFederalError(
	        			ValidationField.RP_STACK.value(),
	        			"releasePoint.stack.required",
	        			createValidationDetails(releasePoint),
	        			"Stack Diameter");
            
            // Stack Diameter range         
	        } else if (releasePoint.getStackDiameter() < 0.001 || releasePoint.getStackDiameter() > 300) {
	        	
	            result = false;
	            context.addFederalError(
	            		ValidationField.RP_STACK.value(),
	            		"releasePoint.stack.diameterRange",
	            		createValidationDetails(releasePoint));
	        }

	        // Stack dimensions must be in FT
	        if (!validateUomFT(validatorContext, releasePoint, releasePoint.getStackHeight(), releasePoint.getStackHeightUomCode(), "Stack Height")) {
	        	result = false;
        	}
        
	        if (!validateUomFT(validatorContext, releasePoint, releasePoint.getStackDiameter(), releasePoint.getStackDiameterUomCode(), "Stack Diameter")) {
        		result = false;
        	}

	        // Calculation check, Stack Diameter must be less than Stack Height      
	        if (releasePoint.getStackDiameter() != null 
	        	&& releasePoint.getStackHeight() != null
	        	&& (releasePoint.getStackDiameter() >= releasePoint.getStackHeight())) {
	        	
	        	result = false;
	        	context.addFederalWarning(
	        			ValidationField.RP_STACK.value(),
	        			"releasePoint.stack.diameterCheck",
	        			createValidationDetails(releasePoint));
	        }
        }
        
        // FUGITIVE RELEASE POINT CHECKS
        if (releasePoint.getTypeCode().getCode().equals(FUGITIVE_RELEASE_POINT_CODE)) {
        	
        	// Fugitive Height Range
        	if (releasePoint.getFugitiveHeight() != null
        		&& (releasePoint.getFugitiveHeight() < 0 || releasePoint.getFugitiveHeight() > 500)) {
        	
	        	result = false;
	        	context.addFederalError(
	        			ValidationField.RP_FUGITIVE.value(),
	        			"releasePoint.fugitive.heightRange",
	        			createValidationDetails(releasePoint));
        	}
        	
        	// Fugitive Length Range
        	if (releasePoint.getFugitiveLength() != null
          		&& (releasePoint.getFugitiveLength() < 1 || releasePoint.getFugitiveLength() > 10000)) {
          	
  	        	result = false;
  	        	context.addFederalError(
  	        			ValidationField.RP_FUGITIVE.value(),
  	        			"releasePoint.fugitive.lengthRange",
  	        			createValidationDetails(releasePoint));
          }
        	
        	// Fugitive Width Range
        	if (releasePoint.getFugitiveWidth() != null
          		&& (releasePoint.getFugitiveWidth() < 1 || releasePoint.getFugitiveWidth() > 10000)) {
          	
  	        	result = false;
  	        	context.addFederalError(
  	        			ValidationField.RP_FUGITIVE.value(),
  	        			"releasePoint.fugitive.widthRange",
  	        			createValidationDetails(releasePoint));
          }
        	
        	// Fugitive Angle Range
        	if (releasePoint.getFugitiveAngle() != null
          		&& (releasePoint.getFugitiveAngle() < 0 || releasePoint.getFugitiveAngle() > 89)) {
          	
  	        	result = false;
  	        	context.addFederalError(
  	        			ValidationField.RP_FUGITIVE.value(),
  	        			"releasePoint.fugitive.angleRange",
  	        			createValidationDetails(releasePoint));
          }
        	
        	// Fugitive Dimensions must be in FT
        	if (!validateUomFT_long(validatorContext, releasePoint, releasePoint.getFugitiveLength(), releasePoint.getFugitiveLengthUomCode(), "Fugitive Length")) {
        		result = false;
        	}
      	
        	if (!validateUomFT_long(validatorContext, releasePoint, releasePoint.getFugitiveWidth(), releasePoint.getFugitiveWidthUomCode(), "Fugitive Width")) {
        		result = false;
        	}
      	
        	if (!validateUomFT_long(validatorContext, releasePoint, releasePoint.getFugitiveHeight(), releasePoint.getFugitiveHeightUomCode(), "Fugitive Height")) {
        		result = false;
        	}
        }
        
        // CHECKS FOR ALL RELEASE POINT TYPES
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

        // Fence Line Distance Range
        if (releasePoint.getFenceLineDistance() != null
        		&& (releasePoint.getFenceLineDistance() < 0 || releasePoint.getFenceLineDistance() > 99999)) {
        	
        	result = false;
        	context.addFederalError(
        			ValidationField.RP_FENCELINE.value(),
        			"releasePoint.fenceLine.range",
        			createValidationDetails(releasePoint));
      	}
        
        // Fence Line Distance must be in FT
        if (!validateUomFT_long(validatorContext, releasePoint, releasePoint.getFenceLineDistance(), releasePoint.getFenceLineUomCode(), "Fence Line Distance")) {
      		result = false;
      	}
        
        // Latitude/Longitude Tolerance Check
        BigDecimal facilitySiteLat = releasePoint.getFacilitySite().getLatitude();
        BigDecimal facilitySiteLong = releasePoint.getFacilitySite().getLongitude();
       
        if (!validateCoordinateTolerance(validatorContext, releasePoint, releasePoint.getLatitude(), facilitySiteLat, "latitude", "latitude")) {
        	result = false;
        }
        
        if (!validateCoordinateTolerance(validatorContext, releasePoint, releasePoint.getLongitude(), facilitySiteLong, "longitude", "longitude")) {
        	result = false;
        }
        
        return result;
    }
    
    
    public boolean validateUomFT(ValidatorContext validatorContext, ReleasePoint releasePoint, Double measure, UnitMeasureCode uom, String uomField) {

      CefValidatorContext context = getCefValidatorContext(validatorContext);
      boolean result = true;
      
      if ((uom != null && !"FT".contentEquals(uom.getCode())) || (uom == null && measure != null)) {

      	result = false;
      	context.addFederalError(
      			ValidationField.RP_UOM_FT.value(),
      			"releasePoint.uom.ft",
      			createValidationDetails(releasePoint),
      			uomField);
  		  }
      
      return result;
    }
    
    public boolean validateUomFT_long(ValidatorContext validatorContext, ReleasePoint releasePoint, Long measure, UnitMeasureCode uom, String uomField) {
    	if (measure != null) {
    		return validateUomFT(validatorContext, releasePoint, Double.valueOf(measure), uom, uomField);
    	} else {
    		return validateUomFT(validatorContext, releasePoint, null, uom, uomField);
    	}
    }
    
    public boolean validateCoordinateTolerance(ValidatorContext validatorContext, ReleasePoint releasePoint, Double rpCoordinate,  BigDecimal facilityCoordinate, String rpLatLongField, String facilityLatLongField) {
    	
      CefValidatorContext context = getCefValidatorContext(validatorContext);
      boolean result = true;
      
      String facilityEisId = releasePoint.getFacilitySite().getEisProgramId();
      
      if (latLongToleranceRepo.findById(facilityEisId).orElse(null) == null) {
      	BigDecimal maxRange = facilityCoordinate.add(DEFAULT_TOLERANCE).setScale(6, RoundingMode.DOWN);
      	BigDecimal minRange = facilityCoordinate.subtract(DEFAULT_TOLERANCE).setScale(6, RoundingMode.DOWN);
      	
      	if (rpCoordinate == null ||
      		(new BigDecimal(rpCoordinate).setScale(6, RoundingMode.DOWN).compareTo(maxRange) == 1) ||
      		(new BigDecimal(rpCoordinate).setScale(6, RoundingMode.DOWN).compareTo(minRange) == -1)){
      		
      		result = false;
      		context.addFederalError(
      				ValidationField.RP_COORDINATE.value(),
      				"releasePoint.coordinate.tolerance.facilityRange",
      				createValidationDetails(releasePoint),
      				rpLatLongField,
      				Double.valueOf(DEFAULT_TOLERANCE.toString()).toString(),
      				facilityLatLongField,
      				facilityCoordinate.setScale(6, RoundingMode.DOWN).toString());
      	}
      } else {
      	BigDecimal facilityTolerance = latLongToleranceRepo.findById(facilityEisId).orElse(null).getCoordinateTolerance().setScale(6, RoundingMode.DOWN);
      	BigDecimal maxRange = facilityCoordinate.add(facilityTolerance).setScale(6, RoundingMode.DOWN);
      	BigDecimal minRange = facilityCoordinate.subtract(facilityTolerance).setScale(6, RoundingMode.DOWN);
      	
      	if (rpCoordinate == null ||
      		(new BigDecimal(rpCoordinate).setScale(6, RoundingMode.DOWN).compareTo(maxRange) == 1) ||
      		(new BigDecimal(rpCoordinate).setScale(6, RoundingMode.DOWN).compareTo(minRange) == -1)){
      		
      		result = false;
      		context.addFederalError(
      				ValidationField.RP_COORDINATE.value(),
      				"releasePoint.coordinate.tolerance.facilityRange",
      				createValidationDetails(releasePoint),
      				rpLatLongField,
      				Double.valueOf(facilityTolerance.toString()).toString(),
      				facilityLatLongField,
      				facilityCoordinate.setScale(6, RoundingMode.DOWN).toString());
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
