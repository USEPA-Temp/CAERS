package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.PointSourceSccCode;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.repository.PointSourceSccCodeRepository;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import gov.epa.cef.web.util.ConstantUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;

@Component
public class EmissionsUnitValidator extends BaseValidator<EmissionsUnit> {
	
	@Autowired
	private PointSourceSccCodeRepository sccRepo;
    
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
        
        // If the facility source type code is landfill, then the emissions process can still be "operating" because of passive emissions that are emitted from the landfill.
        // For all other facility source types, if the facility is shutdown, then the emissions process underneath the emissions unit must also be shutdown.
        if ((emissionsUnit.getFacilitySite().getFacilitySourceTypeCode() != null && !ConstantUtils.FACILITY_SOURCE_LANDFILL_CODE.contentEquals(emissionsUnit.getFacilitySite().getFacilitySourceTypeCode().getCode()))
        		|| emissionsUnit.getFacilitySite().getFacilitySourceTypeCode() == null) {
	        	
	        //if the unit is temporarily shutdown, then the underlying processes must also be temporarily or permanently shutdown
	        if (ConstantUtils.STATUS_TEMPORARILY_SHUTDOWN.contentEquals(emissionsUnit.getOperatingStatusCode().getCode())) {
	        	List<EmissionsProcess> epList = emissionsUnit.getEmissionsProcesses().stream()
	        			.filter(emissionsProcess -> !ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode())
	        					&& !ConstantUtils.STATUS_TEMPORARILY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode()))
	        			.collect(Collectors.toList());
	        	
	        	for (EmissionsProcess ep: epList) {
	        		result = false;
	        		context.addFederalError(
	        				ValidationField.PROCESS_STATUS_CODE.value(),
	        				"emissionsProcess.statusTypeCode.temporarilyShutdown",
	        				createEmissionsProcessValidationDetails(ep));
	        	}
	        	
