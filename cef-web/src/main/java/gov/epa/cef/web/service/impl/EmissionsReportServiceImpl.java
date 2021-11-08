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

import gov.epa.cef.web.api.rest.EmissionsReportApi.ReviewDTO;
import gov.epa.cef.web.client.soap.DocumentDataSource;
import gov.epa.cef.web.client.soap.SignatureServiceClient;
import gov.epa.cef.web.config.AppPropertyName;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.MasterFacilityNAICSXref;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.Attachment;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.provider.system.AdminPropertyProvider;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.MasterFacilityNAICSXrefRepository;
import gov.epa.cef.web.repository.MasterFacilityRecordRepository;
import gov.epa.cef.web.repository.AttachmentRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.EmissionsReportStatusService;
import gov.epa.cef.web.service.FacilitySiteContactService;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.UserFeedbackService;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;
import gov.epa.cef.web.service.dto.EmissionsReportAgencyDataDto;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EmissionsReportStarterDto;
import gov.epa.cef.web.service.dto.FacilitySiteContactDto;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import gov.epa.cef.web.service.mapper.FacilityNAICSMapper;
import gov.epa.cef.web.service.mapper.LookupEntityMapper;
import gov.epa.cef.web.service.mapper.MasterFacilityNAICSMapper;
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

import com.google.common.base.Strings;

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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmissionsReportServiceImpl implements EmissionsReportService {

    private static final Logger logger = LoggerFactory.getLogger(EmissionsReportServiceImpl.class);

    @Autowired
    private EmissionsReportRepository erRepo;

    @Autowired
    private MasterFacilityRecordRepository mfrRepo;

    @Autowired
    private AttachmentRepository reportAttachmentsRepo;
    
    @Autowired
    private MasterFacilityNAICSXrefRepository mfNaicsXrefRepo;
    
    @Autowired
    private EmissionsReportMapper emissionsReportMapper;

    @Autowired
    private MasterFacilityRecordMapper mfrMapper;
    
    @Autowired
    private MasterFacilityNAICSMapper mfNaicsMapper;
    
    @Autowired
    private FacilityNAICSMapper facilityNaicsMapper;

    @Autowired
    private LookupEntityMapper lookupMapper;

    @Autowired
    private CefConfig cefConfig;

    @Autowired
    private SLTConfigHelper sltConfigHelper;

    @Autowired
    private SignatureServiceClient signatureServiceClient;

    @Autowired
    private CersXmlService cersXmlService;

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
    private UserFeedbackService userFeedbackService;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private AdminPropertyProvider propertyProvider;

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

    /**
     * Find all agencies with reports and which years they have reports for
     */
    public List<EmissionsReportAgencyDataDto> findAgencyReportedYears() {

        List<EmissionsReportAgencyDataDto> result = erRepo.findDistinctProgramSystems()
                .stream()
                .map(agency -> {
                    return new EmissionsReportAgencyDataDto()
                            .withProgramSystemCode(this.lookupMapper.toDto(agency))
                            .withYears(erRepo.findDistinctReportingYears(agency.getCode()));
                }).collect(Collectors.toList());
        return result;
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
                
                // update MFR record with site changes
                MasterFacilityRecord mfr = emissionsReport.getMasterFacilityRecord();
                FacilitySite fs = emissionsReport.getFacilitySites().get(0);
                
                SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(emissionsReport.getProgramSystemCode().getCode());
                
                // update Master Facility NAICS with Facility Site NAICS if edit NAICs is enabled for certifiers and preparers
                if (Boolean.FALSE.equals(sltConfig.getFacilityNaicsEnabled())) {
	                mfNaicsXrefRepo.deleteByMasterFacilityId(mfr.getId());
	                
	                for (FacilityNAICSXref fsNaics: fs.getFacilityNAICS()) {
	                	MasterFacilityNAICSXref mfNaics;
		                mfNaics = mfNaicsMapper.toMasterFacilityNaicsXref(fsNaics);
		                mfNaics.setMasterFacilityRecord(mfr);
		                mfr.getMasterFacilityNAICS().add(mfNaics);
	                }
                }
                
                mfrService.updateMasterFacilityRecord(mfr, fs);
               
                if (this.propertyProvider.getBoolean(AppPropertyName.FeatureFacilityAutomatedEmailEnabled)) {
	                String cdxSubmissionUrl = cefConfig.getCdxConfig().getSubmissionHistoryUrl() + activityId;
	                String certifierEmail = securityService.getCurrentApplicationUser().getEmail();

	                //send an email notification to the certifier and cc SLT's predefined address that a report has been submitted
	                notificationService.sendReportSubmittedNotification(
	                		certifierEmail,
	                		sltConfig.getSltEmail(),
	                        cefConfig.getDefaultEmailAddress(),
	                        emissionsReport.getFacilitySites().get(0).getName(),
	                        emissionsReport.getYear().toString(),
	                        sltConfig.getSltEisProgramCode(),
	                        sltConfig.getSltEmail(),
	                        cdxSubmissionUrl);
                }
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
                cloneReport.setReturnedReport(false);
                cloneReport.setEisLastSubmissionStatus(EisSubmissionStatus.NotStarted);
                cloneReport.getFacilitySites().forEach(fs -> {
                    this.mfrMapper.updateFacilitySite(cloneReport.getMasterFacilityRecord(), fs);
                    
                    fs.getFacilityNAICS().clear();
                    for (MasterFacilityNAICSXref masterFacilityNAICS : cloneReport.getMasterFacilityRecord().getMasterFacilityNAICS()) {
                    	FacilityNAICSXref facilityNAICS;
                    	facilityNAICS = facilityNaicsMapper.toFacilityNaicsXref(masterFacilityNAICS);
                        facilityNAICS.setFacilitySite(fs);
                        fs.getFacilityNAICS().add(facilityNAICS);
                    }
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
        newReport.setReturnedReport(false);

        FacilitySite facilitySite = this.mfrMapper.toFacilitySite(mfr);
        facilitySite.setEmissionsReport(newReport);
        
        newReport.setProgramSystemCode(facilitySite.getProgramSystemCode());

        newReport.getFacilitySites().add(facilitySite);
        
        EmissionsReport report = this.erRepo.save(newReport);
        
        SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(mfr.getProgramSystemCode().getCode());
        if (Boolean.TRUE.equals(sltConfig.getFacilityNaicsEnabled())) {
	        for (FacilitySite fs : report.getFacilitySites()) {
		        for (MasterFacilityNAICSXref masterFacilityNAICS : mfr.getMasterFacilityNAICS()) {
		        	FacilityNAICSXref facilityNAICS;
		        	facilityNAICS = facilityNaicsMapper.toFacilityNaicsXref(masterFacilityNAICS);
		            facilityNAICS.setFacilitySite(fs);
		            
		            fs.getFacilityNAICS().add(facilityNAICS);
		        }
	        }
        }
        

        return saveAndAuditEmissionsReport(report, ReportAction.CREATED);
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
	  *  Begin Advanced QA for the specified reports, move from Submitted to Advanced QA
	  * @param reportIds
	  * @return
	  */
	 @Override
	 public List<EmissionsReportDto> beginAdvancedQAEmissionsReports(List<Long> reportIds){
	     List<EmissionsReportDto> updatedReports = statusService.advancedQAEmissionsReports(reportIds);
	     reportService.createReportHistory(reportIds, ReportAction.ADVANCED_QA);
	
	     if (this.propertyProvider.getBoolean(AppPropertyName.FeatureFacilityAutomatedEmailEnabled)) {
	    	 StreamSupport.stream(this.erRepo.findAllById(reportIds).spliterator(), false)
		       .forEach(report -> {
		           
		           SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(report.getProgramSystemCode().getCode());
		
		           //there should always be exactly one facility site for a CEF emissions report for now. This may change at
		           //some point in the future if different report types are included in the system
		           FacilitySite reportFacilitySite = report.getFacilitySites().get(0);
	
		           //check for "Emission Inventory" contacts in the facility site and send them a notification that advanced QA has
		           // begun for their report
		           List<FacilitySiteContactDto> eiContacts = contactService.retrieveInventoryContactsForFacility(reportFacilitySite.getId());
	           
		           eiContacts.forEach(contact -> {
		               //if the EI contact has a email address - send them the notification
		               if (StringUtils.isNotEmpty(contact.getEmail())) {
		                   notificationService.sendReportAdvancedQANotification(contact.getEmail(),
		                           cefConfig.getDefaultEmailAddress(),
		                           reportFacilitySite.getName(),
		                           report.getYear().toString(),
		                           sltConfig.getSltEisProgramCode(),
		                           sltConfig.getSltEmail());
		               }
		           });
		       });
	     }
	     return updatedReports;
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

        if (this.propertyProvider.getBoolean(AppPropertyName.FeatureFacilityAutomatedEmailEnabled)) {
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
				        		  sltConfig.getSltEmail());
		    		  }
		    	  });
		      });
        }
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
    		Attachment attachment = this.reportAttachmentsRepo.findById(reviewDTO.getAttachmentId())
    			.orElseThrow(() -> new NotExistException("Report Attachment", reviewDTO.getAttachmentId()));

    		this.erRepo.findAllById(reviewDTO.getReportIds())
            .forEach(report -> {

    			if (attachment.getEmissionsReport().getId() == report.getId()) {
    				reportService.createReportHistory(report.getId(), ReportAction.REJECTED, reviewDTO.getComments(), attachment);

    			} else {
    				Attachment copyAttachment = new Attachment(attachment);
    	    		copyAttachment.clearId();
    	    		copyAttachment.setEmissionsReport(report);

    	    		Attachment result = reportAttachmentsRepo.save(copyAttachment);
    	    		reportService.createReportHistory(report.getId(), ReportAction.REJECTED, reviewDTO.getComments(), result);
    			}
    		});

    	} else {
    		reportService.createReportHistory(reviewDTO.getReportIds(), ReportAction.REJECTED, reviewDTO.getComments());
    	}

    	if (this.propertyProvider.getBoolean(AppPropertyName.FeatureFacilityAutomatedEmailEnabled)) {
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
				        		  sltConfig.getSltEmail());
		    		  }
		    	  });
		      });
    	}
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
