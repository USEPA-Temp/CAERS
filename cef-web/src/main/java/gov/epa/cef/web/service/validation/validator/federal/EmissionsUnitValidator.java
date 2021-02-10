package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsUnit;
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
        
            
	        // checking unit processes with the same SCC code - duplicate processes 
	        Map<Object, List<EmissionsProcess>> sccProcessMap = emissionsUnit.getEmissionsProcesses().stream()
	        		.filter(ep -> ep.getSccCode() != null)
					.collect(Collectors.groupingBy(EmissionsProcess::getSccCode));
	        
	        List<EmissionsProcess> duplicateProcessAndFuelDataList = new ArrayList<EmissionsProcess>();
	        List<EmissionsProcess> notDuplicteProcessList = new ArrayList<EmissionsProcess>();
	        List<EmissionsProcess> totalDuplicateProcesses = new ArrayList<EmissionsProcess>();
	        Boolean fuelUseRequired = null;
	        
	        if (sccProcessMap.size() > 0) {
				for (List<EmissionsProcess> pList : sccProcessMap.values()) {
					duplicateProcessAndFuelDataList.clear();
					notDuplicteProcessList.clear();
					totalDuplicateProcesses.clear();
					fuelUseRequired = sccRepo.findById(pList.get(0).getSccCode()).orElse(null).getFuelUseRequired();// ? pList.get(0).getSccCode() : null;
					
					// checks processes with the same SCC
					if (pList.size() > 1) {
						for (int i = 0; i < pList.size()-1; i++) {
							for (int j = i+1; j < pList.size(); j++) {
								
							  // check if process details are the same
							  List<Boolean> sameProcessDetails = new ArrayList<Boolean>(); // process, reporting period type, rp appt details
							  List<Boolean> sameOpDetails = new ArrayList<Boolean>();
							  Boolean sameRpOpType = null;
							  
							  // compare process info
							  sameProcessDetails.add(pList.get(i).getOperatingStatusCode().equals(pList.get(j).getOperatingStatusCode()));
							  sameProcessDetails.add(pList.get(i).getStatusYear().equals(pList.get(j).getStatusYear()));
							  
							  // compare reporting period and operating details if reporting period exists
							  if (pList.get(i).getReportingPeriods().size() > 0 && pList.get(j).getReportingPeriods().size() > 0) {
								  // compare operating details
								  sameOpDetails.add(pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0).getActualHoursPerPeriod()
										  .equals(pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0).getActualHoursPerPeriod()));
								  sameOpDetails.add(pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0).getAvgWeeksPerPeriod()
										  .equals(pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0).getAvgWeeksPerPeriod()));
								  sameOpDetails.add(pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0).getAvgDaysPerWeek()
										  .equals(pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0).getAvgDaysPerWeek()));
								  sameOpDetails.add(pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0).getAvgHoursPerDay()
										  .equals(pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0).getAvgHoursPerDay()));
								  sameOpDetails.add(pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0).getPercentFall()
										  .equals(pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0).getPercentFall()));
								  sameOpDetails.add(pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0).getPercentSpring()
										  .equals(pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0).getPercentSpring()));
								  sameOpDetails.add(pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0).getPercentSummer()
										  .equals(pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0).getPercentSummer()));
								  sameOpDetails.add(pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0).getPercentWinter()
										  .equals(pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0).getPercentWinter()));
								  
								  // compare reporting period
								  sameProcessDetails.add(pList.get(i).getReportingPeriods().get(0).getReportingPeriodTypeCode()
										  .equals(pList.get(j).getReportingPeriods().get(0).getReportingPeriodTypeCode()));
								  sameRpOpType = (pList.get(i).getReportingPeriods().get(0).getEmissionsOperatingTypeCode()
										  .equals(pList.get(j).getReportingPeriods().get(0).getEmissionsOperatingTypeCode()));
								  
							  } else if ((pList.get(i).getReportingPeriods().size() > 0 && pList.get(j).getReportingPeriods().size() == 0)
									  || pList.get(i).getReportingPeriods().size() == 0 && pList.get(j).getReportingPeriods().size() > 0) {
								  sameProcessDetails.add(false);
							  } else {
								  sameProcessDetails.add(true);
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
									  if (sameRpAppt == 0) {
										  sameProcessDetails.add(true);
									  } else {
										  sameProcessDetails.add(false);
									  }
								  } else {
									  sameProcessDetails.add(true);
								  }
							  } else {
								  sameProcessDetails.add(false);
							  }
							  
							  // TODO: compare pollutants
							  // duplicate processes cannot have same pollutants
							  
							  // processes considered duplicates
							  // same process details	same operating details	same reporting period op type
							  // TRUE					TRUE					TRUE			CHECK FUEL
							  // processes considered not duplicates
							  // FALSE					FALSE/TRUE				TRUE			CHECK WARNING
							  // TRUE					FALSE					TRUE			CHECK WARNING
							  // TRUE					FALSE/TRUE				FALSE			NONE
							  // FALSE					FALSE/TRUE				FALSE			NONE
							  // note: any time the reporting period operating type is different the process is not a duplicate process
							  
							  // processes are the same if all the details are the same 
							  if (!sameProcessDetails.contains(false) && !sameOpDetails.contains(false) && sameRpOpType == true){
								  
								  // add process to list if fuel data exists
								  if (checkFuelFields(pList.get(i).getReportingPeriods().get(0)) && !duplicateProcessAndFuelDataList.contains(pList.get(i))) {
									  duplicateProcessAndFuelDataList.add(pList.get(i));
								  }
								  if (checkFuelFields(pList.get(j).getReportingPeriods().get(0)) && !duplicateProcessAndFuelDataList.contains(pList.get(j))) {
									  duplicateProcessAndFuelDataList.add(pList.get(j));
								  }
								  
								  if (!totalDuplicateProcesses.contains(pList.get(i))) {
									  totalDuplicateProcesses.add(pList.get(i));
								  }
								  if (!totalDuplicateProcesses.contains(pList.get(j))) {
									  totalDuplicateProcesses.add(pList.get(j));
								  }
							  }
							  
							  // process has same SCC and process details are different and reporting operating types are the same
							  // if reporting period operating types are the same, operating details are different, and operating type is the same
							  if ((sameProcessDetails.contains(false) && sameRpOpType == true)
									  || (!sameProcessDetails.contains(false) && sameOpDetails.contains(false) && sameRpOpType == true)) {
								 
								  if (!notDuplicteProcessList.contains(pList.get(i).getEmissionsProcessIdentifier())) {
									  notDuplicteProcessList.add(pList.get(i));
								  }
								  if (!notDuplicteProcessList.contains(pList.get(j).getEmissionsProcessIdentifier())) {
									  notDuplicteProcessList.add(pList.get(j));
								  }
								  
								  // check fuel use values for non duplicated process if reporting period exists
								  if (pList.get(i).getReportingPeriods().size() > 0) {
									  result = checkFuelData(validatorContext, pList.get(i));
								  }
							  }
							  
							  // if operating types are different, processes are duplicate. check fuel use
							  if (sameRpOpType == false) {
								  result = checkFuelData(validatorContext, pList.get(i));
							  }
							}
						}
						
					// check fuel use conditions if there is only one process with 
					} else if (pList.get(0).getReportingPeriods().size() == 1) {
							result = checkFuelData(validatorContext, pList.get(0));
					}
					
					// one process for a given SCC code can have fuel use data
					// and if all processes for a given SCC code do not have fuel use data
					if ((duplicateProcessAndFuelDataList.size() > 1 && fuelUseRequired == true)
							|| (duplicateProcessAndFuelDataList.size() == 0 && totalDuplicateProcesses.size() > 1 && fuelUseRequired == true)) {
						
						result = false;
						context.addFederalError(
								ValidationField.PERIOD_DUP_SCC_FUEL_USE.value(),
								"emissionsUnit.emissionsProcess.sccDuplicate.fuelUseData",
								createValidationDetails(emissionsUnit),
								pList.get(0).getSccCode());
					}
					
					// warn if there are multiple processes for a given SCC 
					if (notDuplicteProcessList.size() > 0) {
						
						result = false;
						context.addFederalWarning(
								ValidationField.EMISSIONS_UNIT_PROCESS.value(),
								"emissionsUnit.emissionsProcess.sccDuplicate.notDupProcessWarning",
								createValidationDetails(emissionsUnit),
								notDuplicteProcessList.get(0).getSccCode());
					}
					
					// check fuel use conditions if only one process of a given SCC has fuel data
					if (duplicateProcessAndFuelDataList.size() == 1) {
						result = checkFuelData(validatorContext, duplicateProcessAndFuelDataList.get(0));
					}
				}
	        }
        }

        // Process identifier must be unique within unit
        Map<Object, List<EmissionsProcess>> epMap = emissionsUnit.getEmissionsProcesses().stream()
            .filter(ep -> ep.getEmissionsProcessIdentifier() != null)
            .collect(Collectors.groupingBy(eu -> eu.getEmissionsProcessIdentifier().toLowerCase()));
        
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
    
    private boolean checkFuelFields(ReportingPeriod period) {
    	return ((period.getFuelUseValue() != null || period.getFuelUseUom() != null || period.getFuelUseMaterialCode() != null
    			|| period.getFuelUseUom() != null || period.getFuelUseMaterialCode() != null || period.getFuelUseValue() != null
    			|| period.getHeatContentValue() != null || period.getHeatContentUom() != null || period.getHeatContentUom() != null || period.getHeatContentValue() != null));
    }
    private boolean checkFuelData(ValidatorContext validatorContext, EmissionsProcess process) {
    	CefValidatorContext context = getCefValidatorContext(validatorContext);
        boolean result = true;
    	PointSourceSccCode isFuelUsePointSourceSccCode = sccRepo.findById(process.getSccCode()).orElse(null);
    	ReportingPeriod period = process.getReportingPeriods().get(0);
    	
    	if (isFuelUsePointSourceSccCode.getFuelUseRequired()) {
    		if (period.getFuelUseValue() == null || period.getFuelUseUom() == null || period.getFuelUseMaterialCode() == null) {
    			
    				result = false;
		            context.addFederalError(
		            		ValidationField.PERIOD_FUEL_USE_VALUES.value(),
		            		"reportingPeriod.fuelUseValues.required", 
		            		createEmissionsProcessValidationDetails(process),
		            		process.getSccCode());
		    }
    		
    		if (period.getHeatContentUom() == null || period.getHeatContentValue() == null) {
    			
    				result = false;
		            context.addFederalError(
		            		ValidationField.PERIOD_HEAT_CONTENT_VALUES.value(),
		            		"reportingPeriod.heatContentValues.required", 
		            		createEmissionsProcessValidationDetails(process),
		            		process.getSccCode());
		     }
    		
    	} else {
    		if ((period.getFuelUseValue() != null || period.getFuelUseUom() != null || period.getFuelUseMaterialCode() != null ) &&
	        	(period.getFuelUseUom() == null || period.getFuelUseMaterialCode() == null || period.getFuelUseValue() == null)) {
    			
    				result = false;
		            context.addFederalWarning(
		            		ValidationField.PERIOD_FUEL_USE_VALUES.value(),
		            		"reportingPeriod.fuelUseValues.optionalFields.required", 
		            		createEmissionsProcessValidationDetails(process));
		            
	        }
    		
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
