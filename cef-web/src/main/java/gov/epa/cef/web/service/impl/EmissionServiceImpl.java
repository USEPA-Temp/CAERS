package gov.epa.cef.web.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.EmissionsByFacilityAndCAS;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.repository.EmissionsByFacilityAndCASRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.EmissionService;
import gov.epa.cef.web.service.dto.EmissionsByFacilityAndCASDto;
import gov.epa.cef.web.service.mapper.EmissionsByFacilityAndCASMapper;

@Service
public class EmissionServiceImpl implements EmissionService {
    
    Logger logger = LoggerFactory.getLogger(EmissionServiceImpl.class);
    
    @Autowired
    private EmissionsByFacilityAndCASRepository emissionsByFacilityAndCASRepo;
    
    @Autowired
    private EmissionsReportRepository emissionsReportRepo;
    
    @Autowired
    EmissionsByFacilityAndCASMapper emissionsByFacilityAndCASMapper;
    
    private static final String pointEmissionReleasePoint = "point";
    
    private static enum RETURN_CODE {NO_EMISSIONS_REPORT, NO_EMISSIONS_REPORTED_FOR_CAS, EMISSIONS_FOUND};
    
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
        logger.debug("findEmissionsByFacilityAndCAS - Entering");
        
        EmissionsByFacilityAndCASDto emissionsByFacilityDto = new EmissionsByFacilityAndCASDto();
        Short latestReportYear = null;
        
        //First find the most recent report for the the given facility so we can check THAT report for emissions
        List<EmissionsReport> emissionsReports = emissionsReportRepo.findByFrsFacilityId(frsFacilityId, Sort.by(Sort.Direction.DESC, "year"));
        if (!emissionsReports.isEmpty()) {
            latestReportYear = emissionsReports.get(0).getYear();
        }
        else
        {
            logger.debug("findEmissionsByFacilityAndCAS - No Emissions Reports for the given facility - returning empty");
            String noReportsMessage = new String("No emission reports found for FRS Facility ID = ").concat(frsFacilityId);
            emissionsByFacilityDto.setMessage(noReportsMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.NO_EMISSIONS_REPORT.toString());
            return emissionsByFacilityDto;
        }
        
        List<EmissionsByFacilityAndCAS> emissionsByFacilityAndCAS = 
                emissionsByFacilityAndCASRepo.findByFrsFacilityIdAndPollutantCasIdAndYear(frsFacilityId, pollutantCasId, latestReportYear);
        
        //if there are any emissions that match the facility and CAS Id for the most recent year, 
        //then loop through them and add them to the point / nonPoint totals
        if (emissionsByFacilityAndCAS.isEmpty()){
            logger.debug("findEmissionsByFacilityAndCAS - No emissions for the given CAS number were reported on the most recent report for the facility");
            String noEmissionsMessage = new String("There were no emissions reported for the CAS number ").concat(pollutantCasId).
                    concat(" on the most recent emissions report for FRS Facility ID = ").concat(frsFacilityId);
            emissionsByFacilityDto.setMessage(noEmissionsMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.NO_EMISSIONS_REPORTED_FOR_CAS.toString());
            return emissionsByFacilityDto;
        }
        else
        {
            logger.debug("findEmissionsByFacilityAndCAS - found " + emissionsByFacilityAndCAS.size() + " emission records");
            //populate the common parts of the DTO object by mapping the first result.
            //since we're matching on facility and CAS, all of these fields should be the same for each instance of the list
            emissionsByFacilityDto = emissionsByFacilityAndCASMapper.toDto(emissionsByFacilityAndCAS.get(0));
            
            BigDecimal pointEmissions = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            BigDecimal nonPointEmissions = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
               
            for (EmissionsByFacilityAndCAS currentEmissions :emissionsByFacilityAndCAS)
            {             
                //if the release point type is fugitive - add it to the "non-point" emissions. Otherwise add the amount
                //to the point release emissions
                if (StringUtils.equalsIgnoreCase(pointEmissionReleasePoint, currentEmissions.getReleasePointType()))
                { 
                    pointEmissions = pointEmissions.add(currentEmissions.getApportionedEmissions());
                }
                else {
                    nonPointEmissions = nonPointEmissions.add(currentEmissions.getApportionedEmissions());
                }
            }
            
            //Round both of the values to the nearest hundredth
            emissionsByFacilityDto.setPointEmissions(pointEmissions);
            emissionsByFacilityDto.setNonPointEmissions(nonPointEmissions);
            String totalEmissionsMessage = new String("Found ").concat(pointEmissions.toPlainString()).
                    concat(" point emissions and ").concat(nonPointEmissions.toPlainString()).
                    concat(" non-point emissions for CAS number = ").concat(pollutantCasId).
                    concat(" on the most recent emissions report for FRS Facility ID = ").concat(frsFacilityId);
            emissionsByFacilityDto.setMessage(totalEmissionsMessage);
            emissionsByFacilityDto.setCode(RETURN_CODE.EMISSIONS_FOUND.toString());
        }

        logger.debug("findEmissionsByFacilityAndCAS - Exiting");
        return emissionsByFacilityDto;
    }

}
