package gov.epa.cef.web.service.impl;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import gov.epa.cef.web.client.api.ExcelParserClient;
import gov.epa.cef.web.client.api.ExcelParserResponse;
import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.ControlPathPollutant;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFormulaVariable;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.exception.BulkReportValidationException;
import gov.epa.cef.web.exception.CalculationException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.AircraftEngineTypeCodeRepository;
import gov.epa.cef.web.repository.CalculationMaterialCodeRepository;
import gov.epa.cef.web.repository.CalculationMethodCodeRepository;
import gov.epa.cef.web.repository.CalculationParameterTypeCodeRepository;
import gov.epa.cef.web.repository.ContactTypeCodeRepository;
import gov.epa.cef.web.repository.ControlMeasureCodeRepository;
import gov.epa.cef.web.repository.EmissionFormulaVariableCodeRepository;
import gov.epa.cef.web.repository.EmissionsOperatingTypeCodeRepository;
import gov.epa.cef.web.repository.FacilityCategoryCodeRepository;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.repository.FacilitySourceTypeCodeRepository;
import gov.epa.cef.web.repository.FipsCountyRepository;
import gov.epa.cef.web.repository.FipsStateCodeRepository;
import gov.epa.cef.web.repository.MasterFacilityRecordRepository;
import gov.epa.cef.web.repository.NaicsCodeRepository;
import gov.epa.cef.web.repository.OperatingStatusCodeRepository;
import gov.epa.cef.web.repository.PollutantRepository;
import gov.epa.cef.web.repository.ProgramSystemCodeRepository;
import gov.epa.cef.web.repository.ReleasePointTypeCodeRepository;
import gov.epa.cef.web.repository.ReportingPeriodCodeRepository;
import gov.epa.cef.web.repository.TribalCodeRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.repository.UnitTypeCodeRepository;
import gov.epa.cef.web.service.BulkUploadService;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EmissionsReportStarterDto;
import gov.epa.cef.web.service.dto.bulkUpload.BlankToNullModule;
import gov.epa.cef.web.service.dto.bulkUpload.ControlAssignmentBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPathBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPathPollutantBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPollutantBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionFormulaVariableBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsProcessBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsUnitBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilityNAICSBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteContactBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.OperatingDetailBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointApptBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReportingPeriodBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetError;
import gov.epa.cef.web.service.mapper.BulkUploadMapper;
import gov.epa.cef.web.service.mapper.MasterFacilityRecordMapper;
import gov.epa.cef.web.util.CalculationUtils;
import gov.epa.cef.web.util.MassUomConversion;
import gov.epa.cef.web.util.TempFile;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BulkUploadServiceImpl implements BulkUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AircraftEngineTypeCodeRepository aircraftEngineRepo;

    @Autowired
    private CalculationMaterialCodeRepository calcMaterialCodeRepo;

    @Autowired
    private CalculationMethodCodeRepository calcMethodCodeRepo;

    @Autowired
    private CalculationParameterTypeCodeRepository calcParamTypeCodeRepo;

    @Autowired
    private ContactTypeCodeRepository contactTypeRepo;

    @Autowired
    private ControlMeasureCodeRepository controlMeasureCodeRepo;

    @Autowired
    private FipsCountyRepository countyRepo;

    @Autowired
    private EmissionFormulaVariableCodeRepository emissionFormulaVariableCodeRepo;

    @Autowired
    private EmissionsOperatingTypeCodeRepository emissionsOperatingTypeCodeRepo;

    @Autowired
    private EmissionsReportService emissionsReportService;

    @Autowired
    private ExcelParserClient excelParserClient;

    @Autowired
    private FacilityCategoryCodeRepository facilityCategoryRepo;

    @Autowired
    private FacilitySourceTypeCodeRepository facilitySourceTypeRepo;

    @Autowired
    private MasterFacilityRecordMapper mfrMapper;

    @Autowired
    private MasterFacilityRecordRepository mfrRepo;

    @Autowired
    private NaicsCodeRepository naicsCodeRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OperatingStatusCodeRepository operatingStatusRepo;

    @Autowired
    private PollutantRepository pollutantRepo;

    @Autowired
    private ProgramSystemCodeRepository programSystemCodeRepo;

    @Autowired
    private ReleasePointTypeCodeRepository releasePointTypeRepo;

    @Autowired
    private ReportingPeriodCodeRepository reportingPeriodCodeRepo;

    @Autowired
    private FipsStateCodeRepository stateCodeRepo;

    @Autowired
    private TribalCodeRepository tribalCodeRepo;

    @Autowired
    private UnitMeasureCodeRepository unitMeasureCodeRepo;

    @Autowired
    private UnitTypeCodeRepository unitTypeRepo;

    @Autowired
    private BulkUploadMapper uploadMapper;

    @Autowired
    private BulkReportValidator validator;
    
    @Autowired
    private FacilitySiteRepository facilitySiteRepo;

    @Override
    public Function<JsonNode, EmissionsReportBulkUploadDto> parseJsonNode(boolean failUnknownProperties) {

        return new JsonNodeToBulkUploadDto(this.objectMapper, failUnknownProperties);
    }

    /**
     * Save the emissions report to the database.
     *
     * @param bulkEmissionsReport
     * @return
     */
    @Override
    public EmissionsReportDto saveBulkEmissionsReport(EmissionsReportBulkUploadDto bulkEmissionsReport) {

        EmissionsReport emissionsReport = toEmissionsReport().apply(bulkEmissionsReport);
        
        // if a previous report already exists, then update that existing report with the data from this uploaded file
        // and reset the validation, report, and CROMERR status for the report
        Optional<EmissionsReport> previousReport = this.emissionsReportService.retrieveByMasterFacilityRecordIdAndYear(bulkEmissionsReport.getMasterFacilityRecordId(), 
                                                                                                                       bulkEmissionsReport.getYear());
        if (previousReport.isPresent()) {
        	
        	//remove any previous facilitySites from the report (the main report data)
        	//should be only one facility site, but putting in a loop for security
        	List<FacilitySite> oldSites = previousReport.get().getFacilitySites();
        	for (FacilitySite oldSite : oldSites) {
        		facilitySiteRepo.deleteById(oldSite.getId());
        	}
        	
        	//add in the new facility site from the uploaded report to the old report
        	EmissionsReport reportToUpdate = previousReport.get();
        	reportToUpdate.getFacilitySites().clear();
        	List<FacilitySite> newFacilitySites = emissionsReport.getFacilitySites();
        	for (FacilitySite newSite : newFacilitySites) {
        		newSite.setEmissionsReport(reportToUpdate);
        		reportToUpdate.getFacilitySites().add(newSite);
        	}

        	//update the report metadata to make sure it's reset since the report has been recreated
        	if (ReportStatus.RETURNED == reportToUpdate.getStatus()) {
        	    reportToUpdate.setStatus(ReportStatus.RETURNED);
        	} else {
        	    reportToUpdate.setStatus(ReportStatus.IN_PROGRESS);
        	}
            reportToUpdate.setValidationStatus(ValidationStatus.UNVALIDATED);
            reportToUpdate.setEisLastSubmissionStatus(EisSubmissionStatus.NotStarted);
        	reportToUpdate.setCromerrActivityId(null);
        	reportToUpdate.setCromerrDocumentId(null);
        	return this.emissionsReportService.saveAndAuditEmissionsReport(reportToUpdate, ReportAction.UPLOADED);
        }
        //otherwise, just add the entire report from the excel upload into the system
        else {
        	return this.emissionsReportService.saveAndAuditEmissionsReport(emissionsReport, ReportAction.UPLOADED);
        }
    }

    public EmissionsReportDto saveBulkWorkbook(EmissionsReportStarterDto metadata, TempFile workbook) {

        EmissionsReportDto result = null;

        ExcelParserResponse response = this.excelParserClient.parseWorkbook(workbook);

        if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {

            logger.info("ExcelJsonParser Result {}", response.getJson());

            EmissionsReportBulkUploadDto bulkEmissionsReport = parseWorkbookJson(response, metadata);

            this.validator.validate(bulkEmissionsReport);

            try {

                result = saveBulkEmissionsReport(bulkEmissionsReport);

            } catch (Exception e) {

                String msg = e.getMessage()
                    .replaceAll(EmissionsReportBulkUploadDto.class.getPackage().getName().concat("."), "")
                    .replaceAll(EmissionsReport.class.getPackage().getName().concat("."), "");

                WorksheetError violation = WorksheetError.createSystemError(msg);

                throw new BulkReportValidationException(Collections.singletonList(violation));
            }

        } else {

            List<WorksheetError> errors = new ArrayList<>();
            errors.add(WorksheetError.createSystemError("Unable to read workbook."));
            if (response.getJson() != null && response.getJson().hasNonNull("message")) {

                errors.add(WorksheetError.createSystemError(response.getJson().path("message").asText()));
            }

            throw new BulkReportValidationException(errors);
        }

        return result;
    }

    protected Function<EmissionsReportBulkUploadDto, EmissionsReport> toEmissionsReport() {

        return bulkEmissionsReport -> {

            Collection<String> warnings = new ArrayList<>();

            EmissionsReport emissionsReport = mapEmissionsReport(bulkEmissionsReport);
            
            MasterFacilityRecord mfr = mfrRepo.findByEisProgramIdAndAgencyFacilityId(bulkEmissionsReport.getEisProgramId(), bulkEmissionsReport.getAltSiteIdentifier()).orElse(null);

            for (FacilitySiteBulkUploadDto bulkFacility : bulkEmissionsReport.getFacilitySites()) {
                FacilitySite facility = mapFacility(bulkFacility);
                
                facility.setName(mfr.getName());
                facility.setFacilitySourceTypeCode(mfr.getFacilitySourceTypeCode());
                facility.setLongitude(mfr.getLongitude());
                facility.setLatitude(mfr.getLatitude());
                facility.setStreetAddress(mfr.getStreetAddress());
                facility.setCity(mfr.getCity());
                facility.setStateCode(mfr.getStateCode());
                facility.setPostalCode(mfr.getPostalCode());
                facility.setCountyCode(mfr.getCountyCode());

                Preconditions.checkArgument(bulkFacility.getId() != null,
                    "FacilitySite ID can not be null.");

                // Maps for storing the entity referenced by a certain id in the JSON
                Map<Long, ReleasePoint> releasePointMap = new HashMap<>();
                Map<Long, EmissionsProcess> processMap = new HashMap<>();
                Map<Long, ControlPath> controlPathMap = new HashMap<>();
                Map<Long, Control> controlMap = new HashMap<>();

                // Map Facility Contacts
                for (FacilitySiteContactBulkUploadDto bulkFacilityContact : bulkEmissionsReport.getFacilityContacts()) {
                    FacilitySiteContact facilityContact = mapFacilityContact(bulkFacilityContact);

                    if (bulkFacility.getId().equals(bulkFacilityContact.getFacilitySiteId())) {
                        facilityContact.setFacilitySite(facility);
                        facility.getContacts().add(facilityContact);
                    }
                }

                // Map Facility NAICS
                for (FacilityNAICSBulkUploadDto bulkFacilityNAICS : bulkEmissionsReport.getFacilityNAICS()) {
                    FacilityNAICSXref facilityNAICS = mapFacilityNAICS(bulkFacilityNAICS);

                    if (bulkFacility.getId().equals(bulkFacilityNAICS.getFacilitySiteId())) {
                        facilityNAICS.setFacilitySite(facility);
                        facility.getFacilityNAICS().add(facilityNAICS);
                    }
                }

                // Map Release Points
                for (ReleasePointBulkUploadDto bulkRp : bulkEmissionsReport.getReleasePoints()) {
                    ReleasePoint releasePoint = mapReleasePoint(bulkRp);

                    if (bulkFacility.getId().equals(bulkRp.getFacilitySiteId())) {
                        releasePoint.setFacilitySite(facility);
                        facility.getReleasePoints().add(releasePoint);
                        releasePointMap.put(bulkRp.getId(), releasePoint);
                    }
                }

                // Map Emissions Units
                for (EmissionsUnitBulkUploadDto bulkEmissionsUnit : bulkEmissionsReport.getEmissionsUnits()) {

                    if (bulkFacility.getId().equals(bulkEmissionsUnit.getFacilitySiteId())) {

                        EmissionsUnit emissionsUnit = mapEmissionsUnit(bulkEmissionsUnit);
                        emissionsUnit.setFacilitySite(facility);

                        // Map Emissions Processes
                        List<EmissionsProcess> processes = bulkEmissionsReport.getEmissionsProcesses().stream()
                            .filter(p -> bulkEmissionsUnit.getId().equals(p.getEmissionsUnitId()))
                            .map(bulkProcess -> {
                                EmissionsProcess process = mapEmissionsProcess(bulkProcess);

                                // Map Reporting Periods
                                List<ReportingPeriod> periods = bulkEmissionsReport.getReportingPeriods().stream()
                                    .filter(rp -> bulkProcess.getId().equals(rp.getEmissionsProcessId()))
                                    .map(bulkPeriod -> {
                                        ReportingPeriod period = mapReportingPeriod(bulkPeriod);

                                        // Map Operating Details, should only be 1
                                        List<OperatingDetail> details = bulkEmissionsReport.getOperatingDetails().stream()
                                            .filter(od -> bulkPeriod.getId().equals(od.getReportingPeriodId()))
                                            .map(bulkDetail -> {
                                                OperatingDetail detail = mapOperatingDetail(bulkDetail);
                                                detail.setReportingPeriod(period);

                                                return detail;
                                            }).collect(Collectors.toList());

                                        // Map Emissions
                                        List<Emission> emissions = bulkEmissionsReport.getEmissions().stream()
                                            .filter(e -> bulkPeriod.getId().equals(e.getReportingPeriodId()))
                                            .map(bulkEmission -> {
                                                Emission emission = mapEmission(bulkEmission);

                                                List<EmissionFormulaVariable> variables = bulkEmissionsReport.getEmissionFormulaVariables().stream()
                                                    .filter(efv -> bulkEmission.getId().equals(efv.getEmissionId()))
                                                    .map(bulkVariable -> {
                                                        EmissionFormulaVariable variable = mapEmissionFormulaVariable(bulkVariable);
                                                        variable.setEmission(emission);

                                                        return variable;
                                                    }).collect(Collectors.toList());

                                                emission.setReportingPeriod(period);
                                                emission.setVariables(variables);

                                                if (Boolean.TRUE.equals(emission.getFormulaIndicator()) && !emission.getVariables().isEmpty()) {
                                                    try {
                                                        emission.setEmissionsFactor(CalculationUtils.calculateEmissionFormula(emission.getEmissionsFactorFormula(), emission.getVariables()));
                                                    } catch (CalculationException e) {
                                                        // TODO: handle exception
                                                    }
                                                }

                                                return emission;
                                            }).collect(Collectors.toList());

                                        period.setEmissionsProcess(process);
                                        period.setEmissions(emissions);
                                        period.setOperatingDetails(details);

                                        return period;
                                    }).collect(Collectors.toList());

                                process.setEmissionsUnit(emissionsUnit);
                                process.setReportingPeriods(periods);

                                processMap.put(bulkProcess.getId(), process);

                                return process;
                            }).collect(Collectors.toList());

                        emissionsUnit.setEmissionsProcesses(processes);

                        facility.getEmissionsUnits().add(emissionsUnit);

                    }
                }

                // Map Control Paths
                List<ControlPath> controlPaths = bulkEmissionsReport.getControlPaths().stream()
                    .filter(c -> bulkFacility.getId().equals(c.getFacilitySiteId()))
                    .map(bulkControlPath -> {
                        ControlPath path = mapControlPath(bulkControlPath);
                        path.setFacilitySite(facility);
                        
                        // Map Control Path Pollutants
                        List<ControlPathPollutant> controlPathPollutants = bulkEmissionsReport.getControlPathPollutants().stream()
                            .filter(cp -> bulkControlPath.getId().equals(cp.getControlPathId()))
                            .map(bulkControlPathPollutant -> {
                            	
                                ControlPathPollutant controlPathPollutant = mapControlPathPollutant(bulkControlPathPollutant);
                                controlPathPollutant.setControlPath(path);

                                return controlPathPollutant;
                            }).collect(Collectors.toList());

                        path.setPollutants(controlPathPollutants);

                        controlPathMap.put(bulkControlPath.getId(), path);

                        return path;
                    }).collect(Collectors.toList());

                facility.setControlPaths(controlPaths);

                // Map Controls
                List<Control> controls = bulkEmissionsReport.getControls().stream()
                    .filter(c -> bulkFacility.getId().equals(c.getFacilitySiteId()))
                    .map(bulkControl -> {
                        Control control = mapControl(bulkControl);
                        control.setFacilitySite(facility);

                        // Map Control Pollutants
                        List<ControlPollutant> controlPollutants = bulkEmissionsReport.getControlPollutants().stream()
                            .filter(c -> bulkControl.getId().equals(c.getControlId()))
                            .map(bulkControlPollutant -> {
                                ControlPollutant controlPollutant = mapControlPollutant(bulkControlPollutant);
                                controlPollutant.setControl(control);

                                return controlPollutant;
                            }).collect(Collectors.toList());

                        control.setPollutants(controlPollutants);

                        controlMap.put(bulkControl.getId(), control);

                        return control;
                    }).collect(Collectors.toList());

                facility.setControls(controls);

                // Map Control Assignments into controls and control paths
                bulkEmissionsReport.getControlAssignments().forEach(bulkControlAssignment -> {
                    ControlAssignment controlAssignment = mapControlAssignment(bulkControlAssignment);

                    ControlPath controlPath = controlPathMap.get(bulkControlAssignment.getControlPathId());
                    if (controlPath != null) {
                        controlAssignment.setControlPath(controlPath);
                        controlPath.getAssignments().add(controlAssignment);
                    } else {

                        warnings.add(String.format("ControlPath %s referenced by assigned %s does not exist.",
                            bulkControlAssignment.getControlPathId(), bulkControlAssignment.getId()));
                    }

                    ControlPath controlPathChild = controlPathMap.get(bulkControlAssignment.getControlPathChildId());
                    if (controlPathChild != null) {
                        controlAssignment.setControlPathChild(controlPathChild);
                        controlPathChild.getChildAssignments().add(controlAssignment);
                    } else {

                        warnings.add(String.format("ControlPath %s referenced by assigned %s does not exist.",
                            bulkControlAssignment.getControlPathChildId(), bulkControlAssignment.getId()));
                    }

                    Control control = controlMap.get(bulkControlAssignment.getControlId());
                    if (control != null) {
                        controlAssignment.setControl(control);
                        control.getAssignments().add(controlAssignment);
                    } else {

                        warnings.add(String.format("Control %s referenced by assigned %s does not exist.",
                            bulkControlAssignment.getControlId(), bulkControlAssignment.getId()));
                    }

                });

                // Map Release Point Apportionments into release points and emissions processes, along with control paths
                bulkEmissionsReport.getReleasePointAppts().forEach(bulkRpAppt -> {
                    ReleasePoint rp = releasePointMap.get(bulkRpAppt.getReleasePointId());
                    EmissionsProcess ep = processMap.get(bulkRpAppt.getEmissionProcessId());
                    if (rp != null && ep != null) {
                        ReleasePointAppt rpAppt = mapReleasePointAppt(bulkRpAppt);
                        rpAppt.setReleasePoint(rp);
                        rpAppt.setEmissionsProcess(ep);

                        if (bulkRpAppt.getControlPathId() != null) {
                            ControlPath controlPath = controlPathMap.get(bulkRpAppt.getControlPathId());

                            if (controlPath != null) {
                                rpAppt.setControlPath(controlPath);
                                controlPath.getReleasePointAppts().add(rpAppt);
                            }
                        }

                        rp.getReleasePointAppts().add(rpAppt);
                        ep.getReleasePointAppts().add(rpAppt);
                    }
                });

                facility.setEmissionsReport(emissionsReport);
                emissionsReport.getFacilitySites().add(facility);
            }

            logger.debug("Warnings {}", warnings);

            return emissionsReport;
        };
    }

    /**
     * Calculate the total emissions in Tons for an Emission
     *
     * @param emission
     * @return
     */
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
     * Map an ControlBulkUploadDto to an Control domain model
     */
    private Control mapControl(ControlBulkUploadDto dto) {

        Control result = uploadMapper.controlFromDto(dto);

        if (dto.getOperatingStatusCode() != null) {
            result.setOperatingStatusCode(operatingStatusRepo.findById(dto.getOperatingStatusCode()).orElse(null));
        }
        if (dto.getStatusYear() != null) {
        	result.setStatusYear(toShort(dto.getStatusYear()));
        }
        if (dto.getControlMeasureCode() != null) {
            result.setControlMeasureCode(controlMeasureCodeRepo.findById(dto.getControlMeasureCode()).orElse(null));
        }
        // Percent capture has been removed for control device per CEF-984.
        if (dto.getPercentCapture() != null) {
        	result.setPercentCapture(null);
        }

        return result;
    }

    /**
     * Map an ControlAssignmentBulkUploadDto to an ControlAssignment domain model
     */
    private ControlAssignment mapControlAssignment(ControlAssignmentBulkUploadDto dto) {

        ControlAssignment result = uploadMapper.controlAssignmentFromDto(dto);

        return result;
    }

    /**
     * Map an ControlPathBulkUploadDto to an ControlPath domain model
     */
    private ControlPath mapControlPath(ControlPathBulkUploadDto dto) {

        ControlPath result = uploadMapper.controlPathFromDto(dto);

        return result;
    }

    /**
     * Map an ControlPollutantBulkUploadDto to an ControlPollutant domain model
     */
    private ControlPollutant mapControlPollutant(ControlPollutantBulkUploadDto dto) {

        ControlPollutant result = uploadMapper.controlPollutantFromDto(dto);

        if (dto.getPollutantCode() != null) {
            result.setPollutant(pollutantRepo.findById(dto.getPollutantCode()).orElse(null));
        }

        return result;
    }
    
    /**
     * Map an ControlPathPollutantBulkUploadDto to an ControlPathPollutant domain model
     */
    private ControlPathPollutant mapControlPathPollutant(ControlPathPollutantBulkUploadDto dto) {

    	ControlPathPollutant result = uploadMapper.controlPathPollutantFromDto(dto);

        if (dto.getPollutantCode() != null) {
            result.setPollutant(pollutantRepo.findById(dto.getPollutantCode()).orElse(null));
        }

        return result;
    }

    /**
     * Map an OperatingDetailBulkUploadDto to an OperatingDetail domain model
     */
    private Emission mapEmission(EmissionBulkUploadDto dto) {

        Emission result = uploadMapper.emissionsFromDto(dto);

        result.setFormulaIndicator(Strings.emptyToNull(dto.getEmissionsFactorFormula()) != null);

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

        if (result.getEmissionsUomCode() != null && result.getTotalEmissions() != null) {
            result.setCalculatedEmissionsTons(calculateEmissionTons(result));
        }

        result.setTotalEmissions(CalculationUtils.setSignificantFigures(result.getTotalEmissions(), CalculationUtils.EMISSIONS_PRECISION));

        return result;
    }

    private EmissionFormulaVariable mapEmissionFormulaVariable(EmissionFormulaVariableBulkUploadDto dto) {

        EmissionFormulaVariable result = new EmissionFormulaVariable();
        result.setValue(toBigDecimal(dto.getValue()));

        if (dto.getEmissionFormulaVariableCode() != null) {
            result.setVariableCode(emissionFormulaVariableCodeRepo.findById(dto.getEmissionFormulaVariableCode()).orElse(null));
        }

        return result;
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
     * Map an EmissionsReportBulkUploadDto to an EmissionsReport domain model
     */
    private EmissionsReport mapEmissionsReport(EmissionsReportBulkUploadDto bulkEmissionsReport) {

        EmissionsReport emissionsReport = new EmissionsReport();

        emissionsReport.setEisProgramId(bulkEmissionsReport.getEisProgramId());
        emissionsReport.setYear(bulkEmissionsReport.getYear());
        emissionsReport.setStatus(ReportStatus.valueOf(bulkEmissionsReport.getStatus()));
        emissionsReport.setEisLastSubmissionStatus(EisSubmissionStatus.NotStarted);

        if (bulkEmissionsReport.getProgramSystemCode() != null) {
            emissionsReport.setProgramSystemCode(programSystemCodeRepo.findById(bulkEmissionsReport.getProgramSystemCode()).orElse(null));
        }
        if (bulkEmissionsReport.getValidationStatus() != null) {
            emissionsReport.setValidationStatus(ValidationStatus.valueOf(bulkEmissionsReport.getValidationStatus()));
        }

        emissionsReport.setMasterFacilityRecord(findMasterFacilityRecord(bulkEmissionsReport));


        return emissionsReport;
    }


    /**
     * Find the master facility record associated with the bulk upload emissions report
     */
    private MasterFacilityRecord findMasterFacilityRecord(EmissionsReportBulkUploadDto bulkEmissionsReport) {
        Optional<MasterFacilityRecord> mfr = null;

        //check based on the master facility record id
        if (bulkEmissionsReport.getMasterFacilityRecordId() != null) {
            mfr = mfrRepo.findById(bulkEmissionsReport.getMasterFacilityRecordId());
        }

        //if the master facility record isn't found yet, search based on EIS ID
        if ((mfr == null || !mfr.isPresent()) && bulkEmissionsReport.getEisProgramId() != null && !(bulkEmissionsReport.getEisProgramId().isEmpty())) {
            mfr = mfrRepo.findByEisProgramId(bulkEmissionsReport.getEisProgramId());
        }

        //if the master facility record isn't found yet, search based on agency facility ID and PSC combo
        if (mfr == null || !mfr.isPresent()) {
            String agencyFacilityId = findAgencyFacilityId(bulkEmissionsReport);
            List<MasterFacilityRecord> mfrList = mfrRepo.findByProgramSystemCodeCodeAndAgencyFacilityIdIgnoreCase(bulkEmissionsReport.getProgramSystemCode(), agencyFacilityId);
            if (mfrList.size() > 0) {
                mfr = Optional.of(mfrList.get(0));
            }

        }

        return mfr.orElseThrow(() -> new NotExistException("Master Facility Record", bulkEmissionsReport.getMasterFacilityRecordId().toString()));
    }


    /**
     * Find the agency facility ID within the facility site of the bulk emissions report
     */
    private String findAgencyFacilityId(EmissionsReportBulkUploadDto bulkEmissionsReport) {
        
        String agencyFacilityId = null;

        if (bulkEmissionsReport.getFacilitySites().size() > 0) {
            agencyFacilityId = bulkEmissionsReport.getFacilitySites().get(0).getAltSiteIdentifier();
        }

        return agencyFacilityId;
    }


    /**
     * Map an EmissionsUnitBulkUploadDto to an EmissionsUnit domain model
     */
    private EmissionsUnit mapEmissionsUnit(EmissionsUnitBulkUploadDto bulkEmissionsUnit) {

        EmissionsUnit emissionsUnit = new EmissionsUnit();

        emissionsUnit.setUnitIdentifier(bulkEmissionsUnit.getUnitIdentifier());
        emissionsUnit.setDescription(bulkEmissionsUnit.getDescription());
        emissionsUnit.setStatusYear(toShort(bulkEmissionsUnit.getStatusYear()));
        emissionsUnit.setDesignCapacity(toBigDecimal(bulkEmissionsUnit.getDesignCapacity()));
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
     * Map a FacilitySiteBulkUploadDto to a FacilitySite domain model
     */
    private FacilitySite mapFacility(FacilitySiteBulkUploadDto bulkFacility) {

        FacilitySite facility = new FacilitySite();

        facility.setAltSiteIdentifier(bulkFacility.getAltSiteIdentifier());
        facility.setName(bulkFacility.getName());
        facility.setDescription(bulkFacility.getDescription());
        facility.setStatusYear(toShort(bulkFacility.getStatusYear()));
        facility.setStreetAddress(bulkFacility.getStreetAddress());
        facility.setCity(bulkFacility.getCity());
        facility.setCountryCode(bulkFacility.getCountryCode());
        facility.setPostalCode(bulkFacility.getPostalCode());
        facility.setLatitude(toBigDecimal(bulkFacility.getLatitude()));
        facility.setLongitude(toBigDecimal(bulkFacility.getLongitude()));
        facility.setMailingStreetAddress(bulkFacility.getMailingStreetAddress());
        facility.setMailingCity(bulkFacility.getMailingCity());
        facility.setMailingPostalCode(bulkFacility.getMailingPostalCode());
        facility.setComments(bulkFacility.getComments());

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

        if (bulkFacility.getStateCode() != null) {
            facility.setStateCode((stateCodeRepo.findByUspsCode(bulkFacility.getStateCode().toUpperCase())).orElse(null));
            if (facility.getStateCode() != null && Strings.emptyToNull(bulkFacility.getCountyCode()) != null) {
                facility.setCountyCode(countyRepo.findByFipsStateCodeCodeAndCountyCode(facility.getStateCode().getCode(), bulkFacility.getCountyCode()).orElse(null));
            }
        }
        if (bulkFacility.getMailingStateCode() != null) {
            facility.setMailingStateCode((stateCodeRepo.findByUspsCode(bulkFacility.getMailingStateCode().toUpperCase())).orElse(null));
        }

        return facility;
    }

    /**
     * Map an FacilitySiteContactBulkUploadDto to an FacilitySiteContact domain model
     */
    private FacilitySiteContact mapFacilityContact(FacilitySiteContactBulkUploadDto bulkFacilityContact) {

        FacilitySiteContact facilityContact = new FacilitySiteContact();

        facilityContact.setPrefix(bulkFacilityContact.getPrefix());
        facilityContact.setFirstName(bulkFacilityContact.getFirstName());
        facilityContact.setLastName(bulkFacilityContact.getLastName());
        facilityContact.setEmail(bulkFacilityContact.getEmail());
        facilityContact.setPhone(bulkFacilityContact.getPhone());
        facilityContact.setPhoneExt(bulkFacilityContact.getPhoneExt());
        facilityContact.setStreetAddress(bulkFacilityContact.getStreetAddress());
        facilityContact.setCity(bulkFacilityContact.getCity());
        facilityContact.setCountryCode(bulkFacilityContact.getCountryCode());
        facilityContact.setPostalCode(bulkFacilityContact.getPostalCode());
        facilityContact.setMailingStreetAddress(bulkFacilityContact.getMailingStreetAddress());
        facilityContact.setMailingCity(bulkFacilityContact.getMailingCity());
        facilityContact.setMailingPostalCode(bulkFacilityContact.getMailingPostalCode());
        facilityContact.setMailingCountryCode(bulkFacilityContact.getMailingCountryCode());

        if (bulkFacilityContact.getType() != null) {
            facilityContact.setType((contactTypeRepo.findById(bulkFacilityContact.getType())).orElse(null));
        }
        if (bulkFacilityContact.getStateCode() != null) {
            facilityContact.setStateCode((stateCodeRepo.findByUspsCode(bulkFacilityContact.getStateCode().toUpperCase())).orElse(null));
            if (facilityContact.getStateCode() != null && Strings.emptyToNull(bulkFacilityContact.getCountyCode()) != null) {
                facilityContact.setCountyCode(countyRepo.findByFipsStateCodeCodeAndCountyCode(facilityContact.getStateCode().getCode(), bulkFacilityContact.getCountyCode()).orElse(null));
            }
        }
        if (bulkFacilityContact.getMailingStateCode() != null) {
            facilityContact.setMailingStateCode((stateCodeRepo.findByUspsCode(bulkFacilityContact.getMailingStateCode().toUpperCase())).orElse(null));
        }

        return facilityContact;
    }

    /**
     * Map an FacilityNAICSBulkUploadDto to an FacilityNAICS domain model
     */
    private FacilityNAICSXref mapFacilityNAICS(FacilityNAICSBulkUploadDto bulkFacilityNAICS) {

        FacilityNAICSXref facilityNAICS = new FacilityNAICSXref();

        facilityNAICS.setPrimaryFlag(bulkFacilityNAICS.isPrimaryFlag());

        Integer naics = toInt(bulkFacilityNAICS.getCode());
        if (naics != null) {
            facilityNAICS.setNaicsCode((naicsCodeRepo.findById(naics)).orElse(null));
        }

        return facilityNAICS;
    }

    /**
     * Map an OperatingDetailBulkUploadDto to an OperatingDetail domain model
     */
    private OperatingDetail mapOperatingDetail(OperatingDetailBulkUploadDto dto) {

        OperatingDetail result = uploadMapper.operatingDetailFromDto(dto);

        return result;
    }

    /**
     * Map a ReleasePointBulkUploadDto to a ReleasePoint domain model
     */
    private ReleasePoint mapReleasePoint(ReleasePointBulkUploadDto bulkReleasePoint) {

        ReleasePoint releasePoint = new ReleasePoint();

        releasePoint.setReleasePointIdentifier(bulkReleasePoint.getReleasePointIdentifier());
        releasePoint.setDescription(bulkReleasePoint.getDescription());
        releasePoint.setStackHeight(toDouble(bulkReleasePoint.getStackHeight()));
        releasePoint.setStackDiameter(toDouble(bulkReleasePoint.getStackDiameter()));
        releasePoint.setStackWidth(toDouble(bulkReleasePoint.getStackWidth()));
        releasePoint.setStackLength(toDouble(bulkReleasePoint.getStackLength()));
        releasePoint.setExitGasVelocity(toDouble(bulkReleasePoint.getExitGasVelocity()));
        releasePoint.setExitGasTemperature(toShort(bulkReleasePoint.getExitGasTemperature()));
        releasePoint.setExitGasFlowRate(toDouble(bulkReleasePoint.getExitGasFlowRate()));
        releasePoint.setStatusYear(toShort(bulkReleasePoint.getStatusYear()));
        releasePoint.setFugitiveLine1Latitude(toDouble(bulkReleasePoint.getFugitiveLine1Latitude()));
        releasePoint.setFugitiveLine1Longitude(toDouble(bulkReleasePoint.getFugitiveLine1Longitude()));
        releasePoint.setFugitiveLine2Latitude(toDouble(bulkReleasePoint.getFugitiveLine2Latitude()));
        releasePoint.setFugitiveLine2Longitude(toDouble(bulkReleasePoint.getFugitiveLine2Longitude()));
        releasePoint.setLatitude(toDouble(bulkReleasePoint.getLatitude()));
        releasePoint.setLongitude(toDouble(bulkReleasePoint.getLongitude()));
        releasePoint.setComments(bulkReleasePoint.getComments());
        releasePoint.setFugitiveHeight(toLong(bulkReleasePoint.getFugitiveHeight()));
        releasePoint.setFugitiveWidth(toLong(bulkReleasePoint.getFugitiveWidth()));
        releasePoint.setFugitiveLength(toLong(bulkReleasePoint.getFugitiveLength()));
        releasePoint.setFugitiveAngle(toLong(bulkReleasePoint.getFugitiveAngle()));
        releasePoint.setFenceLineDistance(toLong(bulkReleasePoint.getFenceLineDistance()));

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
        if (bulkReleasePoint.getStackWidthUomCode() != null) {
            releasePoint.setStackWidthUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getStackWidthUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getStackLengthUomCode() != null) {
            releasePoint.setStackLengthUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getStackLengthUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getExitGasVelocityUomCode() != null) {
            releasePoint.setExitGasVelocityUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getExitGasVelocityUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getExitGasFlowUomCode() != null) {
            releasePoint.setExitGasFlowUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getExitGasFlowUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getFenceLineUomCode() != null) {
            releasePoint.setFenceLineUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getFenceLineUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getFugitiveHeightUomCode() != null) {
            releasePoint.setFugitiveHeightUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getFugitiveHeightUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getFugitiveWidthUomCode() != null) {
            releasePoint.setFugitiveWidthUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getFugitiveWidthUomCode()).orElse(null));
        }
        if (bulkReleasePoint.getFugitiveLengthUomCode() != null) {
            releasePoint.setFugitiveLengthUomCode(unitMeasureCodeRepo.findById(bulkReleasePoint.getFugitiveLengthUomCode()).orElse(null));
        }

        return releasePoint;
    }

    /**
     * Map an ReleasePointApptBulkUploadDto to an OperatingDetail domain model
     */
    private ReleasePointAppt mapReleasePointAppt(ReleasePointApptBulkUploadDto dto) {

        ReleasePointAppt result = uploadMapper.releasePointApptFromDto(dto);

        return result;
    }

    /**
     * Map an ReportingPeriodBulkUploadDto to an ReportingPeriod domain model
     */
    private ReportingPeriod mapReportingPeriod(ReportingPeriodBulkUploadDto dto) {

        ReportingPeriod result = uploadMapper.reportingPeriodFromDto(dto);

        if (dto.getCalculationMaterialCode() != null) {
            result.setCalculationMaterialCode(calcMaterialCodeRepo.findById(dto.getCalculationMaterialCode()).orElse(null));
        }
        if (dto.getCalculationParameterTypeCode() != null) {
            result.setCalculationParameterTypeCode(calcParamTypeCodeRepo.findById(dto.getCalculationParameterTypeCode()).orElse(null));
        }
        if (dto.getCalculationParameterUom() != null) {
            result.setCalculationParameterUom(unitMeasureCodeRepo.findById(dto.getCalculationParameterUom()).orElse(null));
        }
        if (dto.getEmissionsOperatingTypeCode() != null) {
            result.setEmissionsOperatingTypeCode(emissionsOperatingTypeCodeRepo.findById(dto.getEmissionsOperatingTypeCode()).orElse(null));
        }
        if (dto.getReportingPeriodTypeCode() != null) {
            result.setReportingPeriodTypeCode(reportingPeriodCodeRepo.findById(dto.getReportingPeriodTypeCode()).orElse(null));
        }
        if (dto.getFuelUseMaterialCode() != null) {
            result.setFuelUseMaterialCode(calcMaterialCodeRepo.findById(dto.getFuelUseMaterialCode()).orElse(null));
        }
        if (dto.getFuelUseUom() != null) {
            result.setFuelUseUom(unitMeasureCodeRepo.findById(dto.getFuelUseUom()).orElse(null));
        }
        if (dto.getHeatContentUom() != null) {
            result.setHeatContentUom(unitMeasureCodeRepo.findById(dto.getHeatContentUom()).orElse(null));
        }

        return result;
    }

    private EmissionsReportBulkUploadDto parseWorkbookJson(ExcelParserResponse response,
                                                           EmissionsReportStarterDto metadata) {

       return parseJsonNode(true).andThen(result -> {

           result.setProgramSystemCode(metadata.getProgramSystemCode());
           result.setMasterFacilityRecordId(metadata.getMasterFacilityRecordId());
           result.setEisProgramId(metadata.getEisProgramId());
           result.setFrsFacilityId(metadata.getFrsFacilityId());
           result.setAltSiteIdentifier(metadata.getStateFacilityId());
           result.setYear(metadata.getYear());
           result.setStatus(ReportStatus.IN_PROGRESS.name());
           result.setValidationStatus(ValidationStatus.UNVALIDATED.name());
           result.setEisLastSubmissionStatus(EisSubmissionStatus.NotStarted.name());

           return result;

       }).apply(response.getJson());
    }

    private BigDecimal toBigDecimal(String strval) {

        return Strings.isNullOrEmpty(strval) ? null : new BigDecimal(strval);
    }

    private Double toDouble(String strval) {

        return Strings.isNullOrEmpty(strval) ? null : Double.parseDouble(strval);
    }

    private Integer toInt(String strval) {

        return Strings.isNullOrEmpty(strval) ? null : Integer.parseInt(strval);
    }

    private Long toLong(String strval) {

        return Strings.isNullOrEmpty(strval) ? null : Long.parseLong(strval);
    }

    private Short toShort(String strval) {

        return Strings.isNullOrEmpty(strval) ? null : Short.parseShort(strval);
    }

    public static class JsonNodeToBulkUploadDto implements Function<JsonNode, EmissionsReportBulkUploadDto> {

        private final ObjectMapper objectMapper;

        public JsonNodeToBulkUploadDto(ObjectMapper objectMapper, boolean failUnknownProperties) {

            this.objectMapper = objectMapper.copy()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failUnknownProperties)
                .registerModule(new BlankToNullModule());
        }

        @Override
        public EmissionsReportBulkUploadDto apply(JsonNode jsonNode) {

            EmissionsReportBulkUploadDto result;

            try {
                result = this.objectMapper.treeToValue(jsonNode, EmissionsReportBulkUploadDto.class);

            } catch (JsonProcessingException e) {

                String msg = e.getMessage().replaceAll(
                    EmissionsReportBulkUploadDto.class.getPackage().getName().concat("."), "");

                WorksheetError violation = WorksheetError.createSystemError(msg);

                throw new BulkReportValidationException(Collections.singletonList(violation));
            }

            return result;
        }
    }
}
