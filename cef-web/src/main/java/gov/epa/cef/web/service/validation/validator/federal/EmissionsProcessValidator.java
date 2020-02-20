package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.AircraftEngineTypeCode;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.PointSourceSccCode;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.repository.AircraftEngineTypeCodeRepository;
import gov.epa.cef.web.repository.PointSourceSccCodeRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Strings;

@Component
public class EmissionsProcessValidator extends BaseValidator<EmissionsProcess> {
	
	@Autowired
	private PointSourceSccCodeRepository sccRepo;
	
	@Autowired
	private AircraftEngineTypeCodeRepository aircraftEngCodeRepo;
    
    @Override
    public void compose(FluentValidator validator,
                        ValidatorContext validatorContext,
                        EmissionsProcess emissionsProcess) {

        ValidationRegistry registry = getCefValidatorContext(validatorContext).getValidationRegistry();

        // add more validators as needed
        validator.onEach(emissionsProcess.getReportingPeriods(),
            registry.findOneByType(ReportingPeriodValidator.class));
    }

    @Override
    public boolean validate(ValidatorContext validatorContext, EmissionsProcess emissionsProcess) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        Double totalReleasePointPercent = emissionsProcess.getReleasePointAppts().stream().mapToDouble(ReleasePointAppt::getPercent).sum();
        // Might need to add a rounding tolerance.
        if (100 != totalReleasePointPercent) {

            result = false;
            context.addFederalError(
                    ValidationField.PROCESS_RP_PCT.value(),
                    "emissionsProcess.releasePointAppts.percent.total",
                    createValidationDetails(emissionsProcess));
        }
        
        // Check for valid SCC Code
        if (Strings.emptyToNull(emissionsProcess.getSccCode()) != null) {

        	PointSourceSccCode isPointSourceSccCode = sccRepo.findById(emissionsProcess.getSccCode()).orElse(null);
        	short reportYear = emissionsProcess.getEmissionsUnit().getFacilitySite().getEmissionsReport().getYear();
        	
        	if (isPointSourceSccCode == null) {
        		
        		result = false;
        		context.addFederalError(
                    ValidationField.PROCESS_INFO_SCC.value(),
                    "emissionsProcess.information.scc.invalid",
                    createValidationDetails(emissionsProcess),
                    emissionsProcess.getSccCode());
            
        	} else if (isPointSourceSccCode.getLastInventoryYear() != null
        		&& isPointSourceSccCode.getLastInventoryYear() < reportYear) {
        		
        		result = false;
        		context.addFederalError(
        			ValidationField.PROCESS_INFO_SCC.value(),
        			"emissionsProcess.information.scc.expired",
        			createValidationDetails(emissionsProcess),
        			emissionsProcess.getSccCode(),
        			isPointSourceSccCode.getLastInventoryYear().toString());
        		
        	} else if (isPointSourceSccCode.getLastInventoryYear() != null
        		&& isPointSourceSccCode.getLastInventoryYear() >= reportYear) {
        		
        		result = false;
        		context.addFederalWarning(
        			ValidationField.PROCESS_INFO_SCC.value(),
        			"emissionsProcess.information.scc.retired",
        			createValidationDetails(emissionsProcess),
        			emissionsProcess.getSccCode(),
        			isPointSourceSccCode.getLastInventoryYear().toString());
          }
        	
          List<String> aircraftEngineScc = new ArrayList<String>();
          Collections.addAll(aircraftEngineScc, "2275001000", "2275020000", "2275050011", "2275050012", "2275060011", "2275060012");
        	
          // if SCC is an aircraft engine type, then aircraft engine code is required 
          for (String code: aircraftEngineScc) {
          	if (code.contentEquals(emissionsProcess.getSccCode()) && emissionsProcess.getAircraftEngineTypeCode() == null) {
          		
          		result = false;
          		context.addFederalError(
          				ValidationField.PROCESS_AIRCRAFT_CODE.value(),
          				"emissionsProcess.aircraftCode.required",
          				createValidationDetails(emissionsProcess));
          		
          	} 
          }
        }
        