	        //if the unit is permanently shutdown, then the underlying processes must also be permanently shutdown
	        } else if (ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsUnit.getOperatingStatusCode().getCode())) {
	        	List<EmissionsProcess> epList = emissionsUnit.getEmissionsProcesses().stream()
	        			.filter(emissionsProcess -> !ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode()))
	        			.collect(Collectors.toList());
	        	
	        	for (EmissionsProcess ep: epList) {
	        		result = false;
	        		context.addFederalError(
	        				ValidationField.PROCESS_STATUS_CODE.value(),
	        				"emissionsProcess.statusTypeCode.permanentShutdown",
	        				createEmissionsProcessValidationDetails(ep));
	        	}
	        }
        }
        
        // If unit operation status is not operating, status year is required
        if (!ConstantUtils.STATUS_OPERATING.contentEquals(emissionsUnit.getOperatingStatusCode().getCode()) && emissionsUnit.getStatusYear() == null) {
 	
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
	                .collect(Collectors.groupingBy(feu -> feu.getUnitIdentifier().toLowerCase().trim()));
	        
	        
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
        
        //Only run the following checks is the Unit status is operating. Otherwise, these checks are moot b/c
        //the data will not be sent to EIS and the user shouldn't have to go back and update them. Only id, status, 
        //and status year are sent to EIS for units that are not operating.
        if (ConstantUtils.STATUS_OPERATING.contentEquals(emissionsUnit.getOperatingStatusCode().getCode())) {
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
            
        // checking unit processes with the same SCC code - duplicate processes 
        Map<Object, List<EmissionsProcess>> sccProcessMap = emissionsUnit.getEmissionsProcesses().stream()
        		.filter(ep -> ConstantUtils.STATUS_OPERATING.contentEquals(ep.getOperatingStatusCode().getCode()) && ep.getSccCode() != null)
				.collect(Collectors.groupingBy(EmissionsProcess::getSccCode));
        
        List<EmissionsProcess> allDuplicateProcesses = new ArrayList<EmissionsProcess>();
        List<EmissionsProcess> duplicateProcessAndFuelDataList = new ArrayList<EmissionsProcess>();
        List<EmissionsProcess> notDuplicateProcessList = new ArrayList<EmissionsProcess>();
        
        Boolean fuelUseRequired = null;
        
        if (sccProcessMap.size() > 0) {
			for (List<EmissionsProcess> pList : sccProcessMap.values()) {
				duplicateProcessAndFuelDataList.clear();
				notDuplicateProcessList.clear();
				allDuplicateProcesses.clear();
				fuelUseRequired = sccRepo.findById(pList.get(0).getSccCode()).orElse(null).getFuelUseRequired();
				
				// checks processes with the same SCC
				if (pList.size() > 1) {
					for (int i = 0; i < pList.size()-1; i++) {
						System.out.println(pList.get(i).getSccCode() + " " + pList.get(i).getOperatingStatusCode().getCode() + " "+fuelUseRequired);
						for (int j = i+1; j < pList.size(); j++) {
							
						  // check if process details are the same
						  Boolean diffProcessDetails = false; // process, reporting period type, rp appt details
						  Boolean diffOpDetails = false;
						  Boolean sameRpOpType = null;
						  
						  // compare process info
						  diffProcessDetails = diffProcessDetails || (!pList.get(i).getStatusYear().equals(pList.get(j).getStatusYear()));
						  
						  // TODO: update to compare processes with multiple reporting periods with the same operating type
						  // compare reporting period and operating details if reporting period exists
						  if (pList.get(i).getReportingPeriods().size() > 0 && pList.get(j).getReportingPeriods().size() > 0) {
							  OperatingDetail processA = pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0);
							  OperatingDetail processB = pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0);
							  
							  // compare operating details
							  diffOpDetails = diffOpDetails || (!processA.getActualHoursPerPeriod().equals(processB.getActualHoursPerPeriod()));
							  diffOpDetails = diffOpDetails || (!processA.getAvgWeeksPerPeriod().equals(processB.getAvgWeeksPerPeriod()));
							  diffOpDetails = diffOpDetails || (!processA.getAvgDaysPerWeek().equals(processB.getAvgDaysPerWeek()));
							  diffOpDetails = diffOpDetails || (!processA.getAvgHoursPerDay().equals(processB.getAvgHoursPerDay()));
							  diffOpDetails = diffOpDetails || (!processA.getPercentFall().equals(processB.getPercentFall()));
							  diffOpDetails = diffOpDetails || (!processA.getPercentSpring().equals(processB.getPercentSpring()));
							  diffOpDetails = diffOpDetails || (!processA.getPercentSummer().equals(processB.getPercentSummer()));
							  diffOpDetails = diffOpDetails || (!processA.getPercentWinter().equals(processB.getPercentWinter()));
							  
							  // compare reporting period
							  diffProcessDetails =  diffProcessDetails || (!pList.get(i).getReportingPeriods().get(0).getReportingPeriodTypeCode()
									  .equals(pList.get(j).getReportingPeriods().get(0).getReportingPeriodTypeCode()));
							  sameRpOpType = (pList.get(i).getReportingPeriods().get(0).getEmissionsOperatingTypeCode()
									  .equals(pList.get(j).getReportingPeriods().get(0).getEmissionsOperatingTypeCode()));
							  
						  } else {
						  // process details do not match if reporting period list sizes are not equal
						  diffProcessDetails = diffProcessDetails || ((pList.get(i).getReportingPeriods().size() > 0 && pList.get(j).getReportingPeriods().size() == 0)
								  || pList.get(i).getReportingPeriods().size() == 0 && pList.get(j).getReportingPeriods().size() > 0); 
						  }
						  
						  // compare release point apportionments 
						  int sameRpAppt = pList.get(j).getReleasePointAppts().size();
						  if (pList.get(i).getReleasePointAppts().size() == sameRpAppt) {
							  if (pList.get(i).getReleasePointAppts().size() > 0) {
								  for (ReleasePointAppt rpa1 : pList.get(i).getReleasePointAppts()) {
									  for (ReleasePointAppt rpa2 : pList.get(j).getReleasePointAppts()) {
										  if ((rpa1.getReleasePoint().getId() == rpa2.getReleasePoint().getId())
												  && rpa1.getPercent().compareTo(rpa2.getPercent()) == 0) {
											  if (((rpa1.getControlPath() != null && rpa2.getControlPath() != null)
												  && rpa1.getControlPath().getId() == rpa2.getControlPath().getId())
												  || (rpa1.getControlPath() == null && rpa2.getControlPath() == null)) {
												  --sameRpAppt;
											  }
										  }
									  }
								  }
								  // sameRpAppt should be 0 if all release point appt details match
								  diffProcessDetails = diffProcessDetails || (sameRpAppt != 0);
							  }
						  } else {
							  // release point appt details do not match if total size of list are not equal
							  diffProcessDetails = diffProcessDetails || (pList.get(i).getReleasePointAppts().size() != sameRpAppt);
						  }
						  
						  // TODO: compare pollutants
						  // duplicate processes cannot have same pollutants
						  
						  // processes considered duplicates
						  // same process details	same operating details	same reporting period op type
						  // TRUE					TRUE					TRUE			CHECK DUPLICATE FUEL
						  // processes considered not duplicates
						  // FALSE					FALSE/TRUE				TRUE			CHECK WARNING DUPLICATE
						  // TRUE					FALSE					TRUE			CHECK WARNING DUPLICATE
						  // TRUE					FALSE/TRUE				FALSE			NONE
						  // FALSE					FALSE/TRUE				FALSE			NONE
						  // note: any time the reporting period operating type is different the process is not a duplicate process
						  
						  // processes are the same if all the details are the same 
						  if (diffProcessDetails == false && diffOpDetails == false && sameRpOpType == true) {
							  
							  // add process to list if fuel data exists
							  if (checkFuelFields(pList.get(i).getReportingPeriods().get(0)) && !duplicateProcessAndFuelDataList.contains(pList.get(i))) {
								  duplicateProcessAndFuelDataList.add(pList.get(i));
							  }
							  if (checkFuelFields(pList.get(j).getReportingPeriods().get(0)) && !duplicateProcessAndFuelDataList.contains(pList.get(j))) {
								  duplicateProcessAndFuelDataList.add(pList.get(j));
							  }
							  
							  if (!allDuplicateProcesses.contains(pList.get(i))) {
								  allDuplicateProcesses.add(pList.get(i));
							  }
							  if (!allDuplicateProcesses.contains(pList.get(j))) {
								  allDuplicateProcesses.add(pList.get(j));
							  }
						  }
						  
						  // process has same SCC and process details are different and reporting operating types are the same
						  // if reporting period operating types are the same, operating details are different, and operating type is the same
						  if ((diffProcessDetails == true && sameRpOpType == true)
								  || (diffProcessDetails == false && diffOpDetails == true && sameRpOpType == true)) {
							  
							  if (!notDuplicateProcessList.contains(pList.get(i))) {
								  notDuplicateProcessList.add(pList.get(i));
							  }
							  if (!notDuplicateProcessList.contains(pList.get(j))) {
								  notDuplicateProcessList.add(pList.get(j));
							  }
							  
						  }
						}
						// check fuel use values for non duplicated processes if reporting period exists
						if (!allDuplicateProcesses.contains(pList.get(i)) && pList.get(i).getReportingPeriods().size() > 0) {
							result = checkFuelData(validatorContext, pList.get(i));
						}
					}
					
				// check fuel use conditions if process scc has only one process
				} else if (pList.size() == 1 && pList.get(0).getReportingPeriods().size() > 0) {
					result = checkFuelData(validatorContext, pList.get(0));
				}
				
				// check fuel use conditions for duplicate process only if one of the processes of a given SCC has fuel data
				if (duplicateProcessAndFuelDataList.size() == 1) {
					result = checkFuelData(validatorContext, duplicateProcessAndFuelDataList.get(0));
				}
				
				// only one process for a given SCC code can have fuel use data
				// and if all processes for a given SCC code do not have fuel use data and fuel use is required
				if ((duplicateProcessAndFuelDataList.size() > 1)
						|| (duplicateProcessAndFuelDataList.size() == 0 && allDuplicateProcesses.size() > 1 && fuelUseRequired == true)) {

					result = false;
					context.addFederalError(
							ValidationField.PERIOD_DUP_SCC_FUEL_USE.value(),
							"emissionsUnit.emissionsProcess.sccDuplicate.fuelUseData",
							createValidationDetails(emissionsUnit),
							pList.get(0).getSccCode());
				}
				
				// warn if there are multiple processes for a given SCC 
				if (notDuplicateProcessList.size() > 0) {
					
					result = false;
					context.addFederalWarning(
							ValidationField.EMISSIONS_UNIT_PROCESS.value(),
							"emissionsUnit.emissionsProcess.sccDuplicate.notDupProcessWarning",
							createValidationDetails(emissionsUnit),
							notDuplicateProcessList.get(0).getSccCode());
				}
				
			}
        }

        // Process identifier must be unique within unit
        Map<Object, List<EmissionsProcess>> epMap = emissionsUnit.getEmissionsProcesses().stream()
            .filter(ep -> ep.getEmissionsProcessIdentifier() != null)
            .collect(Collectors.groupingBy(eu -> eu.getEmissionsProcessIdentifier().toLowerCase().trim()));
        
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
    
    // checks duplicate process for any of the fuel data fields exist
    private boolean checkFuelFields(ReportingPeriod period) {
    	return ((period.getFuelUseValue() != null || period.getFuelUseUom() != null || period.getFuelUseMaterialCode() != null
    			|| period.getFuelUseUom() != null || period.getFuelUseMaterialCode() != null || period.getFuelUseValue() != null
    			|| period.getHeatContentValue() != null || period.getHeatContentUom() != null || period.getHeatContentUom() != null || period.getHeatContentValue() != null));
    }
    
    // Fuel Use Input Checks
    private boolean checkFuelData(ValidatorContext validatorContext, EmissionsProcess process) {
    	CefValidatorContext context = getCefValidatorContext(validatorContext);
        boolean result = true;
    	PointSourceSccCode isFuelUsePointSourceSccCode = sccRepo.findById(process.getSccCode()).orElse(null);
    	ReportingPeriod period = process.getReportingPeriods().get(0);
    	
    	if (isFuelUsePointSourceSccCode.getFuelUseRequired()) {
    		
    		// Fuel Material, Fuel Value, and Fuel UoM when the Process SCC requires fuel use
    		if (period.getFuelUseValue() == null || period.getFuelUseUom() == null || period.getFuelUseMaterialCode() == null) {
    			
    				result = false;
		            context.addFederalError(
		            		ValidationField.PERIOD_FUEL_USE_VALUES.value(),
		            		"reportingPeriod.fuelUseValues.required", 
		            		createEmissionsProcessValidationDetails(process),
		            		process.getSccCode());
		    }
    		
    		// Heat Content Value and Heat Content UoM when the Process SCC requires fuel use
    		if (period.getHeatContentUom() == null || period.getHeatContentValue() == null) {
    			
    				result = false;
		            context.addFederalError(
		            		ValidationField.PERIOD_HEAT_CONTENT_VALUES.value(),
		            		"reportingPeriod.heatContentValues.required", 
		            		createEmissionsProcessValidationDetails(process),
		            		process.getSccCode());
		     }
    		
    	// when Process SCC does not require fuel use
    	} else {
    		// warning is generated that all fuel use fields must be reported for any to be submitted with the report.
    		if ((period.getFuelUseValue() != null || period.getFuelUseUom() != null || period.getFuelUseMaterialCode() != null ) &&
	        	(period.getFuelUseUom() == null || period.getFuelUseMaterialCode() == null || period.getFuelUseValue() == null)) {
    			
    				result = false;
		            context.addFederalWarning(
		            		ValidationField.PERIOD_FUEL_USE_VALUES.value(),
		            		"reportingPeriod.fuelUseValues.optionalFields.required", 
		            		createEmissionsProcessValidationDetails(process));
		            
	        }
    		
    		// warning is generated that all Heat Content fields must be reported for any to be submitted with the report.
    		if ((period.getHeatContentValue() != null || period.getHeatContentUom() != null) &&
		        	(period.getHeatContentUom() == null || period.getHeatContentValue() == null)) {
    			
    				result = false;
		            context.addFederalWarning(
		            		ValidationField.PERIOD_HEAT_CONTENT_VALUES.value(),
		            		"reportingPeriod.heatContentValues.optionalFields.required", 
		            		createEmissionsProcessValidationDetails(process));
			            
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
