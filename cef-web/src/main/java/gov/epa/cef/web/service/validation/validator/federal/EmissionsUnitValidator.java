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

import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;

@Component
public class EmissionsUnitValidator extends BaseValidator<EmissionsUnit> {

    private static final String STATUS_TEMPORARILY_SHUTDOWN = "TS";
    private static final String STATUS_PERMANENTLY_SHUTDOWN = "PS";
    private static final String STATUS_OPERATING = "OP";
    
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
        
        if (STATUS_TEMPORARILY_SHUTDOWN.contentEquals(emissionsUnit.getOperatingStatusCode().getCode())) {
        	List<EmissionsProcess> epList = emissionsUnit.getEmissionsProcesses().stream()
        			.filter(emissionsProcess -> !STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode())
        					&& !STATUS_TEMPORARILY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode()))
        			.collect(Collectors.toList());
        	
        	for (EmissionsProcess ep: epList) {
        		result = false;
        		context.addFederalError(
        				ValidationField.PROCESS_STATUS_CODE.value(),
        				"emissionsProcess.statusTypeCode.temporarilyShutdown",
        				createEmissionsProcessValidationDetails(ep));
        	}
        } else if (STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsUnit.getOperatingStatusCode().getCode())) {
        	List<EmissionsProcess> epList = emissionsUnit.getEmissionsProcesses().stream()
        			.filter(emissionsProcess -> !STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode()))
        			.collect(Collectors.toList());
        	
        	for (EmissionsProcess ep: epList) {
        		result = false;
        		context.addFederalError(
        				ValidationField.PROCESS_STATUS_CODE.value(),
        				"emissionsProcess.statusTypeCode.permanentShutdown",
        				createEmissionsProcessValidationDetails(ep));
        	}
        }
        
        // If unit operation status is not operating, status year is required
        if (!STATUS_OPERATING.contentEquals(emissionsUnit.getOperatingStatusCode().getCode()) && emissionsUnit.getStatusYear() == null) {
 	
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
        
        // Emissions Unit identifier must be unique within the facility.
        if (emissionsUnit != null && emissionsUnit.getFacilitySite() != null && emissionsUnit.getFacilitySite().getEmissionsUnits() != null) {
	        Map<Object, List<EmissionsUnit>> euMap = emissionsUnit.getFacilitySite().getEmissionsUnits().stream()
	                .filter(eu -> (eu.getUnitIdentifier() != null))
	                .collect(Collectors.groupingBy(feu -> feu.getUnitIdentifier()));
	        
	        
	        for (List<EmissionsUnit> euList : euMap.values()) {
	        	
	        	if (euList.size() > 1 && euList.get(0).getUnitIdentifier().contentEquals(emissionsUnit.getUnitIdentifier())) {
	          	
	        		result = false;
	          	context.addFederalError(
	          			ValidationField.EMISSIONS_UNIT_IDENTIFIER.value(),
	          			"emissionsUnit.unitIdentifier.duplicate",
	          			createValidationDetails(emissionsUnit));
	        	}
	        }
        }
        
        if (!STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsUnit.getOperatingStatusCode().getCode())) {
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
            
            // Cannot report legacy UoM
            if (emissionsUnit.getUnitOfMeasureCode() != null && 
                    (Boolean.TRUE.equals(emissionsUnit.getUnitOfMeasureCode().getLegacy()) 
                            || Boolean.FALSE.equals(emissionsUnit.getUnitOfMeasureCode().getUnitDesignCapacity()))) {
    
                result = false;
                context.addFederalError(
                        ValidationField.EMISSIONS_UNIT_UOM.value(),
                        "emissionsUnit.capacity.legacy",
                        createValidationDetails(emissionsUnit),
                        emissionsUnit.getUnitOfMeasureCode().getCode());
            }
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
    
    private String getEmissionsUnitIdentifier(EmissionsProcess process) {
      if (process.getEmissionsUnit() != null) {
          return process.getEmissionsUnit().getUnitIdentifier();
      }
      return null;
  }
    
    private ValidationDetailDto createEmissionsProcessValidationDetails(EmissionsProcess source) {

      String description = MessageFormat.format("Emission Unit: {0}, Emission Process: {1}", 
              getEmissionsUnitIdentifier(source),
              source.getEmissionsProcessIdentifier());

      ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getEmissionsProcessIdentifier(), EntityType.EMISSIONS_PROCESS, description);
      return dto;
  }
}
