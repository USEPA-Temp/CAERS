package gov.epa.cef.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import gov.epa.cef.web.client.api.ExcelParserClient;
import gov.epa.cef.web.client.api.ExcelParserResponse;
import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFormulaVariable;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.FipsStateCode;
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
import gov.epa.cef.web.repository.FacilitySourceTypeCodeRepository;
import gov.epa.cef.web.repository.FipsCountyRepository;
import gov.epa.cef.web.repository.FipsStateCodeRepository;
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
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EmissionsReportStarterDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlAssignmentBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPathBulkUploadDto;
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
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import gov.epa.cef.web.util.CalculationUtils;
import gov.epa.cef.web.util.MassUomConversion;
import gov.epa.cef.web.util.TempFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private EmissionsReportMapper emissionsReportMapper;

    @Autowired
    private EmissionsReportService emissionsReportService;

    @Autowired
    private ExcelParserClient excelParserClient;

    @Autowired
    private FacilityCategoryCodeRepository facilityCategoryRepo;

    @Autowired
    private FacilitySourceTypeCodeRepository facilitySourceTypeRepo;

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

    /**
     * Testing method for generating upload JSON for a report
     *
     * @param reportId
     * @return
     */
    @Override
    public EmissionsReportBulkUploadDto generateBulkUploadDto(Long reportId) {

        EmissionsReport report = this.emissionsReportService.retrieve(reportId)
            .orElseThrow(() -> new NotExistException("Emissions Report", reportId));

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
        List<ControlPath> controlPaths = facilitySites.stream()
            .flatMap(f -> f.getControlPaths().stream())
            .collect(Collectors.toList());
        List<Control> controls = facilitySites.stream()
            .flatMap(c -> c.getControls().stream())
            .collect(Collectors.toList());
        // control_path_id in the DB is non-null so this should get every assignment exactly once
        List<ControlAssignment> controlAssignments = controlPaths.stream()
            .flatMap(c -> c.getAssignments().stream())
            .collect(Collectors.toList());
        List<ControlPollutant> controlPollutants = controls.stream()
            .flatMap(c -> c.getPollutants().stream())
            .collect(Collectors.toList());
        List<FacilityNAICSXref> facilityNacis = facilitySites.stream()
            .flatMap(fn -> fn.getFacilityNAICS().stream())
            .collect(Collectors.toList());
        List<FacilitySiteContact> facilityContacts = facilitySites.stream()
            .flatMap(fc -> fc.getContacts().stream())
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
        reportDto.setControlPaths(uploadMapper.controlPathToDtoList(controlPaths));
        reportDto.setControls(uploadMapper.controlToDtoList(controls));
        reportDto.setControlAssignments(uploadMapper.controlAssignmentToDtoList(controlAssignments));
        reportDto.setControlPollutants(uploadMapper.controlPollutantToDtoList(controlPollutants));
        reportDto.setFacilityNAICS(uploadMapper.faciliytNAICSToDtoList(facilityNacis));
        reportDto.setFacilityContacts(uploadMapper.facilitySiteContactToDtoList(facilityContacts));

        return reportDto;
    }

    /**
     * Save the emissions report to the database.
     *
     * @param bulkEmissionsReport
     * @return
     */
    @Override
    public EmissionsReportDto saveBulkEmissionsReport(EmissionsReportBulkUploadDto bulkEmissionsReport) {

        Collection<String> warnings = new ArrayList<>();

        EmissionsReport emissionsReport = mapEmissionsReport(bulkEmissionsReport);

        for (FacilitySiteBulkUploadDto bulkFacility : bulkEmissionsReport.getFacilitySites()) {
            FacilitySite facility = mapFacility(bulkFacility);

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
                                                }catch (CalculationException e) {
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

        return this.emissionsReportService.saveAndAuditEmissionsReport(emissionsReport, ReportAction.CREATED);
    }

    public EmissionsReportDto saveBulkWorkbook(EmissionsReportStarterDto metadata, TempFile workbook) {

        EmissionsReportDto result = null;

        this.emissionsReportService.retrieveByEisProgramIdAndYear(metadata.getEisProgramId(), metadata.getYear())
            .ifPresent(report -> {

                this.emissionsReportService.delete(report.getId());
            });

        ExcelParserResponse response = this.excelParserClient.parseWorkbook(workbook);

        if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {

            logger.info("ExcelJsonParser Result {}", response.getJson().toString());

            EmissionsReportBulkUploadDto bulkEmissionsReport = parseWorkbookJson(response, metadata);

            this.validator.validate(bulkEmissionsReport);

            try {

                result = saveBulkEmissionsReport(bulkEmissionsReport);

            } catch (Exception e) {

                String msg = e.getMessage()
                    .replaceAll(EmissionsReportBulkUploadDto.class.getPackage().getName().concat("."), "")
                    .replaceAll(EmissionsReport.class.getPackage().getName().concat("."), "");

                WorksheetError violation = new WorksheetError("*", -1, msg);

                throw new BulkReportValidationException(Collections.singletonList(violation));
            }

        } else {

            List<WorksheetError> errors = new ArrayList<>();
            errors.add(new WorksheetError(null, -1, "Unable to read workbook."));
            if (response.getJson() != null && response.getJson().hasNonNull("message")) {
                errors.add(new WorksheetError(null, -1, response.getJson().path("message").asText()));
            }

            throw new BulkReportValidationException(errors);
        }

        return result;
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
        if (dto.getControlMeasureCode() != null) {
            result.setControlMeasureCode(controlMeasureCodeRepo.findById(dto.getControlMeasureCode()).orElse(null));
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
     * Map an OperatingDetailBulkUploadDto to an OperatingDetail domain model
     */
    private Emission mapEmission(EmissionBulkUploadDto dto) {

        Emission result = uploadMapper.emissionsFromDto(dto);

        result.setTotalManualEntry(false);
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
     * Map an EmissionsUnitBulkUploadDto to an EmissionsUnit domain model
     */
    private EmissionsUnit mapEmissionsUnit(EmissionsUnitBulkUploadDto bulkEmissionsUnit) {

        EmissionsUnit emissionsUnit = new EmissionsUnit();

        emissionsUnit.setUnitIdentifier(bulkEmissionsUnit.getUnitIdentifier());
        emissionsUnit.setDescription(bulkEmissionsUnit.getDescription());
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
     * Map a FacilitySiteBulkUploadDto to a FacilitySite domain model
     */
    private FacilitySite mapFacility(FacilitySiteBulkUploadDto bulkFacility) {

        FacilitySite facility = new FacilitySite();

        facility.setFrsFacilityId(bulkFacility.getFrsFacilityId());
        facility.setAltSiteIdentifier(bulkFacility.getAltSiteIdentifier());
        facility.setName(bulkFacility.getName());
        facility.setDescription(bulkFacility.getDescription());
        facility.setStatusYear(bulkFacility.getStatusYear());
        facility.setStreetAddress(bulkFacility.getStreetAddress());
        facility.setCity(bulkFacility.getCity());
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

        if (Strings.emptyToNull(bulkFacility.getStateCode()) != null && Strings.emptyToNull(bulkFacility.getCountyCode()) != null) {
            FipsStateCode stateCode = stateCodeRepo.findByUspsCode(bulkFacility.getStateCode()).orElse(null);
            if (stateCode != null) {
                facility.setCountyCode(countyRepo.findByFipsStateCodeCodeAndCountyCode(stateCode.getCode(), bulkFacility.getCountyCode()).orElse(null));
            }
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
            facilityContact.setStateCode((stateCodeRepo.findByUspsCode(bulkFacilityContact.getStateCode())).orElse(null));
            if (facilityContact.getStateCode() != null && Strings.emptyToNull(bulkFacilityContact.getCountyCode()) != null) {
                facilityContact.setCountyCode(countyRepo.findByFipsStateCodeCodeAndCountyCode(facilityContact.getStateCode().getCode(), bulkFacilityContact.getCountyCode()).orElse(null));
            }
        }
        if (bulkFacilityContact.getMailingStateCode() != null) {
            facilityContact.setMailingStateCode((stateCodeRepo.findByUspsCode(bulkFacilityContact.getMailingStateCode())).orElse(null));
        }

        return facilityContact;
    }

    /**
     * Map an FacilityNAICSBulkUploadDto to an FacilityNAICS domain model
     */
    private FacilityNAICSXref mapFacilityNAICS(FacilityNAICSBulkUploadDto bulkFacilityNAICS) {

        FacilityNAICSXref facilityNAICS = new FacilityNAICSXref();

        facilityNAICS.setPrimaryFlag(bulkFacilityNAICS.isPrimaryFlag());

        if (bulkFacilityNAICS.getCode() != null) {
            facilityNAICS.setNaicsCode((naicsCodeRepo.findById(bulkFacilityNAICS.getCode())).orElse(null));
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
        releasePoint.setFugitiveHeight(bulkReleasePoint.getFugitiveHeight());
        releasePoint.setFugitiveWidth(bulkReleasePoint.getFugitiveWidth());
        releasePoint.setFugitiveLength(bulkReleasePoint.getFugitiveLength());
        releasePoint.setFugitiveAngle(bulkReleasePoint.getFugitiveAngle());
        releasePoint.setFenceLineDistance(bulkReleasePoint.getFenceLineDistance());

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

        return result;
    }

    private EmissionFormulaVariable mapEmissionFormulaVariable(EmissionFormulaVariableBulkUploadDto dto) {

        EmissionFormulaVariable result = new EmissionFormulaVariable();
        result.setValue(dto.getValue());

        if (dto.getEmissionFormulaVariableCode() != null) {
            result.setVariableCode(emissionFormulaVariableCodeRepo.findById(dto.getEmissionFormulaVariableCode()).orElse(null));
        }

        return result;
    }

    private EmissionsReportBulkUploadDto parseWorkbookJson(ExcelParserResponse response,
                                                           EmissionsReportStarterDto metadata) {

        EmissionsReportBulkUploadDto result;

        try {
            result = this.objectMapper.copy()
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .treeToValue(response.getJson(), EmissionsReportBulkUploadDto.class);

            result.setAgencyCode(EmissionsReportService.__HARD_CODED_AGENCY_CODE__);
            result.setEisProgramId(metadata.getEisProgramId());
            result.setFrsFacilityId(metadata.getFrsFacilityId());
            result.setAltSiteIdentifier(metadata.getStateFacilityId());
            result.setYear(metadata.getYear());
            result.setStatus(ReportStatus.IN_PROGRESS.name());
            result.setValidationStatus(ValidationStatus.UNVALIDATED.name());

            logger.info(result.getEmissionFormulaVariables().toString());

        } catch (JsonProcessingException e) {

            String msg = e.getMessage().replaceAll(
                EmissionsReportBulkUploadDto.class.getPackage().getName().concat("."), "");

            WorksheetError violation = new WorksheetError("*", -1, msg);

            throw new BulkReportValidationException(Collections.singletonList(violation));
        }

        return result;
    }
}
