package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsByFacilityAndCAS;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.EmissionsByFacilityAndCASRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.EmissionService;
import gov.epa.cef.web.service.dto.EmissionDto;
import gov.epa.cef.web.service.dto.EmissionsByFacilityAndCASDto;
import gov.epa.cef.web.service.mapper.EmissionMapper;
import gov.epa.cef.web.service.mapper.EmissionsByFacilityAndCASMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

        EmissionDto result = emissionMapper.toDto(emissionRepo.save(emission));
        return result;
    }

    /**
     * Update an existing emission from a DTO
     */
    public EmissionDto update(EmissionDto dto) {

        Emission emission = emissionRepo.findById(dto.getId()).orElse(null);
        emissionMapper.updateFromDto(dto, emission);

        EmissionDto result = emissionMapper.toDto(emissionRepo.save(emission));
        return result;
    }

    /**
     * Delete an Emission for a given id
     * @param id
     */
    public void delete(Long id) {
        emissionRepo.deleteById(id);
    }

    /**
     * Find Emissions by Facility and CAS Number.
     * This method is primarily intended to provide the interface to TRIMEweb so that TRI users can
     * see what emissions have been reported to the Common Emissions Form for the current
     * facility and chemical that they are working on.
     *
     * @param frsFacilityId
     * @param casNumber
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

}
