/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.EisTriXref;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFactor;
import gov.epa.cef.web.domain.EmissionFormulaVariable;
import gov.epa.cef.web.domain.EmissionsByFacilityAndCAS;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EnergyConversionFactor;
import gov.epa.cef.web.domain.FacilitySourceTypeCode;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.EisTriXrefRepository;
import gov.epa.cef.web.repository.EmissionFactorRepository;
import gov.epa.cef.web.repository.EmissionFormulaVariableRepository;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.EmissionsByFacilityAndCASRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.EnergyConversionFactorRepository;
import gov.epa.cef.web.repository.ReportHistoryRepository;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.service.EmissionService;
import gov.epa.cef.web.service.dto.EmissionBulkEntryDto;
import gov.epa.cef.web.service.dto.EmissionBulkEntryHolderDto;
import gov.epa.cef.web.service.dto.EmissionDto;
import gov.epa.cef.web.service.dto.EmissionFormulaVariableCodeDto;
import gov.epa.cef.web.service.dto.EmissionFormulaVariableDto;
import gov.epa.cef.web.service.dto.EmissionsByFacilityAndCASDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionFormulaVariableBulkUploadDto;
import gov.epa.cef.web.service.mapper.BulkUploadMapper;
import gov.epa.cef.web.service.mapper.EmissionMapper;
import gov.epa.cef.web.service.mapper.EmissionsByFacilityAndCASMapper;
import gov.epa.cef.web.util.CalculationUtils;
import gov.epa.cef.web.util.ConstantUtils;
import gov.epa.cef.web.util.MassUomConversion;

@Service
public class EmissionServiceImpl implements EmissionService {

    Logger logger = LoggerFactory.getLogger(EmissionServiceImpl.class);

    @Autowired
    private EmissionRepository emissionRepo;

    @Autowired
    private EmissionsByFacilityAndCASRepository emissionsByFacilityAndCASRepo;

    @Autowired
    private EmissionsReportRepository emissionsReportRepo;

    @Autowired
    private ReportingPeriodRepository periodRepo;
    
    @Autowired
    private ReportHistoryRepository historyRepo;

    @Autowired
    private UnitMeasureCodeRepository uomRepo;
    
    @Autowired
    private EnergyConversionFactorRepository cfRepo;
    
    @Autowired
    private EmissionFactorRepository efRepo;

    @Autowired
    private EmissionFactorServiceImpl efService;

    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;

    @Autowired
    private EmissionMapper emissionMapper;

    @Autowired
    private EmissionsByFacilityAndCASMapper emissionsByFacilityAndCASMapper;
    
    @Autowired
    private EisTriXrefRepository eisTriXrefRepo;
    
    @Autowired
    private EmissionFormulaVariableRepository variablesRepo;
    
    @Autowired
    private BulkUploadMapper bulkUploadMapper;

    private static final String POINT_EMISSION_RELEASE_POINT = "stack";
    private static final int TWO_DECIMAL_POINTS = 2;

    private enum RETURN_CODE {NO_EMISSIONS_REPORT, NO_EMISSIONS_REPORTED_FOR_CAS, EMISSIONS_FOUND, MULTIPLE_FACILITIES, NO_EIS_FACILITIES}

