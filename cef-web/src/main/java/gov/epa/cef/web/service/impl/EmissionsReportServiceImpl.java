package gov.epa.cef.web.service.impl;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.activation.DataHandler;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gov.epa.cef.web.client.soap.DocumentDataSource;
import gov.epa.cef.web.client.soap.SignatureServiceClient;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilityCategoryCode;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsUnitBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointBulkUploadDto;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.OperatingStatusCodeRepository;
import gov.epa.cef.web.repository.FacilityCategoryCodeRepository;
import gov.epa.cef.web.repository.FacilitySourceTypeCodeRepository;
import gov.epa.cef.web.repository.ReleasePointTypeCodeRepository;
import gov.epa.cef.web.repository.UnitTypeCodeRepository;
import gov.epa.cef.web.repository.ProgramSystemCodeRepository;
import gov.epa.cef.web.repository.TribalCodeRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.repository.NaicsCodeRepository;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import gov.epa.cef.web.util.SLTConfigHelper;
import net.exchangenetwork.wsdl.register.sign._1.SignatureDocumentFormatType;
import net.exchangenetwork.wsdl.register.sign._1.SignatureDocumentType;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmissionsReportServiceImpl implements EmissionsReportService {
    
    Logger LOGGER = LoggerFactory.getLogger(EmissionsReportServiceImpl.class);

    // TODO: Remove hard coded value
    // https://alm.cgifederal.com/projects/browse/CEF-319
    private static final String __HARD_CODED_AGENCY_CODE__ = "GA";

    @Autowired
    private EmissionsReportRepository erRepo;

    @Autowired
    private OperatingStatusCodeRepository operatingStatusRepo;

    @Autowired
    private FacilityCategoryCodeRepository facilityCategoryRepo;

    @Autowired
    private FacilitySourceTypeCodeRepository facilitySourceTypeRepo;

    @Autowired
    private ReleasePointTypeCodeRepository releasePointTypeRepo;

    @Autowired
    private UnitTypeCodeRepository unitTypeRepo;

    @Autowired
    private ProgramSystemCodeRepository programSystemCodeRepo;

    @Autowired
    private TribalCodeRepository tribalCodeRepo;

    @Autowired
    private UnitMeasureCodeRepository unitMeasureCodeRepo;

    @Autowired
    private NaicsCodeRepository naicsCodeRepo;

    @Autowired
    private EmissionsReportMapper emissionsReportMapper;

    @Autowired
    private CefConfig cefConfig;
    
    @Autowired
    private SLTConfigHelper sltConfigHelper;

    @Autowired
    private SignatureServiceClient signatureServiceClient;

    @Autowired
    private CersXmlService cersXmlService;

    @Autowired
    private FacilitySiteService facilitySiteService;
    
    @Autowired
    private NotificationService notificationService;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findByFacilityId(java.lang.String)
     */
    @Override
    public List<EmissionsReportDto> findByFacilityEisProgramId(String facilityEisProgramId) {
        return findByFacilityEisProgramId(facilityEisProgramId, false);
    }


    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findByFacilityId(java.lang.String)
     */
    @Override
    public List<EmissionsReportDto> findByFacilityEisProgramId(String facilityEisProgramId, boolean addReportForCurrentYear) {
        List<EmissionsReport> emissionReports= erRepo.findByEisProgramId(facilityEisProgramId);
        if (addReportForCurrentYear) {
        	addCurrentYear(emissionReports, facilityEisProgramId);
        }

        List<EmissionsReportDto> dtoList = emissionsReportMapper.toDtoList(emissionReports);
        return dtoList;
    }


	/* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findById(java.lang.Long)
     */
    @Override
    public EmissionsReportDto findById(Long id) {

        return erRepo.findById(id)
            .map(report -> emissionsReportMapper.toDto(report))
            .orElse(null);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findMostRecentByFacility(java.lang.String)
     */
    @Override
    public EmissionsReportDto findMostRecentByFacilityEisProgramId(String facilityEisProgramId) {

        return findMostRecentEmissionsReport(facilityEisProgramId)
            .map(emissionsReport -> emissionsReportMapper.toDto(emissionsReport))
            .orElse(null);
    }

    @Override
    public String submitToCromerr(Long emissionsReportId, String activityId) throws ApplicationException {
        String cromerrDocumentId=null;
        File tmp = null;
        try {
            Optional<EmissionsReport> emissionsReportOptional=erRepo.findById(emissionsReportId);
            if(emissionsReportOptional.isPresent()) {
                EmissionsReport emissionsReport=emissionsReportOptional.get();
                URL signatureServiceUrl = new URL(cefConfig.getCdxConfig().getRegisterSignServiceEndpoint());
                String signatureToken = signatureServiceClient.authenticate(signatureServiceUrl, cefConfig.getCdxConfig().getNaasUser(), cefConfig.getCdxConfig().getNaasPassword());
                String xmlData=cersXmlService.retrieveCersXml(emissionsReportId);
                SignatureDocumentType sigDoc = new SignatureDocumentType();
                sigDoc.setName("emissionsReport.xml");
                sigDoc.setFormat(SignatureDocumentFormatType.XML);
                tmp = File.createTempFile("Attachment", ".xml");
                FileUtils.writeStringToFile(tmp, xmlData, StandardCharsets.UTF_8);
                sigDoc.setContent(new DataHandler(new DocumentDataSource(tmp, "application/octet-stream")));
                cromerrDocumentId = signatureServiceClient.sign(signatureServiceUrl, signatureToken,
                        activityId, sigDoc);
                emissionsReport.setStatus(ReportStatus.SUBMITTED);
                emissionsReport.setCromerrActivityId(activityId);
                emissionsReport.setCromerrDocumentId(cromerrDocumentId);
                erRepo.save(emissionsReport);
                
                SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(emissionsReport.getAgencyCode());

                //send an email notification to the SLT's predefined address that a report has been submitted
                notificationService.sentReportSubmittedNotification(sltConfig.getSltEmail(),
                        cefConfig.getDefaultEmailAddress(), 
                        emissionsReport.getFacilitySites().get(0).getName(), 
                        emissionsReport.getYear().toString());               
            }
            return cromerrDocumentId;
        }catch(Exception e) {
            LOGGER.error("submitToCromerr - " + e.getMessage());
            LOGGER.error("submitToCromerr - " + e.getStackTrace().toString());
            throw ApplicationException.asApplicationException(e);
        }finally {
            if(tmp!=null) {
                FileUtils.deleteQuietly(tmp);
            }
        }
    }

    /**
     * Create a copy of the emissions report for the current year based on the specified facility and year.  The copy of the report is NOT saved to the database.
     * @param facilityEisProgramId
     * @param reportYear The year of the report that is being created
     * @return
     */
    @Override
    public EmissionsReportDto createEmissionReportCopy(String facilityEisProgramId, short reportYear) {

        return findMostRecentEmissionsReport(facilityEisProgramId)
            .map(mostRecentReport -> {

                EmissionsReport cloneReport = new EmissionsReport(mostRecentReport);
                cloneReport.setYear(reportYear);
                cloneReport.setStatus(ReportStatus.IN_PROGRESS);
                cloneReport.setValidationStatus(ValidationStatus.UNVALIDATED);
                cloneReport.clearId();

                return this.emissionsReportMapper.toDto(this.erRepo.save(cloneReport));
            })
            .orElse(null);

        /*

        // FIXME
        This code is being commented out until after the pilot and FRS integration can be solidified.

            .orElseGet(() -> this.facilitySiteService.retrieveFromFrs(facilityEisProgramId)
                .map(programFacility -> {

                    // create a shell/dto report
                    EmissionsReportDto newReport = new EmissionsReportDto();
                    newReport.setYear(reportYear);
                    newReport.setStatus(ReportStatus.IN_PROGRESS.toString());
                    newReport.setValidationStatus(ValidationStatus.UNVALIDATED.toString());

                    newReport.setFrsFacilityId(programFacility.getRegistryId());
                    newReport.setEisProgramId(programFacility.getProgramSystemId());

                    // TODO: Remove hard coded value
                    // Using GA for now until FRS has the agency id available for us
                    // https://alm.cgifederal.com/projects/browse/CEF-319
                    newReport.setAgencyCode(__HARD_CODED_AGENCY_CODE__);

                    return newReport;
                })
                .orElse(null)
            );
         */
    }

    @Override
    public EmissionsReportDto createEmissionReportFromFrs(String facilityEisProgramId, short reportYear) {

        return this.facilitySiteService.retrieveFromFrs(facilityEisProgramId)
            .map(programFacility -> {

                EmissionsReport newReport = new EmissionsReport();
                newReport.setYear(reportYear);
                newReport.setStatus(ReportStatus.IN_PROGRESS);
                newReport.setValidationStatus(ValidationStatus.UNVALIDATED);

                newReport.setFrsFacilityId(programFacility.getRegistryId());
                newReport.setEisProgramId(programFacility.getProgramSystemId());

                // TODO: Remove hard coded value
                // Using GA for now until FRS has the agency id available for us
                // https://alm.cgifederal.com/projects/browse/CEF-319
                newReport.setAgencyCode(__HARD_CODED_AGENCY_CODE__);

                newReport = this.erRepo.save(newReport);

                this.facilitySiteService.copyFromFrs(newReport);

                return this.emissionsReportMapper.toDto(newReport);
            })
            .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT,
                String.format("EIS Program ID [%s] is not found in FRS.", facilityEisProgramId)));
    }

    /**
     * Save the emissions report to the database.
     * @param emissionsReport
     * @return
     */
    @Override
    public EmissionsReportDto saveEmissionReport(EmissionsReport emissionsReport) {
        EmissionsReport savedReport = erRepo.save(emissionsReport);
        return emissionsReportMapper.toDto(savedReport);
    }

    /**
     * Save the emissions report to the database.
     * @param bulkEmissionsReport
     * @return
     */
    @Override
    public EmissionsReportDto saveBulkEmissionReport(EmissionsReportBulkUploadDto bulkEmissionsReport) {
        EmissionsReport emissionsReport = MapEmissionsReport(bulkEmissionsReport);

        for (FacilitySiteBulkUploadDto bulkFacility : bulkEmissionsReport.getFacilitySites()) {
            FacilitySite facility = MapFacility(bulkFacility);

            for (ReleasePointBulkUploadDto bulkRp : bulkEmissionsReport.getReleasePoints()) {
                ReleasePoint releasePoint = MapReleasePoint(bulkRp);

                if (bulkRp.getFacilitySiteId().equals(bulkFacility.getId())) {
                    releasePoint.setFacilitySite(facility);
                    facility.getReleasePoints().add(releasePoint);
                }
            }

            for (EmissionsUnitBulkUploadDto bulkEmissionsUnit : bulkEmissionsReport.getEmissionsUnits()) {
                EmissionsUnit emissionsUnit = MapEmissionsUnit(bulkEmissionsUnit);

                if (bulkEmissionsUnit.getFacilitySiteId().equals(bulkFacility.getId())) {
                    emissionsUnit.setFacilitySite(facility);
                    facility.getEmissionsUnits().add(emissionsUnit);
                }
            }

            facility.setEmissionsReport(emissionsReport);
            emissionsReport.getFacilitySites().add(facility);
        }


	    EmissionsReport savedReport = erRepo.save(emissionsReport);
	    return emissionsReportMapper.toDto(savedReport);
    }


    /**
     * Approve the specified reports and move to approved
     * @param reportIds
     * @return
     */
    public List<EmissionsReportDto> acceptEmissionsReports(List<Long> reportIds) {
        return updateEmissionsReportsStatus(reportIds, ReportStatus.APPROVED);
    }

    /**
     * Reject the specified reports and move back to in progress
     * @param reportIds
     * @return
     */
    public List<EmissionsReportDto> rejectEmissionsReports(List<Long> reportIds) {
        return updateEmissionsReportsStatus(reportIds, ReportStatus.IN_PROGRESS);
    }

    /**
     * Update the status of the specified reports
     * @param reportIds
     * @param status
     * @return
     */
    private List<EmissionsReportDto> updateEmissionsReportsStatus(List<Long> reportIds, ReportStatus status) {

        return StreamSupport.stream(this.erRepo.findAllById(reportIds).spliterator(), false)
            .map(report -> {
                report.setStatus(status);
                return this.emissionsReportMapper.toDto(this.erRepo.save(report));
            }).collect(Collectors.toList());

    }

    /**
     * Find the most recent emissions report model object for the given facility
     * @param facilityEisProgramId
     * @return The EmissionsReport model object
     */
    private Optional<EmissionsReport> findMostRecentEmissionsReport(String facilityEisProgramId) {

        return erRepo.findByEisProgramId(facilityEisProgramId, new Sort(Sort.Direction.DESC, "year"))
            .stream().findFirst();
    }


    /**
     * Add an emissions report to the list if one does not exist for the current year
     * @param emissionReports
     */
	private void addCurrentYear(List<EmissionsReport> emissionReports, String facilityEisProgramId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        
        //note: current reporting year is always the previous calendar year - like taxes.
        //e.g. in 2020, facilities will be creating a 2019 report.
        calendar.add(Calendar.YEAR, -1);
        short currentReportingYear = (short) calendar.get(Calendar.YEAR);

        if (!reportYearExists(currentReportingYear, emissionReports)) {
	        EmissionsReport newReport = new EmissionsReport();
	        newReport.setEisProgramId(facilityEisProgramId);
	        newReport.setStatus(ReportStatus.NEW);
	        newReport.setValidationStatus(ValidationStatus.UNVALIDATED);
	        newReport.setYear(currentReportingYear);

	        emissionReports.add(newReport);
        }
	}



    /**
     * Determine whether an emissions report exists for the given year
     * @param year
     * @param emissionReports
     * @return
     */
    private boolean reportYearExists(short year, List<EmissionsReport> emissionReports) {
        for (EmissionsReport rpt : emissionReports) {
	    	if (rpt.getYear() != null && rpt.getYear() ==  year) {
	    		return true;
	    	}
        }
        return false;
    }
    
    
    /**
     * Map an EmissionsReportBulkUploadDto to an EmissionsReport domain model
     */
    private EmissionsReport MapEmissionsReport(EmissionsReportBulkUploadDto bulkEmissionsReport) {
        EmissionsReport emissionsReport = new EmissionsReport();

        emissionsReport.setAgencyCode(bulkEmissionsReport.getAgencyCode());
        emissionsReport.setEisProgramId(bulkEmissionsReport.getEisProgramId());
        emissionsReport.setFrsFacilityId(bulkEmissionsReport.getFrsFacilityId());
        emissionsReport.setYear(bulkEmissionsReport.getYear());
        emissionsReport.setStatus(ReportStatus.valueOf(bulkEmissionsReport.getStatus()));

        if (bulkEmissionsReport.getValidationStatus() != null) {
            emissionsReport.setValidationStatus(ValidationStatus.valueOf(bulkEmissionsReport.getValidationStatus()));
        }

        return emissionsReport;
    }


    /**
     * Map a FacilitySiteBulkUploadDto to a FacilitySite domain model
     */
    private FacilitySite MapFacility(FacilitySiteBulkUploadDto bulkFacility) {
        FacilitySite facility = new FacilitySite();

        facility.setFrsFacilityId(bulkFacility.getFrsFacilityId());
        facility.setAltSiteIdentifier(bulkFacility.getAltSiteIdentifier());
        facility.setName(bulkFacility.getName());
        facility.setDescription(bulkFacility.getDescription());
        facility.setStatusYear(bulkFacility.getStatusYear());
        facility.setStreetAddress(bulkFacility.getStreetAddress());
        facility.setCity(bulkFacility.getCity());
        facility.setCounty(bulkFacility.getCounty());
        facility.setStateCode(bulkFacility.getStateCode());
        facility.setCountryCode(bulkFacility.getCountryCode());
        facility.setPostalCode(bulkFacility.getPostalCode());
        facility.setLatitude(bulkFacility.getLatitude());
        facility.setLongitude(bulkFacility.getLongitude());
        facility.setMailingStreetAddress(bulkFacility.getMailingStreetAddress());
        facility.setMailingCity(bulkFacility.getMailingCity());
        facility.setMailingStateCode(bulkFacility.getMailingStateCode());
        facility.setMailingPostalCode(bulkFacility.getMailingPostalCode());
        facility.setEisProgramId(bulkFacility.getEisProgramId());

        if (bulkFacility.getNaicsCode() != null) {
            naicsCodeRepo.findById(bulkFacility.getNaicsCode()).ifPresent(code -> {
                FacilityNAICSXref naics = new FacilityNAICSXref();
                naics.setFacilitySite(facility);
                naics.setNaicsCode(code);
                naics.setPrimaryFlag(true);
                facility.getFacilityNAICS().add(naics);
            });
        }
        if (bulkFacility.getFacilityCategoryCode() != null) {
            facility.setFacilityCategoryCode(facilityCategoryRepo.findById(bulkFacility.getFacilityCategoryCode()).orElse(null));
        }
        if (bulkFacility.getFacilitySourceTypeCode() != null) {
            facility.setFacilitySourceTypeCode(facilitySourceTypeRepo.findById(bulkFacility.getFacilitySourceTypeCode()).orElse(null));
        }
        if (bulkFacility.getOperatingStatusCode() != null) {
            facility.setOperatingStatusCode(operatingStatusRepo.findById(bulkFacility.getOperatingStatusCode()).orElse(null));
        }
        if (bulkFacility.getProgramSystemCode() != null) {
            facility.setProgramSystemCode(programSystemCodeRepo.findById(bulkFacility.getProgramSystemCode()).orElse(null));
        }
        if (bulkFacility.getTribalCode() != null) {
            facility.setTribalCode(tribalCodeRepo.findById(bulkFacility.getTribalCode()).orElse(null));
        }

        return facility;
    }


    /**
     * Map a ReleasePointBulkUploadDto to a ReleasePoint domain model
     */
    private ReleasePoint MapReleasePoint(ReleasePointBulkUploadDto bulkReleasePoint) {
        ReleasePoint releasePoint = new ReleasePoint();

        releasePoint.setReleasePointIdentifier(bulkReleasePoint.getReleasePointIdentifier());
        releasePoint.setDescription(bulkReleasePoint.getDescription());
        releasePoint.setStackHeight(bulkReleasePoint.getStackHeight());
        releasePoint.setStackDiameter(bulkReleasePoint.getStackDiameter());
        releasePoint.setExitGasVelocity(bulkReleasePoint.getExitGasVelocity());
        releasePoint.setExitGasTemperature(bulkReleasePoint.getExitGasTemperature());
        releasePoint.setExitGasFlowRate(bulkReleasePoint.getExitGasFlowRate());
        releasePoint.setStatusYear(bulkReleasePoint.getStatusYear());
        releasePoint.setFugitiveLine1Latitude(bulkReleasePoint.getFugitiveLine1Latitude());
        releasePoint.setFugitiveLine1Longitude(bulkReleasePoint.getFugitiveLine1Longitude());
        releasePoint.setFugitiveLine2Latitude(bulkReleasePoint.getFugitiveLine2Latitude());
        releasePoint.setFugitiveLine2Longitude(bulkReleasePoint.getFugitiveLine2Longitude());
        releasePoint.setLatitude(bulkReleasePoint.getLatitude());
        releasePoint.setLongitude(bulkReleasePoint.getLongitude());
        releasePoint.setComments(bulkReleasePoint.getComments());

        if (bulkReleasePoint.getProgramSystemCode() != null) {
            releasePoint.setProgramSystemCode(programSystemCodeRepo.findById(bulkReleasePoint.getProgramSystemCode()).orElse(null));
        }
        if (bulkReleasePoint.getOperatingStatusCode() != null) {
            releasePoint.setOperatingStatusCode(operatingStatusRepo.findById(bulkReleasePoint.getOperatingStatusCode()).orElse(null));
        }
        if (bulkReleasePoint.getTypeCode() != null) {
            releasePoint.setTypeCode(releasePointTypeRepo.findById(bulkReleasePoint.getTypeCode()).orElse(null));
        }
        if (bulkReleasePoint.getStackHeightUomCode() != null) {
            releasePoint.setStackHeightUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getStackHeightUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getStackDiameterUomCode() != null) {
            releasePoint.setStackDiameterUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getStackDiameterUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getExitGasVelocityUomCode() != null) {
            releasePoint.setExitGasVelocityUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getExitGasVelocityUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getExitGasFlowUomCode() != null) {
            releasePoint.setExitGasFlowUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getExitGasFlowUomCode()).orElse(null));
        }

        return releasePoint;
    }


    /**
     * Map an EmissionsUnitBulkUploadDto to an EmissionsUnit domain model
     */
    private EmissionsUnit MapEmissionsUnit(EmissionsUnitBulkUploadDto bulkEmissionsUnit) {
        EmissionsUnit emissionsUnit = new EmissionsUnit();

        emissionsUnit.setUnitIdentifier(bulkEmissionsUnit.getUnitIdentifier());
        emissionsUnit.setProgramSystemCode(bulkEmissionsUnit.getProgramSystemCode());
        emissionsUnit.setDescription(bulkEmissionsUnit.getDescription());
        emissionsUnit.setTypeCodeDescription(bulkEmissionsUnit.getTypeCodeDescription());
        emissionsUnit.setStatusYear(bulkEmissionsUnit.getStatusYear());
        emissionsUnit.setDesignCapacity(bulkEmissionsUnit.getDesignCapacity());
        emissionsUnit.setComments(bulkEmissionsUnit.getComments());

        if (bulkEmissionsUnit.getTypeCode() != null) {
            emissionsUnit.setUnitTypeCode(unitTypeRepo.findById(bulkEmissionsUnit.getTypeCode()).orElse(null));
        }
        if (bulkEmissionsUnit.getOperatingStatusCodeDescription() != null) {
            emissionsUnit.setOperatingStatusCode(operatingStatusRepo.findById(bulkEmissionsUnit.getOperatingStatusCodeDescription()).orElse(null));
        }
        if (bulkEmissionsUnit.getUnitOfMeasureCode() != null) {
            emissionsUnit.setUnitOfMeasureCode(unitMeasureCodeRepo.findById(bulkEmissionsUnit.getUnitOfMeasureCode()).orElse(null));
        }

        return emissionsUnit;
    }

}
