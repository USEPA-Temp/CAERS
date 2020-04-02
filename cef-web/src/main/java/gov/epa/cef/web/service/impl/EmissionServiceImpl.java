package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFormulaVariable;
import gov.epa.cef.web.domain.EmissionsByFacilityAndCAS;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.EmissionsByFacilityAndCASRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.ReportHistoryRepository;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.service.EmissionService;
import gov.epa.cef.web.service.dto.EmissionDto;
import gov.epa.cef.web.service.dto.EmissionsByFacilityAndCASDto;
import gov.epa.cef.web.service.mapper.EmissionMapper;
import gov.epa.cef.web.service.mapper.EmissionsByFacilityAndCASMapper;
import gov.epa.cef.web.util.CalculationUtils;
import gov.epa.cef.web.util.MassUomConversion;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmissionServiceImpl implements EmissionService {

    Logger LOGGER = LoggerFactory.getLogger(EmissionServiceImpl.class);

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
    private EmissionsReportStatusServiceImpl reportStatusService;

    @Autowired
    private EmissionMapper emissionMapper;

    @Autowired
    private EmissionsByFacilityAndCASMapper emissionsByFacilityAndCASMapper;

    private static final String POINT_EMISSION_RELEASE_POINT = "stack";
    private static final int TWO_DECIMAL_POINTS = 2;

    private enum RETURN_CODE {NO_EMISSIONS_REPORT, NO_EMISSIONS_REPORTED_FOR_CAS, EMISSIONS_FOUND}

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

        emission.setCalculatedEmissionsTons(calculateEmissionTons(emission));

        EmissionDto result = emissionMapper.toDto(emissionRepo.save(emission));
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), EmissionRepository.class);
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

        if (!rp.getCalculationParameterUom().getUnitType().equals(efDenom.getUnitType())) {
            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT,
                    String.format("Reporting Period Calculation Unit of Measure %s cannot be converted into Emission Factor Denominator Unit of Measure %s.",
                            rp.getCalculationParameterUom().getDescription(), efDenom.getDescription()));
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

        BigDecimal totalEmissions = emission.getEmissionsFactor().multiply(rp.getCalculationParameterValue());

        // convert units for denominator and throughput
        if (efDenom != null && rp.getCalculationParameterUom() != null 
                && !rp.getCalculationParameterUom().getCode().equals(efDenom.getCode())) {
            totalEmissions = CalculationUtils.convertUnits(rp.getCalculationParameterUom().getCalculationVariable(), efDenom.getCalculationVariable(), leapYear).multiply(totalEmissions);
        }

        // convert units for numerator and total emissions
        if (efNumerator != null && totalEmissionUom != null && !totalEmissionUom.getCode().equals(efNumerator.getCode())) {
            totalEmissions = CalculationUtils.convertUnits(efNumerator.getCalculationVariable(), totalEmissionUom.getCalculationVariable(), leapYear).multiply(totalEmissions);
        }

        if (emission.getOverallControlPercent() != null) {
            BigDecimal controlRate = new BigDecimal("100").subtract(emission.getOverallControlPercent()).divide(new BigDecimal("100"));
            totalEmissions = totalEmissions.multiply(controlRate);
        }

        emission.setTotalEmissions(totalEmissions);

        return emission;
    }

    /**
     * Find Emissions by Facility and CAS Number.
     * This method is primarily intended to provide the interface to TRIMEweb so that TRI users can
     * see what emissions have been reported to the Common Emissions Form for the current
     * facility and chemical that they are working on.
     *
     * @param frsFacilityId
     * @param pollutantCasId
     * @return
     */
    public EmissionsByFacilityAndCASDto findEmissionsByFacilityAndCAS(String frsFacilityId, String pollutantCasId) {
        LOGGER.debug("findEmissionsByFacilityAndCAS - Entering");

        EmissionsByFacilityAndCASDto emissionsByFacilityDto = new EmissionsByFacilityAndCASDto();
        Short latestReportYear = null;

        //First find the most recent report for the the given facility so we can check THAT report for emissions
        List<EmissionsReport> emissionsReports = emissionsReportRepo.findByFrsFacilityId(frsFacilityId, Sort.by(Sort.Direction.DESC, "year"));
        if (!emissionsReports.isEmpty()) {
            latestReportYear = emissionsReports.get(0).getYear();
        } else {
            LOGGER.debug("findEmissionsByFacilityAndCAS - No Emissions Reports for the given facility - returning empty");
            String noReportsMessage = new String("No emission reports found for FRS Facility ID = ").concat(frsFacilityId);
            emissionsByFacilityDto.setMessage(noReportsMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.NO_EMISSIONS_REPORT.toString());
            return emissionsByFacilityDto;
        }

        List<EmissionsByFacilityAndCAS> emissionsByFacilityAndCAS =
                emissionsByFacilityAndCASRepo.findByFrsFacilityIdAndPollutantCasIdAndYear(frsFacilityId, pollutantCasId, latestReportYear);

        //if there are any emissions that match the facility and CAS Id for the most recent year,
        //then loop through them and add them to the point / nonPoint totals
        if (emissionsByFacilityAndCAS.isEmpty()) {
            LOGGER.debug("findEmissionsByFacilityAndCAS - No emissions for the given CAS number were reported on the most recent report for the facility");
            String noEmissionsMessage = new String("There were no emissions reported for the CAS number ").concat(pollutantCasId).
                    concat(" on the most recent emissions report for FRS Facility ID = ").concat(frsFacilityId);
            emissionsByFacilityDto.setMessage(noEmissionsMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.NO_EMISSIONS_REPORTED_FOR_CAS.toString());
            return emissionsByFacilityDto;
        } else {
            LOGGER.debug("findEmissionsByFacilityAndCAS - found " + emissionsByFacilityAndCAS.size() + " emission records");
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
                    + " the most recent emissions report for FRS Facility ID = %s";
            totalEmissionsMessage = String.format(totalEmissionsMessage, stackEmissions.toPlainString(), fugitiveEmissions.toPlainString(), pollutantCasId, frsFacilityId);
            emissionsByFacilityDto.setMessage(totalEmissionsMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.EMISSIONS_FOUND.toString());
        }

        LOGGER.debug("findEmissionsByFacilityAndCAS - Exiting");
        return emissionsByFacilityDto;
    }
    
    private BigDecimal calculateEmissionTons(Emission emission) {
        try {
            BigDecimal calculatedEmissionsTons = CalculationUtils.convertMassUnits(emission.getTotalEmissions(), 
                    MassUomConversion.valueOf(emission.getEmissionsUomCode().getCode()), 
                    MassUomConversion.TON);
            return calculatedEmissionsTons;
        } catch (IllegalArgumentException ex) {
            LOGGER.debug("Could not perform emission conversion. " + ex.getLocalizedMessage());
            return null;
        }
    }

}