    /**
     * Create a new emission from a DTO object
     */
    public EmissionDto create(EmissionDto dto) {

        Emission emission = emissionMapper.fromDto(dto);

        emission.getVariables().forEach(v -> {
            v.setEmission(emission);
        });

        emission.setCalculatedEmissionsTons(calculateEmissionTons(emission));

        EmissionDto result = emissionMapper.toDto(emissionRepo.save(emission));
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), EmissionRepository.class);
        return result;
    }

    @Override
    public EmissionDto retrieveById(Long id) {

        return this.emissionRepo
            .findById(id)
            .map(e -> emissionMapper.toDto(e))
            .orElse(null);
    }

    public EmissionDto retrieveWithVariablesById(Long id) {

        EmissionDto result = this.emissionRepo
                                .findById(id)
                                .map(e -> emissionMapper.toDto(e))
                                .orElse(null);

        // add missing emission factor variables
        if (result.getFormulaIndicator()) {
            List<EmissionFormulaVariableCodeDto> variables = this.efService.parseFormulaVariables(result.getEmissionsFactorFormula());
            List<String> existingVariables = result.getVariables().stream()
                    .map(EmissionFormulaVariableDto::getVariableCode)
                    .map(EmissionFormulaVariableCodeDto::getCode)
                    .collect(Collectors.toList());

            result.getVariables().addAll(variables.stream()
                .filter(v -> !existingVariables.contains(v.getCode()))
                .map(v -> {
                    EmissionFormulaVariableDto dto = new EmissionFormulaVariableDto();
                    dto.setVariableCode(v);
                    return dto;
                }).collect(Collectors.toList()));
        }

        return result;
    }

    /**
     * Update an existing emission from a DTO
     */
    public EmissionDto update(EmissionDto dto) {

        Emission emission = emissionRepo.findById(dto.getId()).orElse(null);
        emissionMapper.updateFromDto(dto, emission);

        // Match up variables with the existing value if it exists to preserve id, created_by, etc.
        List<EmissionFormulaVariable> variables = new ArrayList<>();
        dto.getVariables().forEach(v -> {
            Optional<EmissionFormulaVariable> variable = emission.getVariables().stream().filter(ov -> ov.getId().equals(v.getId())).findFirst();
            if (variable.isPresent()) {
                variables.add(emissionMapper.updateFormulaVariableFromDto(v, variable.get()));
            } else {
                variables.add(emissionMapper.formulaVariableFromDto(v));
            }
        });
        emission.setVariables(variables);

        emission.getVariables().forEach(v -> {
            v.setEmission(emission);
        });

        EmissionDto result = emissionMapper.toDto(update(emission));
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), EmissionRepository.class);
        return result;
    }

    /**
     * Update an Emission directly and calculate all calculated values
     * @param emission
     * @return
     */
    private Emission update(Emission emission) {

        emission.setCalculatedEmissionsTons(calculateEmissionTons(emission));

        Emission result = emissionRepo.save(emission);
        return result;
    }

    /**
     * Delete an Emission for a given id
     * @param id
     */
    public void delete(Long id) {
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(id), EmissionRepository.class);
        emissionRepo.deleteById(id);
    }

    /**
     * Retrieve Emissions grouped by Reporting Period for Bulk Entry
     * @param facilitySiteId
     * @return
     */
    public List<EmissionBulkEntryHolderDto> retrieveBulkEntryEmissionsForFacilitySite(Long facilitySiteId) {

        List<ReportingPeriod> entities = periodRepo.findByFacilitySiteId(facilitySiteId).stream()
                .filter(rp -> !"PS".equals(rp.getEmissionsProcess().getOperatingStatusCode().getCode()))
                .filter(rp -> {
                    FacilitySourceTypeCode typeCode = rp.getEmissionsProcess().getEmissionsUnit().getFacilitySite().getFacilitySourceTypeCode();
                    return !"PS".equals(rp.getEmissionsProcess().getEmissionsUnit().getOperatingStatusCode().getCode())
                            || (typeCode != null && ConstantUtils.FACILITY_SOURCE_LANDFILL_CODE.contentEquals(typeCode.getCode()));
                }).collect(Collectors.toList());

        List<EmissionBulkEntryHolderDto> result = emissionMapper.periodToEmissionBulkEntryDtoList(entities);

        if (!entities.isEmpty()) {
            // find the last year reported
            Optional<EmissionsReport> lastReport = emissionsReportRepo.findFirstByMasterFacilityRecordIdAndYearLessThanOrderByYearDesc(
                    entities.get(0).getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEmissionsReport().getMasterFacilityRecord().getId(),
                    entities.get(0).getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEmissionsReport().getYear());

            if (lastReport.isPresent()) {
                result.forEach(dto -> {
                    dto.getEmissions().forEach(e -> {
                        List<Emission> oldEntities = emissionRepo.retrieveMatchingForYear(e.getPollutant().getPollutantCode(),
                                dto.getReportingPeriodTypeCode().getCode(),
                                dto.getEmissionsProcessIdentifier(),
                                dto.getUnitIdentifier(), 
                                lastReport.get().getEisProgramId(), 
                                lastReport.get().getYear());
                        // Add previous emissions values if they exist
                        if (!oldEntities.isEmpty()) {
                            e.setPreviousTotalEmissions(oldEntities.get(0).getTotalEmissions());
                            e.setPreviousEmissionsUomCode(oldEntities.get(0).getEmissionsUomCode().getCode());
                        }
                    });
                });
            }
        }

        return result;
    }

    /**
     * Update the total emissions for the provided emissions and recalculate all emissions for the facility
     * @param facilitySiteId
     * @param dtos
     * @return
     */
    public List<EmissionBulkEntryHolderDto> bulkUpdate(Long facilitySiteId, List<EmissionDto> dtos) {

        List<ReportingPeriod> entities = periodRepo.findByFacilitySiteId(facilitySiteId).stream()
                .filter(rp -> !"PS".equals(rp.getEmissionsProcess().getOperatingStatusCode().getCode()))
                .collect(Collectors.toList());

        Map<Long, BigDecimal> updateMap = dtos.stream().collect(Collectors.toMap(EmissionDto::getId, EmissionDto::getTotalEmissions));

        if (!entities.isEmpty()) {
            // find the last year reported
            EmissionsReport lastReport = emissionsReportRepo.findFirstByMasterFacilityRecordIdAndYearLessThanOrderByYearDesc(
                    entities.get(0).getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEmissionsReport().getMasterFacilityRecord().getId(),
                    entities.get(0).getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEmissionsReport().getYear()).orElse(null);

            List<EmissionBulkEntryHolderDto> result = entities.stream().map(rp -> {

                EmissionBulkEntryHolderDto rpDto = emissionMapper.periodToEmissionBulkEntryDto(rp);

                List<EmissionBulkEntryDto> emissions = rp.getEmissions().stream().map(emission -> {

                    String calculationFailureMessage = null;

                    // update total emissions when manual entry is enabled
                    if (Boolean.TRUE.equals(emission.getTotalManualEntry()) 
                            || Boolean.TRUE.equals(emission.getEmissionsCalcMethodCode().getTotalDirectEntry())) {

                        if (updateMap.containsKey(emission.getId())) {

                            emission.setTotalEmissions(updateMap.get(emission.getId()));
                            update(emission);
                        }
                    } else {

                        // recalculate calculated emissions and record if there is an issue
                        try {
                            calculateTotalEmissions(emission, rp);
                            update(emission);
                        } catch (ApplicationException e) {

                            calculationFailureMessage = e.getMessage();
                        }
                    }

                    EmissionBulkEntryDto eDto = emissionMapper.toBulkDto(emission);
                    eDto.setCalculationFailed(calculationFailureMessage != null);
                    eDto.setCalculationFailureMessage(calculationFailureMessage);

                    // find previous reporting period
                    if (lastReport != null) {
                        List<Emission> oldEntities = emissionRepo.retrieveMatchingForYear(eDto.getPollutant().getPollutantCode(),
                                rpDto.getReportingPeriodTypeCode().getCode(),
                                rpDto.getEmissionsProcessIdentifier(),
                                rpDto.getUnitIdentifier(), 
                                lastReport.getEisProgramId(), 
                                lastReport.getYear());
                        if (!oldEntities.isEmpty()) {
                            eDto.setPreviousTotalEmissions(oldEntities.get(0).getTotalEmissions());
                            eDto.setPreviousEmissionsUomCode(oldEntities.get(0).getEmissionsUomCode().getCode());
                        }
                    }

                    return eDto;
                }).collect(Collectors.toList());

                rpDto.setEmissions(emissions);

                return rpDto;
            }).collect(Collectors.toList());

            return result;
            
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Recalculate the total emissions in tons for all emissions in a report without changing any other values
     * @param reportId
     * @return
     */
    public List<EmissionDto> recalculateEmissionTons(Long reportId) {

        List<Emission> emissions = this.emissionRepo.findAllByReportId(reportId);
        List<Emission> result = new ArrayList<>();

        emissions.forEach(e -> {
            BigDecimal calculatedValue = calculateEmissionTons(e);
            if (!Objects.equals(calculatedValue, e.getCalculatedEmissionsTons())) {
                e.setCalculatedEmissionsTons(calculatedValue);
                result.add(this.emissionRepo.save(e));
            }
        });

        return emissionMapper.toDtoList(result);
    }

    /**
     * Calculate total emissions for an emission. Also calculates emission factor if it uses a formula
     * This method should be used when the Reporting Period in the database should be used for calculations 
     * and you have an EmissionDto, probably with values that differ from the ones in the database.
     * @param dto
     * @return
     */
    public EmissionDto calculateTotalEmissions(EmissionDto dto) {

        ReportingPeriod rp = periodRepo.findById(dto.getReportingPeriodId()).orElse(null);

        UnitMeasureCode totalEmissionUom = uomRepo.findById(dto.getEmissionsUomCode().getCode()).orElse(null);
        UnitMeasureCode efNumerator = uomRepo.findById(dto.getEmissionsNumeratorUom().getCode()).orElse(null);
        UnitMeasureCode efDenom = uomRepo.findById(dto.getEmissionsDenominatorUom().getCode()).orElse(null);

        Emission emission = emissionMapper.fromDto(dto);

        emission.setEmissionsUomCode(totalEmissionUom);
        emission.setEmissionsNumeratorUom(efNumerator);
        emission.setEmissionsDenominatorUom(efDenom);

        EmissionDto result = emissionMapper.toDto(calculateTotalEmissions(emission, rp));

        return result;
    }

    /**
     * Calculate total emissions for an emission and reporting period. Also calculates emission factor if it uses a formula
     * This method should be used when you need to specify a Reporting Period with a different throughput or UoM than the 
     * one in the database. 
     * @param emission
     * @param rp
     * @return
     */
    public Emission calculateTotalEmissions(Emission emission, ReportingPeriod rp) {

        UnitMeasureCode totalEmissionUom = emission.getEmissionsUomCode();
        UnitMeasureCode efNumerator = emission.getEmissionsNumeratorUom();
        UnitMeasureCode efDenom = emission.getEmissionsDenominatorUom();

        if (rp.getCalculationParameterUom() == null) {
            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT, "Reporting Period Calculation Unit of Measure must be set.");
        }
        if (totalEmissionUom == null) {
            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT, "Total Emissions Unit of Measure must be set.");
        }
        if (efNumerator == null) {
            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT, "Emission Factor Numerator Unit of Measure must be set.");
        }
        if (efDenom == null) {
            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT, "Emission Factor Denominator Unit of Measure must be set.");
        }

        // if throughput uom type does not match ef denominator uom type
        if (!rp.getCalculationParameterUom().getUnitType().equals(efDenom.getUnitType())) {
        	
        	// if throughput uom is fuel use uom or heat content uom check if there is conversion factor 
        	if (Boolean.TRUE.equals(rp.getCalculationParameterUom().getFuelUseUom()) || Boolean.TRUE.equals(rp.getCalculationParameterUom().getHeatContentUom())) {
        		
        		// get conversion factor based on throughput material
        		EnergyConversionFactor cf = new EnergyConversionFactor();
        		cf = cfRepo.findByCalculationMaterialCode(rp.getCalculationMaterialCode().getCode());
        		
        		// Set conversion factor if cf numerator uom == ef denominator uom, and cf denominator uom == throughput uom
        		// or if cf denominator uom == ef denominator uom, and cf numerator uom == throughput uom
        		if (cf != null && ((cf.getEmissionsNumeratorUom().getUnitType().equals(efDenom.getUnitType())
	        				&& cf.getEmissionsDenominatorUom().getUnitType().equals(rp.getCalculationParameterUom().getUnitType()))
	        				|| (cf.getEmissionsDenominatorUom().getUnitType().equals(efDenom.getUnitType())
	        				&& cf.getEmissionsNumeratorUom().getUnitType().equals(rp.getCalculationParameterUom().getUnitType())))) {
        					emission.setEnergyConversionFactor(cf);
	        		} else {
	        			throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT,
	                            String.format("Reporting Period Calculation Unit of Measure cannot be converted into Emission Factor Denominator Unit of Measure."));
	        		}
        		
        	} else {
	            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT,
	                    String.format("Reporting Period Calculation Unit of Measure %s cannot be converted into Emission Factor Denominator Unit of Measure %s.",
	                            rp.getCalculationParameterUom().getDescription(), efDenom.getDescription()));
        	}
        }

        if (!totalEmissionUom.getUnitType().equals(efNumerator.getUnitType())) {
            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT,
                    String.format("Emission Factor Numerator Unit of Measure %s cannot be converted into Total Emissions Unit of Measure %s.",
                            efNumerator.getDescription(), totalEmissionUom.getDescription()));
        }

        if (emission.getFormulaIndicator()) {
            List<EmissionFormulaVariable> variables = emission.getVariables();

            BigDecimal ef = CalculationUtils.calculateEmissionFormula(emission.getEmissionsFactorFormula(), variables);
            emission.setEmissionsFactor(ef);
        }

        // check if the year is divisible by 4 which would make it a leap year
        boolean leapYear = rp.getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEmissionsReport().getYear() % 4 == 0;
        BigDecimal totalEmissions = rp.getCalculationParameterValue();
		
        // apply energy conversion
        if (emission.getEnergyConversionFactor() != null) {
        	
        	// if cf numerator uom type == ef denominator uom type , and cf denominator uom type == throughput uom type 
        	if (emission.getEnergyConversionFactor().getEmissionsNumeratorUom().getUnitType().equals(efDenom.getUnitType())
        			&& emission.getEnergyConversionFactor().getEmissionsDenominatorUom().getUnitType().equals(rp.getCalculationParameterUom().getUnitType())) {
        		
	        	totalEmissions = totalEmissions.divide(emission.getEnergyConversionFactor().getConversionFactor(), MathContext.DECIMAL128);
	        	
	        	// convert units for throughput to match cf denominator
	        	if (!emission.getEnergyConversionFactor().getEmissionsDenominatorUom().getCode().equals(rp.getCalculationParameterUom().getCode())) {
		        	totalEmissions = CalculationUtils.convertUnits(rp.getCalculationParameterUom().getCalculationVariable(), emission.getEnergyConversionFactor().getEmissionsDenominatorUom().getCalculationVariable(), leapYear).multiply(totalEmissions);
	        	}
		        
		        // convert units for cf numerator to match ef denominator
		        if (emission.getEnergyConversionFactor().getEmissionsNumeratorUom().getUnitType().equals(efDenom.getUnitType())
		        		&& !efDenom.getCode().equals(emission.getEnergyConversionFactor().getEmissionsNumeratorUom().getCode())) {
		            totalEmissions = CalculationUtils.convertUnits(emission.getEnergyConversionFactor().getEmissionsNumeratorUom().getCalculationVariable(), efDenom.getCalculationVariable(), leapYear).multiply(totalEmissions);
		        }
        	}
	        
        	// if cf denominator uom type == ef denominator uom type, and cf numerator uom type == throughput uom type
        	if (emission.getEnergyConversionFactor().getEmissionsDenominatorUom().getUnitType().equals(efDenom.getUnitType())
        			&& emission.getEnergyConversionFactor().getEmissionsNumeratorUom().getUnitType().equals(rp.getCalculationParameterUom().getUnitType())) {
        		
        		totalEmissions = emission.getEnergyConversionFactor().getConversionFactor().multiply(totalEmissions);
        		
		        // convert units for throughput to match cf numerator
		        if (!emission.getEnergyConversionFactor().getEmissionsNumeratorUom().getCode().equals(rp.getCalculationParameterUom().getCode())) {
		        	totalEmissions = CalculationUtils.convertUnits(rp.getCalculationParameterUom().getCalculationVariable(), emission.getEnergyConversionFactor().getEmissionsNumeratorUom().getCalculationVariable(), leapYear).multiply(totalEmissions);
				}
		        
		        // convert units for cf denominator to match ef denominator
		        if (!efDenom.getCode().equals(emission.getEnergyConversionFactor().getEmissionsDenominatorUom().getCode())) {
		            totalEmissions = CalculationUtils.convertUnits(emission.getEnergyConversionFactor().getEmissionsDenominatorUom().getCalculationVariable(), efDenom.getCalculationVariable(), leapYear).multiply(totalEmissions);
		        }
        	}
        }
		
        // apply emission factor
        totalEmissions = emission.getEmissionsFactor().multiply(totalEmissions);

        if (emission.getEnergyConversionFactor() == null) {
	        // convert units for ef denominator and throughput
	        if (rp.getCalculationParameterUom() != null 
	                && !rp.getCalculationParameterUom().getCode().equals(efDenom.getCode())) {
	        	totalEmissions = CalculationUtils.convertUnits(rp.getCalculationParameterUom().getCalculationVariable(), efDenom.getCalculationVariable(), leapYear).multiply(totalEmissions);
	        }
        }
	
        // convert units for numerator and total emissions
        if (!totalEmissionUom.getCode().equals(efNumerator.getCode())) {
            totalEmissions = CalculationUtils.convertUnits(efNumerator.getCalculationVariable(), totalEmissionUom.getCalculationVariable(), leapYear).multiply(totalEmissions);
        }

        if (emission.getOverallControlPercent() != null) {
            BigDecimal controlRate = new BigDecimal("100").subtract(emission.getOverallControlPercent()).divide(new BigDecimal("100"));
            totalEmissions = totalEmissions.multiply(controlRate);
        }

        totalEmissions = CalculationUtils.setSignificantFigures(totalEmissions, CalculationUtils.EMISSIONS_PRECISION);

        emission.setTotalEmissions(totalEmissions);

        return emission;
    }

    /**
     * Find Emission by TRI Facility ID and CAS Number.
     * This method is the second version of the interface to TRIMEweb so that TRI users can
     * see what emissions have been reported to the Common Emissions Form for the current
     * facility and chemical that they are working on. This version takes a TRIFID and looks 
     * up the EIS ID in CAERS and then finds any existing emissions.
     *
     * @param frsFacilityId
     * @param pollutantCasId
     * @return
     */
    public EmissionsByFacilityAndCASDto findEmissionsByTrifidAndCAS(String trifid, String pollutantCasId) {
        logger.debug("findEmissionsByTrifidAndCAS - Entering");

        EmissionsByFacilityAndCASDto emissionsByFacilityDto = new EmissionsByFacilityAndCASDto();
        Short latestReportYear = null;

        //Get The Corresponding EIS for the TRIFID that was provided
        List<EisTriXref> xrefs = eisTriXrefRepo.findByTrifid(trifid);
        

        if (xrefs.isEmpty()) {
            logger.debug("findEmissionsByTrifidAndCAS - No corresponding EIS ID found for TRIFID - returning empty");
            String noEISFacilityMessage = "No corresponding EIS ID found for TRIFID = ".concat(trifid);
            emissionsByFacilityDto.setMessage(noEISFacilityMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.NO_EIS_FACILITIES.toString());
            return emissionsByFacilityDto;	
        }
        
        //we should only ever get one eis ID back. If we get multiple back the we don't know which one to lookup. 
        //Respond to the calling service with the appropriate message.
        else if (xrefs.size() > 1) {
            logger.debug("findEmissionsByTrifidAndCAS - Found multiple EIS IDs for the given TRIFID - "
            		+ "returning empty data to calling service");
            String multipleFacilitiesMessage = "Found multiple EIS IDs for the provided TRIFID = ".concat(trifid).
            		concat(". Unable to retrieve data.");
            emissionsByFacilityDto.setMessage(multipleFacilitiesMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.MULTIPLE_FACILITIES.toString());
            return emissionsByFacilityDto;
        }
        
        //First find the most recent report for the the given facility so we can check THAT report for emissions
        String eisProgramId = xrefs.get(0).getEisId();
        List<EmissionsReport> emissionsReports = emissionsReportRepo.findByEisProgramId(eisProgramId, Sort.by(Sort.Direction.DESC, "year"));
        if (!emissionsReports.isEmpty()) {
            latestReportYear = emissionsReports.get(0).getYear();
        } else {
            logger.debug("findEmissionsByTrifidAndCAS - No Emissions Reports for the given facility - returning empty");
            String noReportsMessage = "No available reports found for TRIFID ID = ".concat(trifid);
            emissionsByFacilityDto.setMessage(noReportsMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.NO_EMISSIONS_REPORT.toString());
            return emissionsByFacilityDto;
        }

        List<EmissionsByFacilityAndCAS> emissionsByFacilityAndCAS =
                emissionsByFacilityAndCASRepo.findByTrifidAndPollutantCasIdAndYear(trifid, pollutantCasId, latestReportYear);

        //if there are any emissions that match the facility and CAS Id for the most recent year,
        //then loop through them and add them to the point / nonPoint totals
        if (emissionsByFacilityAndCAS.isEmpty()) {
            logger.debug("findEmissionsByTrifidAndCAS - No emissions for the given CAS number were reported on the most recent report for the facility");
            String noEmissionsMessage = "There were no emissions reported for the CAS number ".concat(pollutantCasId).
                    concat(" on the most recent emissions report for TRIFID = ").concat(trifid);
            emissionsByFacilityDto.setMessage(noEmissionsMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.NO_EMISSIONS_REPORTED_FOR_CAS.toString());
            return emissionsByFacilityDto;
        } else {
            logger.debug("findEmissionsByTrifidAndCAS - found {} emission records", emissionsByFacilityAndCAS.size());
            //populate the common parts of the DTO object by mapping the first result.
            //since we're matching on facility and CAS, all of these fields should be the same for each instance of the list
            emissionsByFacilityDto = emissionsByFacilityAndCASMapper.toDto(emissionsByFacilityAndCAS.get(0));
            
            //query the report history table to find the most recent SUBMITTED date for the report we're returning data for
            emissionsByFacilityDto.setCertificationDate(historyRepo.retrieveMaxSubmissionDateByReportId(emissionsByFacilityDto.getReportId()).orElse(null));
            
            BigDecimal stackEmissions = new BigDecimal(0).setScale(TWO_DECIMAL_POINTS, RoundingMode.HALF_UP);
            BigDecimal fugitiveEmissions = new BigDecimal(0).setScale(TWO_DECIMAL_POINTS, RoundingMode.HALF_UP);

            for (EmissionsByFacilityAndCAS currentEmissions :emissionsByFacilityAndCAS) {
                //if the release point type is fugitive - add it to the "fugitive" emissions. Otherwise add the amount
                //to the stack release emissions
                if (StringUtils.equalsIgnoreCase(POINT_EMISSION_RELEASE_POINT, currentEmissions.getReleasePointType())) {
                    stackEmissions = stackEmissions.add(currentEmissions.getApportionedEmissions());
                } else {
                    fugitiveEmissions = fugitiveEmissions.add(currentEmissions.getApportionedEmissions());
                }
            }

            //Round both of the values to the nearest hundredth
            emissionsByFacilityDto.setStackEmissions(stackEmissions);
            emissionsByFacilityDto.setFugitiveEmissions(fugitiveEmissions);
            String totalEmissionsMessage = "Found %s stack emissions and %s fugitive emissions for CAS number = %s on"
                    + " the most recent emissions report for TRIFID = %s";
            totalEmissionsMessage = String.format(totalEmissionsMessage, stackEmissions.toPlainString(), fugitiveEmissions.toPlainString(), pollutantCasId, trifid);
            emissionsByFacilityDto.setMessage(totalEmissionsMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.EMISSIONS_FOUND.toString());
        }

        logger.debug("findEmissionsByTrifidAndCAS - Exiting");
        return emissionsByFacilityDto;    	
    }
    
    private BigDecimal calculateEmissionTons(Emission emission) {
        try {
            BigDecimal calculatedEmissionsTons = CalculationUtils.convertMassUnits(emission.getTotalEmissions(), 
                    MassUomConversion.valueOf(emission.getEmissionsUomCode().getCode()), 
                    MassUomConversion.TON);
            return calculatedEmissionsTons;
        } catch (IllegalArgumentException ex) {
            logger.debug("Could not perform emission conversion. {}", ex.getLocalizedMessage());
            return null;
        }
    }


    /**
     * Retrieve a list of emissions for the given program system code and emissions report year
     * @param programSystemCode
     * @param emissionsReportYear
     * @return
     */  
    public List<EmissionBulkUploadDto> retrieveEmissions(String programSystemCode, Short emissionsReportYear) {
    	List<Emission> emissions = emissionRepo.findByPscAndEmissionsReportYear(programSystemCode, emissionsReportYear);
    	return bulkUploadMapper.emissionToDtoList(emissions);
    }


    /**
     * Retrieve a list of emission formula variables for the given program system code and emissions report year
     * @param programSystemCode
     * @param emissionsReportYear
     * @return
     */ 
    public List<EmissionFormulaVariableBulkUploadDto> retrieveEmissionFormulaVariables(String programSystemCode, Short emissionsReportYear) {
    	List<EmissionFormulaVariable> variables = variablesRepo.findByPscAndEmissionsReportYear(programSystemCode, emissionsReportYear);
    	return bulkUploadMapper.emissionFormulaVariableToDtoList(variables);
    }
    
    public Emission updateEmissionsFactorDescription (Emission emission, EmissionsProcess process) {
    	
    	if (Boolean.TRUE.equals(emission.getFormulaIndicator())) {

            // set emission factor text using ef repo to get the description
            EmissionFactor ef = efRepo.findBySccCodePollutantEmissionFactorFormulaControlIndicator(process.getSccCode(),
																			            		   emission.getPollutant().getPollutantCode(),
																			            		   emission.getEmissionsFactorFormula(),
																			            		   emission.getEmissionsCalcMethodCode().getControlIndicator());
            
            if (ef != null) {
            	emission.setEmissionsFactorText(ef.getDescription().substring(0,96).concat("..."));
                // TODO: add logging when the time comes
            }
        } else {
            // set emission factor text using ef repo to get the description
            EmissionFactor ef = efRepo.findBySccCodePollutantEmissionFactorControlIndicator(process.getSccCode(),
																		            		emission.getPollutant().getPollutantCode(),
																		            		emission.getEmissionsFactor(),
																		            		emission.getEmissionsCalcMethodCode().getControlIndicator());
            
            if (ef != null) {
            	emission.setEmissionsFactorText(ef.getDescription().substring(0,96).concat("..."));
                // TODO: add logging when the time comes
            }
        }
    	
    	return emission;
    }

}
