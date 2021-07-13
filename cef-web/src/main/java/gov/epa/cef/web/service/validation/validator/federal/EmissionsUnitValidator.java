package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.PointSourceSccCode;
import gov.epa.cef.web.domain.Pollutant;
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
import java.util.Objects;
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
        if ((emissionsUnit.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().getFacilitySourceTypeCode() == null
        		|| !ConstantUtils.FACILITY_SOURCE_LANDFILL_CODE.contentEquals(emissionsUnit.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().getFacilitySourceTypeCode().getCode()))) {

            //if the unit is temporarily shutdown, then the underlying processes must also be temporarily or permanently shutdown
            if (ConstantUtils.STATUS_TEMPORARILY_SHUTDOWN.contentEquals(emissionsUnit.getOperatingStatusCode().getCode())) {
                List<EmissionsProcess> epList = emissionsUnit.getEmissionsProcesses().stream()
                    .filter(emissionsProcess -> !ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode())
                        && !ConstantUtils.STATUS_TEMPORARILY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode()))
                    .collect(Collectors.toList());

                for (EmissionsProcess ep : epList) {
                    result = false;
                    context.addFederalError(
                        ValidationField.PROCESS_STATUS_CODE.value(),
                        "emissionsProcess.statusTypeCode.temporarilyShutdown",
                        createEmissionsProcessValidationDetails(ep));
                }

            } 

            if (ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsUnit.getOperatingStatusCode().getCode())) {
            	
            	// Warning if unit operation status is permanently shutdown unit will not be copied forward
            	result = false;
                context.addFederalWarning(
                    ValidationField.EMISSIONS_UNIT_STATUS_CODE.value(),
                    "emissionsUnit.statusTypeCode.psNotCopied",
                    createValidationDetails(emissionsUnit));
                
                // Warning if unit operation status is permanently shutdown, process will not be copied forward
                for (EmissionsProcess ep : emissionsUnit.getEmissionsProcesses()) {

                	result = false;
        			context.addFederalWarning(
        					ValidationField.PROCESS_STATUS_CODE.value(),
        					"emissionsProcess.statusTypeCode.notCopied",
        					createEmissionsProcessValidationDetails(ep));
                }
                
                List<EmissionsProcess> epList = emissionsUnit.getEmissionsProcesses().stream()
                    .filter(emissionsProcess -> !ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode()))
                    .collect(Collectors.toList());

                // If the unit is permanently shutdown, then the underlying processes must also be permanently shutdown
                for (EmissionsProcess ep : epList) {
        			
                    result = false;
                    context.addFederalError(
                        ValidationField.PROCESS_STATUS_CODE.value(),
                        "emissionsProcess.statusTypeCode.permanentShutdown",
                        createEmissionsProcessValidationDetails(ep));
                }
                
            } else {
            	// Warning if unit operating status is OP or TS and process operation status is permanently shutdown, process will not be copied forward
            	for (EmissionsProcess ep : emissionsUnit.getEmissionsProcesses()) {
            		if (ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(ep.getOperatingStatusCode().getCode())) {

	                	result = false;
	        			context.addFederalWarning(
	        					ValidationField.PROCESS_STATUS_CODE.value(),
	        					"emissionsProcess.statusTypeCode.psNotCopied",
	        					createEmissionsProcessValidationDetails(ep));
            		}
                }
            }
        }
        
        if (emissionsUnit.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().getFacilitySourceTypeCode() != null 
        		&& ConstantUtils.FACILITY_SOURCE_LANDFILL_CODE.contentEquals(emissionsUnit.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().getFacilitySourceTypeCode().getCode())) {
        	
        	// Warning if the facility source type code is landfill and there are no processes or all processes are permanently shutdown,
            // then unit will not be copied forward
        	List<EmissionsProcess> epList = emissionsUnit.getEmissionsProcesses().stream()
                    .filter(emissionsProcess -> !ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsProcess.getOperatingStatusCode().getCode()))
                    .collect(Collectors.toList());
        	
        	if (epList.isEmpty() && ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionsUnit.getOperatingStatusCode().getCode())) {
        		
        		result = false;
                context.addFederalWarning(
                    ValidationField.EMISSIONS_UNIT_STATUS_CODE.value(),
                    "emissionsUnit.statusTypeCode.psNotCopied",
                    createValidationDetails(emissionsUnit));
        	}
        	
        	// Warning if process operation status is permanently shutdown process will not be copied forward
        	for (EmissionsProcess ep : emissionsUnit.getEmissionsProcesses()) {
	        	if (ConstantUtils.STATUS_PERMANENTLY_SHUTDOWN.contentEquals(ep.getOperatingStatusCode().getCode())) { 
	        		
	            	result = false;
	    			context.addFederalWarning(
	    					ValidationField.PROCESS_STATUS_CODE.value(),
	    					"emissionsProcess.statusTypeCode.psNotCopied",
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
	                .collect(Collectors.groupingBy(feu -> feu.getUnitIdentifier().trim().toLowerCase()));


	        for (List<EmissionsUnit> euList : euMap.values()) {

	        	if (euList.size() > 1 && euList.get(0).getUnitIdentifier().trim().toLowerCase().contentEquals(emissionsUnit.getUnitIdentifier().trim().toLowerCase())) {

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

                for (String code : typeCodeWarn) {
                    if (code.contentEquals(emissionsUnit.getUnitTypeCode().getCode())) {

                        result = false;
                        context.addFederalWarning(
                            ValidationField.EMISSIONS_UNIT_CAPACITY.value(), "emissionsUnit.capacity.check",
                            createValidationDetails(emissionsUnit),
                            emissionsUnit.getUnitTypeCode().getDescription());
                    }
                }
                ;
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
        List<EmissionsProcess> duplicateProcessNoFuelDataList = new ArrayList<EmissionsProcess>();
        List<EmissionsProcess> duplicateProcessSingleFuelList = new ArrayList<EmissionsProcess>();
        List<EmissionsProcess> notDuplicateProcessList = new ArrayList<EmissionsProcess>();
        List<Pollutant> duplicateProcessDuplicateEmissionsList = new ArrayList<Pollutant>();

        Boolean fuelUseRequired = null;

        if (sccProcessMap.size() > 0) {
            for (List<EmissionsProcess> pList : sccProcessMap.values()) {
                duplicateProcessAndFuelDataList.clear();
                duplicateProcessNoFuelDataList.clear();
                duplicateProcessSingleFuelList.clear();
                notDuplicateProcessList.clear();
                allDuplicateProcesses.clear();
                duplicateProcessDuplicateEmissionsList.clear();
                fuelUseRequired = sccRepo.findById(pList.get(0).getSccCode()).orElse(null).getFuelUseRequired();

                // checks processes with the same SCC
                if (pList.size() > 1) {
                    for (int i = 0; i < pList.size() - 1; i++) {
                        for (int j = i + 1; j < pList.size(); j++) {

                            // check if process details are the same
                            boolean diffProcessDetails = false; // process, reporting period type, rp appt details
                            boolean diffOpDetails = false;
                            boolean sameRpOpType = false;

                            // compare process info
                            diffProcessDetails = diffProcessDetails || (!Objects.equals(pList.get(i).getStatusYear(), pList.get(j).getStatusYear()));

                            // TODO: update to compare processes with multiple reporting periods with the same operating type
                            // compare reporting period and operating details if reporting period exists
                            if (pList.get(i).getReportingPeriods().size() > 0 && pList.get(j).getReportingPeriods().size() > 0) {

                                // should be 1 detail per period. If for some reason both periods don't have 1, they're different
                                if (pList.get(i).getReportingPeriods().get(0).getOperatingDetails().size() != pList.get(j).getReportingPeriods().get(0).getOperatingDetails().size()) {

                                    diffOpDetails = true;

                                // if they have the same number and on is empty, they're both empty and we can skip details checks
                                } else if (!pList.get(i).getReportingPeriods().get(0).getOperatingDetails().isEmpty()) {

                                    OperatingDetail processA = pList.get(i).getReportingPeriods().get(0).getOperatingDetails().get(0);
                                    OperatingDetail processB = pList.get(j).getReportingPeriods().get(0).getOperatingDetails().get(0);
    
                                    // compare operating details
                                    diffOpDetails = diffOpDetails || (!Objects.equals(processA.getActualHoursPerPeriod(), processB.getActualHoursPerPeriod()));
                                    diffOpDetails = diffOpDetails || (!Objects.equals(processA.getAvgWeeksPerPeriod(), processB.getAvgWeeksPerPeriod()));
                                    diffOpDetails = diffOpDetails || (!Objects.equals(processA.getAvgDaysPerWeek(), processB.getAvgDaysPerWeek()));
                                    diffOpDetails = diffOpDetails || (!Objects.equals(processA.getAvgHoursPerDay(), processB.getAvgHoursPerDay()));
                                    diffOpDetails = diffOpDetails || (!Objects.equals(processA.getPercentFall(), processB.getPercentFall()));
                                    diffOpDetails = diffOpDetails || (!Objects.equals(processA.getPercentSpring(), processB.getPercentSpring()));
                                    diffOpDetails = diffOpDetails || (!Objects.equals(processA.getPercentSummer(), processB.getPercentSummer()));
                                    diffOpDetails = diffOpDetails || (!Objects.equals(processA.getPercentWinter(), processB.getPercentWinter()));
    
                                    // compare reporting period
                                    diffProcessDetails = diffProcessDetails || (!Objects.equals(pList.get(i).getReportingPeriods().get(0).getReportingPeriodTypeCode(),
                                        pList.get(j).getReportingPeriods().get(0).getReportingPeriodTypeCode()));
                                    sameRpOpType = Objects.equals(pList.get(i).getReportingPeriods().get(0).getEmissionsOperatingTypeCode(),
                                        pList.get(j).getReportingPeriods().get(0).getEmissionsOperatingTypeCode());
                                }

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
                                if (checkFuelFields(pList.get(i).getReportingPeriods().get(0)) && !duplicateProcessAndFuelDataList.contains(pList.get(i))
                                    && checkFuelFields(pList.get(j).getReportingPeriods().get(0)) && !duplicateProcessAndFuelDataList.contains(pList.get(j))) {
                                    duplicateProcessAndFuelDataList.add(pList.get(i));
                                    duplicateProcessAndFuelDataList.add(pList.get(j));

                                    // add process to list if no fuel data exists
                                } else if (!checkFuelFields(pList.get(i).getReportingPeriods().get(0)) && !duplicateProcessNoFuelDataList.contains(pList.get(i))
                                    && !checkFuelFields(pList.get(j).getReportingPeriods().get(0)) && !duplicateProcessNoFuelDataList.contains(pList.get(j))) {
                                    duplicateProcessNoFuelDataList.add(pList.get(i));
                                    duplicateProcessNoFuelDataList.add(pList.get(j));

                                    // add process to list if only one process has fuel data
                                } else {
                                    if (checkFuelFields(pList.get(i).getReportingPeriods().get(0)) && !duplicateProcessSingleFuelList.contains(pList.get(i))) {
                                        duplicateProcessSingleFuelList.add(pList.get(i));
                                    }
                                    if (checkFuelFields(pList.get(j).getReportingPeriods().get(0)) && !duplicateProcessSingleFuelList.contains(pList.get(j))) {
                                        duplicateProcessSingleFuelList.add(pList.get(j));
                                    }
                                }

                                if (!allDuplicateProcesses.contains(pList.get(i))) {
                                    allDuplicateProcesses.add(pList.get(i));
                                }
                                if (!allDuplicateProcesses.contains(pList.get(j))) {
                                    allDuplicateProcesses.add(pList.get(j));
                                }

                                // duplicate processes cannot have same pollutants
                                // one error message per duplicated emission
                                List<Emission> processA_emissions = pList.get(i).getReportingPeriods().get(0).getEmissions();
                                List<Emission> processB_emissions = pList.get(j).getReportingPeriods().get(0).getEmissions();
                                if (processA_emissions.size() > 0 && processB_emissions.size() > 0) {
                                    for (Emission eA : processA_emissions) {
                                        for (Emission eB : processB_emissions) {
                                            if (eA.getPollutant().getPollutantCode().equals(eB.getPollutant().getPollutantCode())) {
                                                if (!duplicateProcessDuplicateEmissionsList.contains(eA.getPollutant())) {
                                                    duplicateProcessDuplicateEmissionsList.add(eA.getPollutant());
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // process has same SCC and process details are different and reporting operating types are the same
                            // if reporting period operating types are the same, operating details are different, and process details are the same
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

                    // check fuel use values for last value in pList if it is not a duplicated processes and reporting period exists
                    if (!allDuplicateProcesses.contains(pList.get(pList.size() - 1)) && pList.get(pList.size() - 1).getReportingPeriods().size() > 0) {
                        result = checkFuelData(validatorContext, pList.get(pList.size() - 1));
                    }

                    // check fuel use conditions if process scc has only one process
                } else if (pList.size() == 1 && pList.get(0).getReportingPeriods().size() > 0) {
                    result = checkFuelData(validatorContext, pList.get(0));
                }

                // check fuel use conditions for duplicate process only if one of the processes of a given SCC has fuel data
                if (duplicateProcessSingleFuelList.size() == 1) {
                    result = checkFuelData(validatorContext, duplicateProcessSingleFuelList.get(0));
                }

                // error generated if more than one process for a given SCC code has fuel use data,
                // and if duplicate processes for a given SCC code do not have fuel use data and fuel use is required
                if (duplicateProcessAndFuelDataList.size() > 1
                    || (duplicateProcessNoFuelDataList.size() > 1 && duplicateProcessNoFuelDataList.size() == allDuplicateProcesses.size() && fuelUseRequired == true)) {

                    result = false;
                    context.addFederalError(
                        ValidationField.PERIOD_DUP_SCC_FUEL_USE.value(),
                        "emissionsUnit.emissionsProcess.sccDuplicate.fuelUseData",
                        createValidationDetails(emissionsUnit),
                        pList.get(0).getSccCode());
                }

                // error generated for each duplicate pollutant of duplicated process
                if (duplicateProcessDuplicateEmissionsList.size() > 0) {
                    for (Pollutant e : duplicateProcessDuplicateEmissionsList) {

                        result = false;
                        context.addFederalError(
                            ValidationField.EMISSIONS_UNIT_PROCESS.value(),
                            "emissionsUnit.emissionsProcess.sccDuplicate.duplicatePollutant",
                            createValidationDetails(emissionsUnit),
                            pList.get(0).getSccCode(),
                            e.getPollutantName());
                    }
                }

                // warning generated if there are multiple processes for a given SCC
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
            if ((period.getFuelUseValue() != null || period.getFuelUseUom() != null || period.getFuelUseMaterialCode() != null) &&
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
