package gov.epa.cef.web.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.repository.AircraftEngineTypeCodeRepository;
import gov.epa.cef.web.repository.CalculationMaterialCodeRepository;
import gov.epa.cef.web.repository.CalculationMethodCodeRepository;
import gov.epa.cef.web.repository.CalculationParameterTypeCodeRepository;
import gov.epa.cef.web.repository.ControlMeasureCodeRepository;
import gov.epa.cef.web.repository.EmissionsOperatingTypeCodeRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.FacilityCategoryCodeRepository;
import gov.epa.cef.web.repository.FacilitySourceTypeCodeRepository;
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
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlAssignmentBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPathBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPollutantBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsProcessBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsUnitBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.OperatingDetailBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointApptBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReportingPeriodBulkUploadDto;
import gov.epa.cef.web.service.mapper.BulkUploadMapper;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import gov.epa.cef.web.util.CalculationUtils;
import gov.epa.cef.web.util.MassUomConversion;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BulkUploadServiceImpl implements BulkUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
    private ControlMeasureCodeRepository controlMeasureCodeRepo;

    @Autowired
    private OperatingStatusCodeRepository operatingStatusRepo;

    @Autowired
    private EmissionsOperatingTypeCodeRepository emissionsOperatingTypeCodeRepo;

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

    /**
     * Save the emissions report to the database.
     * @param bulkEmissionsReport
     * @return
     */
    @Override
    public EmissionsReportDto saveBulkEmissionsReport(EmissionsReportBulkUploadDto bulkEmissionsReport) {
        EmissionsReport emissionsReport = mapEmissionsReport(bulkEmissionsReport);

        for (FacilitySiteBulkUploadDto bulkFacility : bulkEmissionsReport.getFacilitySites()) {
            FacilitySite facility = mapFacility(bulkFacility);

            // Maps for storing the entity referenced by a certain id in the JSON
            Map<Long, ReleasePoint> releasePointMap = new HashMap<>();
            Map<Long, EmissionsProcess> processMap = new HashMap<>();
            Map<Long, ControlPath> controlPathMap = new HashMap<>();
            Map<Long, Control> controlMap = new HashMap<>();

            // Map Release Points
            for (ReleasePointBulkUploadDto bulkRp : bulkEmissionsReport.getReleasePoints()) {
                ReleasePoint releasePoint = mapReleasePoint(bulkRp);

                if (bulkRp.getFacilitySiteId().equals(bulkFacility.getId())) {
                    releasePoint.setFacilitySite(facility);
                    facility.getReleasePoints().add(releasePoint);
                    releasePointMap.put(bulkRp.getId(), releasePoint);
                }
            }

            // Map Emissions Units
            for (EmissionsUnitBulkUploadDto bulkEmissionsUnit : bulkEmissionsReport.getEmissionsUnits()) {

                if (bulkEmissionsUnit.getFacilitySiteId().equals(bulkFacility.getId())) {
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
                                                        emission.setReportingPeriod(period);

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
                }

                ControlPath controlPathChild = controlPathMap.get(bulkControlAssignment.getControlPathChildId());
                if (controlPathChild != null) {
                    controlAssignment.setControlPathChild(controlPathChild);
                    controlPathChild.getChildAssignments().add(controlAssignment);
                }

                Control control = controlMap.get(bulkControlAssignment.getControlId());
                if (control != null) {
                    controlAssignment.setControl(control);
                    control.getAssignments().add(controlAssignment);
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


        EmissionsReport savedReport = erRepo.save(emissionsReport);
        return emissionsReportMapper.toDto(savedReport);
    }

    /**
     * Testing method for generating upload JSON for a report
     * @param reportId
     * @return
     */
    @Override
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

        return reportDto;
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
    private EmissionsUnit mapEmissionsUnit(EmissionsUnitBulkUploadDto bulkEmissionsUnit) {
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
            result.setEmissionsOperatingTypeCode(emissionsOperatingTypeCodeRepo.findById(dto.getEmissionsOperatingTypeCode()).orElse(null));
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

        if (result.getEmissionsUomCode() != null && result.getTotalEmissions() != null) {
            result.setCalculatedEmissionsTons(calculateEmissionTons(result));
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

    /**
     * Map an ControlPathBulkUploadDto to an ControlPath domain model
     */
    private ControlPath mapControlPath(ControlPathBulkUploadDto dto) {
        ControlPath result = uploadMapper.controlPathFromDto(dto);

        return result;
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
     * Calculate the total emissions in Tons for an Emission
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
            logger.debug("Could not perform emission conversion. " + ex.getLocalizedMessage());
            return null;
        }
    }

}
