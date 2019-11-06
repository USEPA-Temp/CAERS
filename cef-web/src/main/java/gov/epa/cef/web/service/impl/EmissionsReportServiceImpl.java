package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.client.soap.DocumentDataSource;
import gov.epa.cef.web.client.soap.SignatureServiceClient;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilityCategoryCode;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsProcessBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsUnitBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.OperatingDetailBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointApptBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReportingPeriodBulkUploadDto;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.AircraftEngineTypeCodeRepository;
import gov.epa.cef.web.repository.CalculationMaterialCodeRepository;
import gov.epa.cef.web.repository.CalculationMethodCodeRepository;
import gov.epa.cef.web.repository.CalculationParameterTypeCodeRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.OperatingStatusCodeRepository;
import gov.epa.cef.web.repository.PollutantRepository;
import gov.epa.cef.web.repository.FacilityCategoryCodeRepository;
import gov.epa.cef.web.repository.FacilitySourceTypeCodeRepository;
import gov.epa.cef.web.repository.ReleasePointTypeCodeRepository;
import gov.epa.cef.web.repository.ReportingPeriodCodeRepository;
import gov.epa.cef.web.repository.UnitTypeCodeRepository;
import gov.epa.cef.web.repository.ProgramSystemCodeRepository;
import gov.epa.cef.web.repository.TribalCodeRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.repository.NaicsCodeRepository;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.mapper.BulkUploadMapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
    private AircraftEngineTypeCodeRepository aircraftEngineRepo;

    @Autowired
    private CalculationMaterialCodeRepository calcMaterialCodeRepo;

    @Autowired
    private CalculationMethodCodeRepository calcMethodCodeRepo;

    @Autowired
    private CalculationParameterTypeCodeRepository calcParamTypeCodeRepo;

    @Autowired
    private OperatingStatusCodeRepository operatingStatusRepo;

    @Autowired
    private FacilityCategoryCodeRepository facilityCategoryRepo;

    @Autowired
    private FacilitySourceTypeCodeRepository facilitySourceTypeRepo;

    @Autowired
    private ReleasePointTypeCodeRepository releasePointTypeRepo;

    @Autowired
    private ReportingPeriodCodeRepository reportingPeriodCodeRepo;

    @Autowired
    private UnitTypeCodeRepository unitTypeRepo;

    @Autowired
    private ProgramSystemCodeRepository programSystemCodeRepo;

    @Autowired
    private PollutantRepository pollutantRepo;

    @Autowired
    private TribalCodeRepository tribalCodeRepo;

    @Autowired
    private UnitMeasureCodeRepository unitMeasureCodeRepo;

    @Autowired
    private NaicsCodeRepository naicsCodeRepo;

    @Autowired
    private EmissionsReportMapper emissionsReportMapper;

    @Autowired
    private BulkUploadMapper uploadMapper;

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
     * Save the emissions report to the database.
     * @param bulkEmissionsReport
     * @return
     */
    @Override
    public EmissionsReportDto saveBulkEmissionReport(EmissionsReportBulkUploadDto bulkEmissionsReport) {
        EmissionsReport emissionsReport = MapEmissionsReport(bulkEmissionsReport);

        for (FacilitySiteBulkUploadDto bulkFacility : bulkEmissionsReport.getFacilitySites()) {
            FacilitySite facility = MapFacility(bulkFacility);
            
            Map<Long, ReleasePoint> releasePointMap = new HashMap<>();
            Map<Long, EmissionsProcess> processMap = new HashMap<>();

            for (ReleasePointBulkUploadDto bulkRp : bulkEmissionsReport.getReleasePoints()) {
                ReleasePoint releasePoint = MapReleasePoint(bulkRp);

                if (bulkRp.getFacilitySiteId().equals(bulkFacility.getId())) {
                    releasePoint.setFacilitySite(facility);
                    facility.getReleasePoints().add(releasePoint);
                    releasePointMap.put(bulkRp.getId(), releasePoint);
                }
            }

            for (EmissionsUnitBulkUploadDto bulkEmissionsUnit : bulkEmissionsReport.getEmissionsUnits()) {

                if (bulkEmissionsUnit.getFacilitySiteId().equals(bulkFacility.getId())) {
                    EmissionsUnit emissionsUnit = MapEmissionsUnit(bulkEmissionsUnit);
                    emissionsUnit.setFacilitySite(facility);

                    List<EmissionsProcess> processes = bulkEmissionsReport.getEmissionsProcesses().stream()
                            .filter(p -> bulkEmissionsUnit.getId().equals(p.getEmissionsUnitId()))
                            .map(bulkProcess -> {
                                EmissionsProcess process = mapEmissionsProcess(bulkProcess);

                                Set<ReportingPeriod> periods = bulkEmissionsReport.getReportingPeriods().stream()
                                        .filter(rp -> bulkProcess.getId().equals(rp.getEmissionsProcessId()))
                                        .map(bulkPeriod -> {
                                            ReportingPeriod period = mapReportingPeriod(bulkPeriod);

                                            Set<OperatingDetail> details = bulkEmissionsReport.getOperatingDetails().stream()
                                                    .filter(od -> bulkPeriod.getId().equals(od.getReportingPeriodId()))
                                                    .map(bulkDetail -> {
                                                        OperatingDetail detail = mapOperatingDetail(bulkDetail);
                                                        detail.setReportingPeriod(period);

                                                        return detail;
                                                    }).collect(Collectors.toSet());

                                            Set<Emission> emissions = bulkEmissionsReport.getEmissions().stream()
                                                    .filter(e -> bulkPeriod.getId().equals(e.getReportingPeriodId()))
                                                    .map(bulkEmission -> {
                                                        Emission emission = mapEmission(bulkEmission);
                                                        emission.setReportingPeriod(period);

                                                        return emission;
                                                    }).collect(Collectors.toSet());

                                            period.setEmissionsProcess(process);
                                            period.setEmissions(emissions);
                                            period.setOperatingDetails(details);

                                            return period;
                                        }).collect(Collectors.toSet());

                                process.setEmissionsUnit(emissionsUnit);
                                process.setReportingPeriods(periods);

                                processMap.put(bulkProcess.getId(), process);

                                return process;
                            }).collect(Collectors.toList());

                    emissionsUnit.setEmissionsProcesses(processes);

                    facility.getEmissionsUnits().add(emissionsUnit);
                }
            }

            bulkEmissionsReport.getReleasePointAppts().forEach(bulkRpAppt -> {
                ReleasePoint rp = releasePointMap.get(bulkRpAppt.getReleasePointId());
                EmissionsProcess ep = processMap.get(bulkRpAppt.getEmissionProcessId());
                if (rp != null && ep != null) {
                    ReleasePointAppt rpAppt = mapReleasePointAppt(bulkRpAppt);
                    rpAppt.setReleasePoint(rp);
                    rpAppt.setEmissionsProcess(ep);
                    rp.getReleasePointAppts().add(rpAppt);
                    ep.getReleasePointAppts().add(rpAppt);
                }
            });

            facility.setEmissionsReport(emissionsReport);
            emissionsReport.getFacilitySites().add(facility);
        }


	    EmissionsReport savedReport = erRepo.save(emissionsReport);
	    return emissionsReportMapper.toDto(savedReport);
    }

    /**
     * Testing method for generating upload JSON for a report
     * @param reportId
     * @return
     */
    public EmissionsReportBulkUploadDto generateBulkUploadDto(Long reportId) {

        EmissionsReport report = erRepo.findById(reportId).orElse(null);
        List<FacilitySite> facilitySites = report.getFacilitySites();
        List<EmissionsUnit> units = facilitySites.stream()
                .flatMap(f -> f.getEmissionsUnits().stream())
                .collect(Collectors.toList());
        List<EmissionsProcess> processes = units.stream()
                .flatMap(u -> u.getEmissionsProcesses().stream())
                .collect(Collectors.toList());
        List<ReportingPeriod> periods = processes.stream()
                .flatMap(p -> p.getReportingPeriods().stream())
                .collect(Collectors.toList());
        List<OperatingDetail> operatingDetails = periods.stream()
                .flatMap(p -> p.getOperatingDetails().stream())
                .collect(Collectors.toList());
        List<Emission> emissions = periods.stream()
                .flatMap(p -> p.getEmissions().stream())
                .collect(Collectors.toList());
        List<ReleasePoint> releasePoints = facilitySites.stream()
                .flatMap(f -> f.getReleasePoints().stream())
                .collect(Collectors.toList());
        List<ReleasePointAppt> releasePointAppts = releasePoints.stream()
                .flatMap(r -> r.getReleasePointAppts().stream())
                .collect(Collectors.toList());

        EmissionsReportBulkUploadDto reportDto = uploadMapper.emissionsReportToDto(report);
        reportDto.setFacilitySites(uploadMapper.facilitySiteToDtoList(facilitySites));
        reportDto.setEmissionsUnits(uploadMapper.emissionsUnitToDtoList(units));
        reportDto.setEmissionsProcesses(uploadMapper.emissionsProcessToDtoList(processes));
        reportDto.setReportingPeriods(uploadMapper.reportingPeriodToDtoList(periods));
        reportDto.setOperatingDetails(uploadMapper.operatingDetailToDtoList(operatingDetails));
        reportDto.setEmissions(uploadMapper.emissionToDtoList(emissions));
        reportDto.setReleasePoints(uploadMapper.releasePointToDtoList(releasePoints));
        reportDto.setReleasePointAppts(uploadMapper.releasePointApptToDtoList(releasePointAppts));

        return reportDto;
    }
    
    
    /**
     * Delete an emissons report for a given id
     * @param id
     */
    public void delete(Long id) {
    	erRepo.deleteById(id);
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
     * Reject the specified reports. Sets report status to in progress and validation status to unvalidated.
     * @param reportIds
     * @return
     */
    public List<EmissionsReportDto> rejectEmissionsReports(List<Long> reportIds) {
    	return updateEmissionsReportsStatus(reportIds, ReportStatus.IN_PROGRESS, ValidationStatus.UNVALIDATED);
    }

    /**
     * Update the status of the specified reports
     * @param reportIds
     * @param status
     * @param validationStatus
     * @return
     */
    private List<EmissionsReportDto> updateEmissionsReportsStatus(List<Long> reportIds, ReportStatus status, ValidationStatus validationStatus) {

        return StreamSupport.stream(this.erRepo.findAllById(reportIds).spliterator(), false)
            .map(report -> {
                report.setStatus(status);
                if(validationStatus != null){
                	report.setValidationStatus(validationStatus);
                }
                return this.emissionsReportMapper.toDto(this.erRepo.save(report));
            }).collect(Collectors.toList());

    }
    
    /**
     * Update the status of the specified reports
     * @param reportIds
     * @param status
     * @return
     */
    private List<EmissionsReportDto> updateEmissionsReportsStatus(List<Long> reportIds, ReportStatus status) {
    	return updateEmissionsReportsStatus(reportIds, status, null);
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

    /**
     * Map an EmissionsProcessBulkUploadDto to an EmissionsProcess domain model
     */
    private EmissionsProcess mapEmissionsProcess(EmissionsProcessBulkUploadDto dto) {
        EmissionsProcess result = uploadMapper.emissionsProcessFromDto(dto);

        if (dto.getAircraftEngineTypeCode() != null) {
            result.setAircraftEngineTypeCode(aircraftEngineRepo.findById(dto.getAircraftEngineTypeCode()).orElse(null));
        }

        if (dto.getOperatingStatusCode() != null) {
            result.setOperatingStatusCode(operatingStatusRepo.findById(dto.getOperatingStatusCode()).orElse(null));
        }

        return result;
    }

    /**
     * Map an ReportingPeriodBulkUploadDto to an ReportingPeriod domain model
     */
    private ReportingPeriod mapReportingPeriod(ReportingPeriodBulkUploadDto dto) {
        ReportingPeriod result = uploadMapper.reportingPeriodFromDto(dto);

        if(dto.getCalculationMaterialCode() != null) {
            result.setCalculationMaterialCode(calcMaterialCodeRepo.findById(dto.getCalculationMaterialCode()).orElse(null));
        }
        if (dto.getCalculationParameterTypeCode() != null) {
            result.setCalculationParameterTypeCode(calcParamTypeCodeRepo.findById(dto.getCalculationParameterTypeCode()).orElse(null));
        }
        if (dto.getCalculationParameterUom() != null) {
            result.setCalculationParameterUom(unitMeasureCodeRepo.findById(dto.getCalculationParameterUom()).orElse(null));
        }
        if (dto.getEmissionsOperatingTypeCode() != null) {
            result.setEmissionsOperatingTypeCode(operatingStatusRepo.findById(dto.getEmissionsOperatingTypeCode()).orElse(null));
        }
        if (dto.getReportingPeriodTypeCode() != null) {
            result.setReportingPeriodTypeCode(reportingPeriodCodeRepo.findById(dto.getReportingPeriodTypeCode()).orElse(null));
        }

        return result;
    }

    /**
     * Map an OperatingDetailBulkUploadDto to an OperatingDetail domain model
     */
    private OperatingDetail mapOperatingDetail(OperatingDetailBulkUploadDto dto) {
        OperatingDetail result = uploadMapper.operatingDetailFromDto(dto);

        return result;
    }

    /**
     * Map an OperatingDetailBulkUploadDto to an OperatingDetail domain model
     */
    private Emission mapEmission(EmissionBulkUploadDto dto) {
        Emission result = uploadMapper.emissionsFromDto(dto);

        if (dto.getEmissionsCalcMethodCode() != null) {
            result.setEmissionsCalcMethodCode(calcMethodCodeRepo.findById(dto.getEmissionsCalcMethodCode()).orElse(null));
        }
        if (dto.getEmissionsUomCode() != null) {
            result.setEmissionsUomCode(unitMeasureCodeRepo.findById(dto.getEmissionsUomCode()).orElse(null));
        }
        if (dto.getEmissionsNumeratorUom() != null) {
            result.setEmissionsNumeratorUom(unitMeasureCodeRepo.findById(dto.getEmissionsNumeratorUom()).orElse(null));
        }
        if (dto.getEmissionsDenominatorUom() != null) {
            result.setEmissionsDenominatorUom(unitMeasureCodeRepo.findById(dto.getEmissionsDenominatorUom()).orElse(null));
        }
        if (dto.getPollutantCode() != null) {
            result.setPollutant(pollutantRepo.findById(dto.getPollutantCode()).orElse(null));
        }

        return result;
    }

    /**
     * Map an ReleasePointApptBulkUploadDto to an OperatingDetail domain model
     */
    private ReleasePointAppt mapReleasePointAppt(ReleasePointApptBulkUploadDto dto) {
        ReleasePointAppt result = uploadMapper.releasePointApptFromDto(dto);

        return result;
    }

}