        if (emissionsProcess != null) {
        	
            // Check for unique SCC and AircraftEngineType code combination within a facility site
            if ((emissionsProcess.getSccCode() != null) && (emissionsProcess.getAircraftEngineTypeCode() != null)) {
            	String testingCombination = emissionsProcess.getAircraftEngineTypeCode().getCode() + emissionsProcess.getSccCode();
                List<String> combinationList = new ArrayList<String>();
                for(EmissionsUnit eu: emissionsProcess.getEmissionsUnit().getFacilitySite().getEmissionsUnits()){
                	if(eu.getEmissionsProcesses() != null){
                		for(EmissionsProcess ep: eu.getEmissionsProcesses()){
                			if ((ep.getSccCode() != null) && (ep.getAircraftEngineTypeCode() != null) && (!ep.getId().equals(emissionsProcess.getId()))){
                    			String combination = ep.getAircraftEngineTypeCode().getCode().toString() + ep.getSccCode().toString();
                    			combinationList.add(combination);
                			}
                		}
                	}
                }
                for(String combination: combinationList){
            		if(combination.equals(testingCombination)){
                    	context.addFederalError(
                    			ValidationField.PROCESS_AIRCRAFT_CODE_AND_SCC_CODE.value(),
                    			"emissionsProcess.aircraftCodeAndSccCombination.duplicate",
                    			createValidationDetails(emissionsProcess));
                    	result = false;
            		}
                }
            }        	
      
          // aircraft engine code must match assigned SCC
          if (emissionsProcess.getSccCode() != null && emissionsProcess.getAircraftEngineTypeCode() != null) {
          	AircraftEngineTypeCode sccHasEngineType = aircraftEngCodeRepo.findById(emissionsProcess.getAircraftEngineTypeCode().getCode()).orElse(null);
          	
          	if (sccHasEngineType != null && !emissionsProcess.getSccCode().contentEquals(sccHasEngineType.getScc())) {
          		
          		result = false;
          		context.addFederalError(
          				ValidationField.PROCESS_AIRCRAFT_CODE.value(),
          				"emissionsProcess.aircraftCode.valid",
          				createValidationDetails(emissionsProcess));
        		}
        	}
          
	        Map<Object, List<ReleasePointAppt>> rpaMap = emissionsProcess.getReleasePointAppts().stream()
	            .filter(rpa -> rpa.getReleasePoint() != null)
	            .collect(Collectors.groupingBy(e -> e.getReleasePoint().getId()));
	     
	        // Process must go to at least one release point
	        if (CollectionUtils.sizeIsEmpty(rpaMap)) {
	
	        	result = false;
	        	context.addFederalError(
	        			ValidationField.PROCESS_RP.value(),
	        			"emissionsProcess.releasePointAppts.required",
	        			createValidationDetails(emissionsProcess));
	        }
	        
	        // release point can be used only once per rp appt collection
	        for (List<ReleasePointAppt> rpa: rpaMap.values()) {
	        	
	        	if (rpa.size() > 1) {
	        		
	        		result = false;
		        	context.addFederalError(
		        			ValidationField.PROCESS_RP.value(),
		        			"emissionsProcess.releasePointAppts.duplicate",
		        			createValidationDetails(emissionsProcess),
		        			rpa.get(0).getReleasePoint().getReleasePointIdentifier());
		        }
		      }
        }
        
        return result;
    }

    private String getEmissionsUnitIdentifier(EmissionsProcess process) {
        if (process.getEmissionsUnit() != null) {
            return process.getEmissionsUnit().getUnitIdentifier();
        }
        return null;
    }

    private ValidationDetailDto createValidationDetails(EmissionsProcess source) {

        String description = MessageFormat.format("Emission Unit: {0}, Emission Process: {1}", 
                getEmissionsUnitIdentifier(source),
                source.getEmissionsProcessIdentifier());

        ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getEmissionsProcessIdentifier(), EntityType.EMISSIONS_PROCESS, description);
        return dto;
    }

}
