package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.api.rest.EmissionsReportApi.ReviewDTO;
import gov.epa.cef.web.client.soap.DocumentDataSource;
import gov.epa.cef.web.client.soap.SignatureServiceClient;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EmissionsOperatingTypeCodeRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.ReportAttachmentRepository;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.EmissionsReportStatusService;
import gov.epa.cef.web.service.FacilitySiteContactService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.LookupService;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.UserFeedbackService;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EmissionsReportStarterDto;
import gov.epa.cef.web.service.dto.FacilitySiteContactDto;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import gov.epa.cef.web.util.SLTConfigHelper;
import net.exchangenetwork.wsdl.register.sign._1.SignatureDocumentFormatType;
import net.exchangenetwork.wsdl.register.sign._1.SignatureDocumentType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataHandler;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmissionsReportServiceImpl implements EmissionsReportService {

    private static final Logger logger = LoggerFactory.getLogger(EmissionsReportServiceImpl.class);

    @Autowired
    private EmissionsReportRepository erRepo;

    @Autowired
    private EmissionsOperatingTypeCodeRepository emissionsOperatingTypeCodeRepo;

    @Autowired
    private ReportAttachmentRepository reportAttachmentsRepo;

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

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmissionsReportStatusService statusService;

    @Autowired
    private FacilitySiteContactService contactService;

    @Autowired
    private LookupService lookupService;

    @Autowired
    private UserFeedbackService userFeedbackService;

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

    @Override
    public Optional<EmissionsReport> retrieve(long id) {

        return erRepo.findById(id);
    }

    @Override
    public Optional<EmissionsReport> retrieveByEisProgramIdAndYear(@NotBlank String facilityEisProgramId, int year) {

        return erRepo.findByEisProgramIdAndYear(facilityEisProgramId, Integer.valueOf(year).shortValue());
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
                SignatureDocumentType sigDoc = new SignatureDocumentType();
                sigDoc.setName("emissionsReport.xml");
                sigDoc.setFormat(SignatureDocumentFormatType.XML);
                tmp = File.createTempFile("Attachment", ".xml");
                try (OutputStream outputStream = new FileOutputStream(tmp)) {
                    cersXmlService.writeCersXmlTo(emissionsReportId, outputStream, EisSubmissionStatus.QaFacility);
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
                notificationService.sendReportSubmittedNotification(sltConfig.getSltEmail(),
                        cefConfig.getDefaultEmailAddress(),
                        emissionsReport.getFacilitySites().get(0).getName(),
                        emissionsReport.getYear().toString());
            }
            return cromerrDocumentId;
        } catch(IOException e) {
            logger.error("submitToCromerr - {}", e.getMessage());
            throw ApplicationException.asApplicationException(e);
        } finally {
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
                cloneReport.setHasSubmitted(false);
                cloneReport.setEisLastSubmissionStatus(EisSubmissionStatus.NotStarted);
                cloneReport.clearId();

            	this.reportService.createReportHistory(this.emissionsReportMapper.toDto(this.erRepo.save(cloneReport)).getId(), ReportAction.COPIED_FWD);

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

    public EmissionsReportDto createEmissionReport(EmissionsReportStarterDto reportDto) {

        EmissionsReport newReport = new EmissionsReport();
        newReport.setEisProgramId(reportDto.getEisProgramId());
        newReport.setYear(reportDto.getYear());
        newReport.setStatus(ReportStatus.IN_PROGRESS);
        newReport.setValidationStatus(ValidationStatus.UNVALIDATED);
        newReport.setFrsFacilityId(reportDto.getFrsFacilityId());
        newReport.setAgencyCode(reportDto.getStateCode());
        newReport.setEisLastSubmissionStatus(EisSubmissionStatus.NotStarted);
        newReport.setHasSubmitted(false);

        FacilitySite facilitySite = this.facilitySiteService.transform(reportDto.getFacilitySite());
        facilitySite.setEmissionsReport(newReport);
        facilitySite.setEisProgramId(reportDto.getEisProgramId());

        //TODO:
        //Hard coding this as a temporary workaround to test onboarding for DC. Long term we should consider changing the
        //"agency code" on the report table to use the EIS Program System Code instead of state abbreviations. We can use
        //the "Responsible Agency Code" returned by CDX to give us the program system code and look that up instead.
        String programSystemCode = null;
        switch (reportDto.getStateCode()) {
			case "GA": 
				programSystemCode = "GADNR";
			case "DC":
				programSystemCode = "DOEE";
		};
        
        facilitySite.setProgramSystemCode(
        		this.lookupService.retrieveProgramSystemTypeCodeEntityByCode(programSystemCode));

        newReport.getFacilitySites().add(facilitySite);

        return saveAndAuditEmissionsReport(newReport, ReportAction.CREATED);
    }

    @Override
    public EmissionsReportDto saveAndAuditEmissionsReport(EmissionsReport emissionsReport, ReportAction reportAction) {

        EmissionsReport result = this.erRepo.save(emissionsReport);

        logger.debug("Report {} {}.", result.getId(), reportAction.label());

        this.reportService.createReportHistory(result.getId(), reportAction);

        return this.emissionsReportMapper.toDto(result);
    }

    /**
     * Delete an emissons report for a given id
     * @param id
     */
    public void delete(Long id) {
    	userFeedbackService.removeReportFromUserFeedback(id);
    	erRepo.deleteById(id);
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
     * Approve the specified reports and move to approved
     * @param reportIds
     * @param comments
     * @return
     */
    @Override
    public List<EmissionsReportDto>acceptEmissionsReports(List<Long> reportIds, String comments) {
    	List<EmissionsReportDto> updatedReports = statusService.acceptEmissionsReports(reportIds);
        reportService.createReportHistory(reportIds, ReportAction.ACCEPTED, comments);

    	StreamSupport.stream(this.erRepo.findAllById(reportIds).spliterator(), false)
	      .forEach(report -> {

	    	  //there should always be exactly one facility site for a CEF emissions report for now. This may change at
	    	  //some point in the future if different report types are included in the system
	    	  FacilitySite reportFacilitySite = report.getFacilitySites().get(0);

	    	  //check for "Emission Inventory" contacts in the facility site and send them a notification that their report
	    	  //has been accepted
	    	  List<FacilitySiteContactDto> eiContacts = contactService.retrieveInventoryContactsForFacility(reportFacilitySite.getId());

	    	  eiContacts.forEach(contact -> {
	    		  //if the EI contact has a email address - send them the notification
	    		  if (StringUtils.isNotEmpty(contact.getEmail())) {
			          notificationService.sendReportAcceptedNotification(contact.getEmail(),
			        		  cefConfig.getDefaultEmailAddress(),
			        		  reportFacilitySite.getName(),
			        		  report.getYear().toString(),
			        		  comments);
	    		  }
	    	  });
	      });
    	return updatedReports;
    }

    /**
     * Reject the specified reports and move to Rejected
     * @param reportIds
     * @param comments
     * @return
     */
    @Override
    public List<EmissionsReportDto>rejectEmissionsReports(ReviewDTO reviewDTO) {
    	List<EmissionsReportDto> updatedReports = statusService.rejectEmissionsReports(reviewDTO.getReportIds());

    	if(reviewDTO.getAttachmentId() != null) {
    		ReportAttachment attachment = this.reportAttachmentsRepo.findById(reviewDTO.getAttachmentId())
    			.orElseThrow(() -> new NotExistException("Report Attachment", reviewDTO.getAttachmentId()));

    		this.erRepo.findAllById(reviewDTO.getReportIds())
            .forEach(report -> {

    			if (attachment.getEmissionsReport().getId() == report.getId()) {
    				reportService.createReportHistory(report.getId(), ReportAction.REJECTED, reviewDTO.getComments(), attachment);

    			} else {
    				ReportAttachment copyAttachment = new ReportAttachment(attachment);
    	    		copyAttachment.clearId();
    	    		copyAttachment.setEmissionsReport(report);

    	    		ReportAttachment result = reportAttachmentsRepo.save(copyAttachment);
    	    		reportService.createReportHistory(report.getId(), ReportAction.REJECTED, reviewDTO.getComments(), result);
    			}
    		});

    	} else {
    		reportService.createReportHistory(reviewDTO.getReportIds(), ReportAction.REJECTED, reviewDTO.getComments());
    	}

    	StreamSupport.stream(this.erRepo.findAllById(reviewDTO.getReportIds()).spliterator(), false)
	      .forEach(report -> {

	    	  //there should always be exactly one facility site for a CEF emissions report for now. This may change at
	    	  //some point in the future if different report types are included in the system
	    	  FacilitySite reportFacilitySite = report.getFacilitySites().get(0);

	    	  //check for "Emissions Inventory" contacts in the facility site and send them a notification that their report
	    	  //has been accepted
	    	  List<FacilitySiteContactDto> eiContacts = contactService.retrieveInventoryContactsForFacility(reportFacilitySite.getId());

	    	  eiContacts.forEach(contact -> {
	    		  //if the EI contact has a email address - send them the notification
	    		  if (StringUtils.isNotEmpty(contact.getEmail())) {
			          notificationService.sendReportRejectedNotification(contact.getEmail(),
			        		  cefConfig.getDefaultEmailAddress(),
			        		  reportFacilitySite.getName(),
			        		  report.getYear().toString(),
			        		  reviewDTO.getComments(), reviewDTO.getAttachmentId());
	    		  }
	    	  });
	      });
    	return updatedReports;
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

    @Override
    public EmissionsReportDto updateSubmitted(long reportId, boolean submitted){

    	EmissionsReport emissionsReport = this.erRepo.findById(reportId)
            .orElseThrow(() -> new NotExistException("Emissions Report", reportId));

    	emissionsReport.setHasSubmitted(submitted);

    	return this.emissionsReportMapper.toDto(this.erRepo.save(emissionsReport));
    }
}
