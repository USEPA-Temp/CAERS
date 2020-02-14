package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

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
    
    @Override
    public boolean validate(ValidatorContext validatorContext, EmissionsUnit emissionsUnit) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);
        
        // If unit operation status is not operating, status year is required
        if (!"OP".contentEquals(emissionsUnit.getOperatingStatusCode().getCode()) && emissionsUnit.getStatusYear() == null) {
 	
        	result = false;
        	context.addFederalError(
        			ValidationField.EMISSIONS_UNIT_STATUS_CODE.value(), "emissionsUnit.statusTypeCode.required",
        			createValidationDetails(emissionsUnit));
        }
        
        // Status year must be between 1900 and 2050
        if (emissionsUnit.getStatusYear() != null && (emissionsUnit.getStatusYear() < 1900 || emissionsUnit.getStatusYear() > 2050)) {
        	
        	result = false;
        	context.addFederalError(
        			ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value(), "emissionsUnit.statusYear.range",
        			createValidationDetails(emissionsUnit));
        }
        
        // Design capacity warning
        if (emissionsUnit.getUnitTypeCode() != null && emissionsUnit.getDesignCapacity() == null) {
        	List<String> typeCodeWarn = new ArrayList<String>(); 
        	Collections.addAll(typeCodeWarn, "100", "120", "140", "160", "180");
        	
        	for (String code: typeCodeWarn) {
          	if (code.contentEquals(emissionsUnit.getUnitTypeCode().getCode())) {
          		
          		result = false;
          		context.addFederalWarning(
          				ValidationField.EMISSIONS_UNIT_CAPACITY.value(), "emissionsUnit.capacity.check",
          				createValidationDetails(emissionsUnit),
          				emissionsUnit.getUnitTypeCode().getDescription());
          	}
          };
        }
        
        if (emissionsUnit.getUnitIdentifier() != null) {
        	
            // Emissions Unit identifier must be unique within the facility.
            Map<Object, List<EmissionsUnit>> euMap = emissionsUnit.getFacilitySite().getEmissionsUnits().stream()
                    .filter(eu -> ((eu.getUnitIdentifier().equals(emissionsUnit.getUnitIdentifier())) && (eu.getId() !=
                    emissionsUnit.getId())))
                    .collect(Collectors.groupingBy(eu -> eu.getUnitIdentifier()));
            
            if (!CollectionUtils.sizeIsEmpty(euMap)) {
            	result = false;
            	context.addFederalError(
            			ValidationField.EMISSIONS_UNIT_IDENTIFIER.value(),
            			"emissionsUnit.unitIdentifier.duplicate",
            			createValidationDetails(emissionsUnit));
            }
        }
        
        // Design capacity range
        if ((emissionsUnit.getDesignCapacity() != null)
        	&& (emissionsUnit.getDesignCapacity().doubleValue() < 0.01 || emissionsUnit.getDesignCapacity().doubleValue() > 100000000)) {
        	
        	result = false;
        	context.addFederalError(
        			ValidationField.EMISSIONS_UNIT_CAPACITY.value(),
        			"emissionsUnit.capacity.range",
        			createValidationDetails(emissionsUnit));
        } 
        
        // Design capacity and UoM must be reported together.
        if ((emissionsUnit.getDesignCapacity() != null && emissionsUnit.getUnitOfMeasureCode() == null)
        	|| (emissionsUnit.getDesignCapacity() == null && emissionsUnit.getUnitOfMeasureCode() != null)) {
        	
        	result = false;
        	context.addFederalError(
        			ValidationField.EMISSIONS_UNIT_CAPACITY.value(),
        			"emissionsUnit.capacity.required",
        			createValidationDetails(emissionsUnit));
        }
        
        // Process identifier must be unique within unit
        Map<Object, List<EmissionsProcess>> epMap = emissionsUnit.getEmissionsProcesses().stream()
            .filter(ep -> ep.getEmissionsProcessIdentifier() != null)
            .collect(Collectors.groupingBy(eu -> eu.getEmissionsProcessIdentifier()));
        
        for (List<EmissionsProcess> epList : epMap.values()) {
        	
        	if (epList.size() > 1) {
        		
        		result = false;
        		context.addFederalError(
	        			ValidationField.EMISSIONS_UNIT_PROCESS.value(),
	        			"emissionsUnit.emissionsProcess.duplicate",
	        			createValidationDetails(emissionsUnit),
	        			epList.get(0).getEmissionsProcessIdentifier());
        	}
      	}
        
        return result;
    }
    
    private ValidationDetailDto createValidationDetails(EmissionsUnit source) {

      String description = MessageFormat.format("Emissions Unit: {0}", source.getUnitIdentifier());

      ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getUnitIdentifier(), EntityType.EMISSIONS_UNIT, description);
      return dto;
  }
}
