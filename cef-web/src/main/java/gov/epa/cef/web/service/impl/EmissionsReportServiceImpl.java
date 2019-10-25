package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.client.soap.DocumentDataSource;
import gov.epa.cef.web.client.soap.SignatureServiceClient;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import gov.epa.cef.web.util.SLTConfigHelper;
import net.exchangenetwork.wsdl.register.sign._1.SignatureDocumentFormatType;
import net.exchangenetwork.wsdl.register.sign._1.SignatureDocumentType;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataHandler;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmissionsReportServiceImpl implements EmissionsReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionsReportServiceImpl.class);

    // TODO: Remove hard coded value
    // https://alm.cgifederal.com/projects/browse/CEF-319
    private static final String __HARD_CODED_AGENCY_CODE__ = "GA";

    @Autowired
    private EmissionsReportRepository erRepo;

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
                byte[] xmlData=cersXmlService.retrieveCersXml(emissionsReportId);
                SignatureDocumentType sigDoc = new SignatureDocumentType();
                sigDoc.setName("emissionsReport.xml");
                sigDoc.setFormat(SignatureDocumentFormatType.XML);
                tmp = File.createTempFile("Attachment", ".xml");
                try (InputStream is = new ByteArrayInputStream(xmlData)) {
                    Files.copy(is, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                sigDoc.setContent(new DataHandler(new DocumentDataSource(tmp, "application/octet-stream")));
                cromerrDocumentId =
                    signatureServiceClient.sign(signatureServiceUrl, signatureToken, activityId, sigDoc);
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

}
