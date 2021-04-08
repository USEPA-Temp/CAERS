package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.api.rest.EmissionsReportApi.ReviewDTO;
import gov.epa.cef.web.client.soap.DocumentDataSource;
import gov.epa.cef.web.client.soap.SignatureServiceClient;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EmissionsOperatingTypeCodeRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.MasterFacilityRecordRepository;
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
import gov.epa.cef.web.service.mapper.MasterFacilityRecordMapper;
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
    private MasterFacilityRecordRepository mfrRepo;

    @Autowired
    private ReportAttachmentRepository reportAttachmentsRepo;

    @Autowired
    private EmissionsReportMapper emissionsReportMapper;

    @Autowired
    private MasterFacilityRecordMapper mfrMapper;

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
    private MasterFacilityRecordServiceImpl mfrService;

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
    public List<EmissionsReportDto> findByMasterFacilityRecordId(Long masterFacilityRecordId) {
        return findByMasterFacilityRecordId(masterFacilityRecordId, false);
    }


    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findByFacilityId(java.lang.String)
     */
    @Override
    public List<EmissionsReportDto> findByMasterFacilityRecordId(Long masterFacilityRecordId, boolean addReportForCurrentYear) {
        List<EmissionsReport> emissionReports= erRepo.findByMasterFacilityRecordId(masterFacilityRecordId);
        if (addReportForCurrentYear) {
        	addCurrentYear(emissionReports, masterFacilityRecordId);
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
    public Optional<EmissionsReport> retrieveByMasterFacilityRecordIdAndYear(@NotBlank Long masterFacilityRecordId, int year) {

        return erRepo.findByMasterFacilityRecordIdAndYear(masterFacilityRecordId, Integer.valueOf(year).shortValue());
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findMostRecentByFacility(java.lang.String)
     */
    @Override
    public EmissionsReportDto findMostRecentByMasterFacilityRecordId(Long masterFacilityRecordId) {

        return findMostRecentEmissionsReport(masterFacilityRecordId)
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
                URL signatureServiceUrl = new URL(cefConfig.getCdxConfig().getRegisterSignServiceEndpoint());
                String signatureToken = signatureServiceClient.authenticate(signatureServiceUrl, cefConfig.getCdxConfig().getNaasUser(), cefConfig.getCdxConfig().getNaasPassword());
                SignatureDocumentType sigDoc = new SignatureDocumentType();
                sigDoc.setName("emissionsReport.xml");
                sigDoc.setFormat(SignatureDocumentFormatType.XML);
                tmp = File.createTempFile("Attachment", ".xml");
                try (OutputStream outputStream = new FileOutputStream(tmp)) {
                    // use null status to get full XML generation
                    if (cefConfig.getFeatureCersV2Enabled()) {
                        cersXmlService.writeCersV2XmlTo(emissionsReportId, outputStream, null);
                    } else {
                        cersXmlService.writeCersXmlTo(emissionsReportId, outputStream, null);
                    }
                }
                sigDoc.setContent(new DataHandler(new DocumentDataSource(tmp, "application/octet-stream")));
                cromerrDocumentId =
                    signatureServiceClient.sign(signatureServiceUrl, signatureToken, activityId, sigDoc);
                // get fresh copy of emissionsReport to make sure any modifications from creating the XML are cleared
                EmissionsReport emissionsReport = erRepo.findById(emissionsReportId).get();
                emissionsReport.setStatus(ReportStatus.SUBMITTED);
                emissionsReport.setCromerrActivityId(activityId);
                emissionsReport.setCromerrDocumentId(cromerrDocumentId);
                erRepo.save(emissionsReport);

                SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(emissionsReport.getProgramSystemCode().getCode());
                String cdxSubmissionUrl = cefConfig.getCdxConfig().getSubmissionHistoryUrl() + activityId;

				//there should always be exactly one facility site for a CEF emissions report for now. This may change at
				//some point in the future if different report types are included in the system
				FacilitySite reportFacilitySite = emissionsReport.getFacilitySites().get(0);
                List<FacilitySiteContactDto> eiContacts = contactService.retrieveInventoryContactsForFacility(reportFacilitySite.getId());
                
                eiContacts.forEach(contact -> {
	                //send an email notification to the certifier and cc SLT's predefined address that a report has been submitted
	                notificationService.sendReportSubmittedNotification(
	                		contact.getEmail(),
	                		sltConfig.getSltEmail(),
	                        cefConfig.getDefaultEmailAddress(),
	                        emissionsReport.getFacilitySites().get(0).getName(),
	                        emissionsReport.getYear().toString(),
	                        sltConfig.getSltEisProgramCode(),
	                        sltConfig.getSltSupportEmail(),
	                        cdxSubmissionUrl);
                });
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
    public EmissionsReportDto createEmissionReportCopy(Long masterFacilityRecordId, short reportYear) {
        return findMostRecentEmissionsReport(masterFacilityRecordId)
            .map(mostRecentReport -> {
                EmissionsReport cloneReport = new EmissionsReport(mostRecentReport);
                cloneReport.setYear(reportYear);
                cloneReport.setStatus(ReportStatus.IN_PROGRESS);
                cloneReport.setValidationStatus(ValidationStatus.UNVALIDATED);
                cloneReport.setHasSubmitted(false);
                cloneReport.setEisLastSubmissionStatus(EisSubmissionStatus.NotStarted);
                cloneReport.getFacilitySites().forEach(fs -> {
                    this.mfrMapper.updateFacilitySite(cloneReport.getMasterFacilityRecord(), fs);
                });
                cloneReport.clearId();

            	this.reportService.createReportHistory(this.emissionsReportMapper.toDto(this.erRepo.save(cloneReport)).getId(), ReportAction.COPIED_FWD);

                return this.emissionsReportMapper.toDto(this.erRepo.save(cloneReport));
            })
            .orElse(null);

    }

    public EmissionsReportDto createEmissionReport(EmissionsReportStarterDto reportDto) {

        MasterFacilityRecord mfr = this.mfrRepo.findById(reportDto.getMasterFacilityRecordId())
           .orElseThrow(() -> new NotExistException("Master Facility Record", reportDto.getMasterFacilityRecordId()));

        EmissionsReport newReport = new EmissionsReport();
        newReport.setEisProgramId(mfr.getEisProgramId());
        newReport.setMasterFacilityRecord(mfr);
        newReport.setYear(reportDto.getYear());
        newReport.setStatus(ReportStatus.IN_PROGRESS);
        newReport.setValidationStatus(ValidationStatus.UNVALIDATED);
        newReport.setEisLastSubmissionStatus(EisSubmissionStatus.NotStarted);
        newReport.setHasSubmitted(false);

        FacilitySite facilitySite = this.mfrMapper.toFacilitySite(mfr);
        facilitySite.setEmissionsReport(newReport);

        newReport.setProgramSystemCode(facilitySite.getProgramSystemCode());

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
    private Optional<EmissionsReport> findMostRecentEmissionsReport(Long masterFacilityRecordId) {

        return erRepo.findByMasterFacilityRecordId(masterFacilityRecordId, new Sort(Sort.Direction.DESC, "year"))
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
	    	  
	    	  SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(report.getProgramSystemCode().getCode());

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
			        		  comments,
			        		  sltConfig.getSltEisProgramCode(),
			        		  sltConfig.getSltSupportEmail());
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
	    	  SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(report.getProgramSystemCode().getCode());
	    	  
	    	  eiContacts.forEach(contact -> {
	    		  //if the EI contact has a email address - send them the notification
	    		  if (StringUtils.isNotEmpty(contact.getEmail())) {
			          notificationService.sendReportRejectedNotification(contact.getEmail(),
			        		  sltConfig.getSltEmail(),
			        		  cefConfig.getDefaultEmailAddress(),
			        		  reportFacilitySite.getName(),
			        		  report.getYear().toString(),
			        		  reviewDTO.getComments(), reviewDTO.getAttachmentId(),
			        		  sltConfig.getSltEisProgramCode(),
			        		  sltConfig.getSltSupportEmail());
	    		  }
	    	  });
	      });
    	return updatedReports;
    }
    
    /**
     * Add an emissions report to the list if one does not exist for the current year
     * @param emissionReports
     */
	private void addCurrentYear(List<EmissionsReport> emissionReports, Long masterFacilityRecordId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        //note: current reporting year is always the previous calendar year - like taxes.
        //e.g. in 2020, facilities will be creating a 2019 report.
        calendar.add(Calendar.YEAR, -1);
        short currentReportingYear = (short) calendar.get(Calendar.YEAR);

        if (!reportYearExists(currentReportingYear, emissionReports)) {
	        EmissionsReport newReport = new EmissionsReport();
	        newReport.setStatus(ReportStatus.NEW);
	        newReport.setValidationStatus(ValidationStatus.UNVALIDATED);
	        newReport.setYear(currentReportingYear);

	        MasterFacilityRecord mfr = new MasterFacilityRecord();
	        mfr.setId(masterFacilityRecordId);
	        newReport.setMasterFacilityRecord(mfr);

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
