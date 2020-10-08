package gov.epa.cef.web.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
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
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.Resources;

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
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetName;
import gov.epa.cef.web.service.mapper.BulkUploadMapper;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import gov.epa.cef.web.util.CalculationUtils;
import gov.epa.cef.web.util.MassUomConversion;
import gov.epa.cef.web.util.TempFile;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BulkUploadServiceImpl implements BulkUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String EXCEL_FILE_PATH = "excel/CEF_BulkUpload_Template.xlsx";
    private static final String EXCEL_GENERIC_LOOKUP_TEXT = "INDEX(%s!$A$2:$A$%d,MATCH(\"%s\",%s!$B$2:$B$%d,0))";
    private static final String EXCEL_GENERIC_LOOKUP_NUMBER = "INDEX(%s!$A$2:$A$%d,MATCH(%s,%s!$B$2:$B$%d,0))";
    private static final int EXCEL_MAPPING_HEADER_ROWS = 23;

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
    
    @Autowired
    private FacilitySiteRepository facilitySiteRepo;

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
            .sorted((i1, i2) -> i1.getUnitIdentifier().compareToIgnoreCase(i2.getUnitIdentifier()))
            .collect(Collectors.toList());
        List<EmissionsProcess> processes = units.stream()
            .flatMap(u -> u.getEmissionsProcesses().stream())
            .collect(Collectors.toList());
        List<ReportingPeriod> periods = processes.stream()
            .flatMap(p -> p.getReportingPeriods().stream())
            .collect(Collectors.toList());
        List<OperatingDetail> operatingDetails = periods.stream()
            .flatMap(p -> p.getOperatingDetails().stream())
            .sorted((i1, i2) -> {
                String display1 = String.format("%s-%s-%s", 
                        i1.getReportingPeriod().getEmissionsProcess().getEmissionsUnit().getUnitIdentifier(), 
                        i1.getReportingPeriod().getEmissionsProcess().getEmissionsProcessIdentifier(),
                        i1.getReportingPeriod().getReportingPeriodTypeCode().getShortName());
                String display2 = String.format("%s-%s-%s", 
                        i2.getReportingPeriod().getEmissionsProcess().getEmissionsUnit().getUnitIdentifier(), 
                        i2.getReportingPeriod().getEmissionsProcess().getEmissionsProcessIdentifier(),
                        i2.getReportingPeriod().getReportingPeriodTypeCode().getShortName());
                return display1.compareToIgnoreCase(display2);
            })
            .collect(Collectors.toList());
        List<Emission> emissions = periods.stream()
            .flatMap(p -> p.getEmissions().stream())
            .collect(Collectors.toList());
        List<EmissionFormulaVariable> variables = emissions.stream()
            .flatMap(e -> e.getVariables().stream())
            .collect(Collectors.toList());
        List<ReleasePoint> releasePoints = facilitySites.stream()
            .flatMap(f -> f.getReleasePoints().stream())
            .sorted((i1, i2) -> i1.getReleasePointIdentifier().compareToIgnoreCase(i2.getReleasePointIdentifier()))
            .collect(Collectors.toList());
        List<ReleasePointAppt> releasePointAppts = releasePoints.stream()
            .flatMap(r -> r.getReleasePointAppts().stream())
            .collect(Collectors.toList());
        List<ControlPath> controlPaths = facilitySites.stream()
            .flatMap(f -> f.getControlPaths().stream())
            .collect(Collectors.toList());
        List<Control> controls = facilitySites.stream()
            .flatMap(c -> c.getControls().stream())
            .sorted((i1, i2) -> i1.getIdentifier().compareToIgnoreCase(i2.getIdentifier()))
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

        reportDto.setEmissionsProcesses(processes.stream().map(i -> {
                EmissionsProcessBulkUploadDto result = uploadMapper.emissionsProcessToDto(i);
                result.setDisplayName(String.format("%s-%s", 
                        i.getEmissionsUnit().getUnitIdentifier(), 
                        i.getEmissionsProcessIdentifier()));
                return result;
            }).sorted((i1, i2) -> i1.getDisplayName().compareToIgnoreCase(i2.getDisplayName()))
            .collect(Collectors.toList()));

        reportDto.setReportingPeriods(periods.stream().map(i -> {
                ReportingPeriodBulkUploadDto result = uploadMapper.reportingPeriodToDto(i);
                result.setDisplayName(String.format("%s-%s-%s", 
                        i.getEmissionsProcess().getEmissionsUnit().getUnitIdentifier(), 
                        i.getEmissionsProcess().getEmissionsProcessIdentifier(),
                        i.getReportingPeriodTypeCode().getShortName()));
                return result;
            }).sorted((i1, i2) -> i1.getDisplayName().compareToIgnoreCase(i2.getDisplayName()))
            .collect(Collectors.toList()));

        reportDto.setOperatingDetails(uploadMapper.operatingDetailToDtoList(operatingDetails));

        reportDto.setEmissions(emissions.stream().map(i -> {
            EmissionBulkUploadDto result = uploadMapper.emissionToDto(i);
            result.setDisplayName(String.format("%s-%s-%s(%s)", 
                    i.getReportingPeriod().getEmissionsProcess().getEmissionsUnit().getUnitIdentifier(), 
                    i.getReportingPeriod().getEmissionsProcess().getEmissionsProcessIdentifier(),
                    i.getReportingPeriod().getReportingPeriodTypeCode().getShortName(),
                    i.getPollutant().getPollutantName()));
            return result;
        }).sorted((i1, i2) -> i1.getDisplayName().compareToIgnoreCase(i2.getDisplayName()))
        .collect(Collectors.toList()));

        reportDto.setEmissionFormulaVariables(uploadMapper.emissionFormulaVariableToDtoList(variables));
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
     * Generate an excel spreadsheet export for a report
     * 
     * Maps a report into our excel template for uploading. This creates a temporary copy of the excel template
     * and then uses Apache POI to populate that copy with the existing data. We modify existing rows like a 
     * user would so that validation and formulas remain intact and populate dropdowns by looking up the value
     * in the spreadsheet for the code we have.
     * 
     * Currently has commented out debugging code while more sections are added
     * @param reportId
     * @param outputStream
     */
    public void generateExcel(Long reportId, OutputStream outputStream) {

        logger.info("Begin generate excel");

        EmissionsReportBulkUploadDto uploadDto = this.generateBulkUploadDto(reportId);

        logger.info("Begin file manipulation");

        URL template = Resources.getResource(EXCEL_FILE_PATH);

        try (FileInputStream is = new FileInputStream(new File(template.toURI()));
             TempFile tempFile = TempFile.from(is, UUID.randomUUID().toString());
             XSSFWorkbook wb = XSSFWorkbookFactory.createWorkbook(tempFile.getFile(), false)) {

            // locked cells will return null and cause null pointer exceptions without this
            wb.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);

            XSSFFormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
 
//            facilitySheet.disableLocking();

            Map<Long, ReleasePointBulkUploadDto> rpMap = uploadDto.getReleasePoints()
                    .stream().collect(Collectors.toMap(ReleasePointBulkUploadDto::getId, Functions.identity()));
            Map<Long, EmissionsUnitBulkUploadDto> euMap = uploadDto.getEmissionsUnits()
                    .stream().collect(Collectors.toMap(EmissionsUnitBulkUploadDto::getId, Functions.identity()));
            Map<Long, EmissionsProcessBulkUploadDto> epMap = uploadDto.getEmissionsProcesses()
                    .stream().collect(Collectors.toMap(EmissionsProcessBulkUploadDto::getId, Functions.identity()));
            Map<Long, ControlBulkUploadDto> controlMap = uploadDto.getControls()
                    .stream().collect(Collectors.toMap(ControlBulkUploadDto::getId, Functions.identity()));
            Map<Long, ControlPathBulkUploadDto> pathMap = uploadDto.getControlPaths()
                    .stream().collect(Collectors.toMap(ControlPathBulkUploadDto::getId, Functions.identity()));
            Map<Long, ReportingPeriodBulkUploadDto> periodMap = uploadDto.getReportingPeriods()
                    .stream().collect(Collectors.toMap(ReportingPeriodBulkUploadDto::getId, Functions.identity()));
            Map<Long, EmissionBulkUploadDto> emissionMap = uploadDto.getEmissions()
                    .stream().collect(Collectors.toMap(EmissionBulkUploadDto::getId, Functions.identity()));

            generateFacilityExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.FacilitySite.sheetName()), uploadDto.getFacilitySites());
            generateFacilityContactExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.FacilitySiteContact.sheetName()), uploadDto.getFacilityContacts());
            generateNAICSExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.FacilityNaics.sheetName()), uploadDto.getFacilityNAICS());
            generateReleasePointsExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.ReleasePoint.sheetName()), uploadDto.getReleasePoints());
            generateEmissionUnitExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.EmissionsUnit.sheetName()), uploadDto.getEmissionsUnits());
            generateProcessesExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.EmissionsProcess.sheetName()), uploadDto.getEmissionsProcesses(), euMap);
            generateControlsExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.Control.sheetName()), uploadDto.getControls());
            generateControlPathsExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.ControlPath.sheetName()), uploadDto.getControlPaths());
            generateControlAssignmentsExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.ControlAssignment.sheetName()), uploadDto.getControlAssignments(), controlMap, pathMap);
            generateControlPollutantExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.ControlPollutant.sheetName()), uploadDto.getControlPollutants(), controlMap);
            generateApportionmentExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.ReleasePointAppt.sheetName()), uploadDto.getReleasePointAppts(), rpMap, epMap, pathMap);
            generateReportingPeriodExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.ReportingPeriod.sheetName()), uploadDto.getReportingPeriods(), epMap);
            generateOperatingDetailExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.OperatingDetail.sheetName()), uploadDto.getOperatingDetails(), periodMap);
            generateEmissionExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.Emission.sheetName()), uploadDto.getEmissions(), periodMap);
            generateEmissionFormulaVariableExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.EmissionFormulaVariable.sheetName()), 
                    uploadDto.getEmissionFormulaVariables(), emissionMap);

            wb.setForceFormulaRecalculation(true);
            wb.write(outputStream);
            wb.close();

            logger.info("Finish generate excel");

        } catch (IOException | EncryptedDocumentException | InvalidFormatException | URISyntaxException ex) {

            logger.error("Unable to generate Excel export ", ex);
            throw new IllegalStateException(ex);
        }

    }

    /**
     * Map facility site into the facility site excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateFacilityExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet, List<FacilitySiteBulkUploadDto> dtos) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (FacilitySiteBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            row.getCell(2).setCellValue(dto.getFrsFacilityId());
            row.getCell(3).setCellValue(dto.getAltSiteIdentifier());
            row.getCell(4).setCellValue(dto.getFacilityCategoryCode());
//                row.getCell(5).setCellValue(dto.getFacilitySourceTypeCode());
            if (dto.getFacilitySourceTypeCode() != null) {
                // find the display name using the code in an excel lookup similar to how the code is found for the dropdown
                // generates a lookup formula then evaluates it to get the correct value using evaluateInCell so that the formula is removed afterwards
                row.getCell(6).setCellFormula(generateLookupFormula(wb, "FacilitySourceTypeCode", dto.getFacilitySourceTypeCode(), false));
                formulaEvaluator.evaluateInCell(row.getCell(6));
            }
            row.getCell(7).setCellValue(dto.getName());
            row.getCell(8).setCellValue(dto.getDescription());
//                row.getCell(9).setCellValue(dto.getOperatingStatusCode());
            if (dto.getOperatingStatusCode() != null) {
                row.getCell(10).setCellFormula(generateLookupFormula(wb, "OperatingStatusCode", dto.getOperatingStatusCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(10));
            }
            setCellNumberValue(row.getCell(11), dto.getStatusYear());
//                row.getCell(12).setCellValue(dto.getProgramSystemCode());
            if (dto.getProgramSystemCode() != null) {
                row.getCell(13).setCellFormula(generateLookupFormula(wb, "ProgramSystemCode", dto.getProgramSystemCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(13));
            }
            row.getCell(14).setCellValue(dto.getStreetAddress());
            row.getCell(15).setCellValue(dto.getCity());
            row.getCell(16).setCellValue(dto.getStateFipsCode());
            row.getCell(17).setCellValue(dto.getStateCode());
            row.getCell(18).setCellValue(dto.getCountyCode());
            row.getCell(19).setCellValue(String.format("%s (%s)", dto.getCounty(), dto.getStateCode()));
//                row.getCell(20).setCellValue(dto.getCountryCode());
            row.getCell(21).setCellValue(dto.getPostalCode());
            setCellNumberValue(row.getCell(22), dto.getLatitude());
            setCellNumberValue(row.getCell(23), dto.getLongitude());
            row.getCell(24).setCellValue(dto.getMailingStreetAddress());
            row.getCell(25).setCellValue(dto.getMailingCity());
            row.getCell(26).setCellValue(dto.getMailingStateCode());
            row.getCell(27).setCellValue(dto.getMailingPostalCode());
//                row.getCell(28).setCellValue(dto.getMailingCountryCode());
            row.getCell(29).setCellValue(dto.getEisProgramId());
//                row.getCell(30).setCellValue(dto.getTribalCode());
            if (dto.getTribalCode() != null) {
                row.getCell(31).setCellFormula(generateLookupFormula(wb, "TribalCode", dto.getTribalCode(), false));
                formulaEvaluator.evaluateInCell(row.getCell(31));
            }

            currentRow++;

        }
    }

    /**
     * Map facility contacts into the facility contacts excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateFacilityContactExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet, List<FacilitySiteContactBulkUploadDto> dtos) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for(FacilitySiteContactBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getType() != null) {
                row.getCell(4).setCellFormula(generateLookupFormula(wb, "ContactTypeCode", dto.getType(), true));
                formulaEvaluator.evaluateInCell(row.getCell(4));
            }
            row.getCell(5).setCellValue(dto.getPrefix());
            row.getCell(6).setCellValue(dto.getFirstName());
            row.getCell(7).setCellValue(dto.getLastName());
            row.getCell(8).setCellValue(dto.getEmail());
            setCellNumberValue(row.getCell(9), dto.getPhone());
            row.getCell(10).setCellValue(dto.getPhoneExt());
            row.getCell(11).setCellValue(dto.getStreetAddress());
            row.getCell(12).setCellValue(dto.getCity());
//            row.getCell(13).setCellValue(dto.getStateFipsCode());
            row.getCell(14).setCellValue(dto.getStateCode());
//            row.getCell(15).setCellValue(dto.getCountyCode());
            row.getCell(16).setCellValue(String.format("%s (%s)", dto.getCounty(), dto.getStateCode()));
//            row.getCell(17).setCellValue(dto.getCountryCode());
            row.getCell(18).setCellValue(dto.getPostalCode());
            row.getCell(19).setCellValue(dto.getMailingStreetAddress());
            row.getCell(20).setCellValue(dto.getMailingCity());
            row.getCell(21).setCellValue(dto.getMailingStateCode());
//            row.getCell(22).setCellValue(dto.getMailingCountryCode());
            row.getCell(23).setCellValue(dto.getMailingPostalCode());
//            row.getCell().setCellValue(dto.);

            currentRow++;

        }
    }

    /**
     * Map NAICS into the NAICS excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateNAICSExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet, List<FacilityNAICSBulkUploadDto> dtos) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        // The last 2 columns for NAICS codes were not leading the correct style and were defaulting
        // to general data type and locked. This will get the overall column style for the columns
        // and use them instead which have the correct data types and are unlocked

        // general data type and unlocked
        CellStyle unlockedStyle = wb.createCellStyle();
        unlockedStyle.cloneStyleFrom(sheet.getColumnStyle(4));

        // text datatype and unlocked
        CellStyle tfStyle = wb.createCellStyle();
        tfStyle.cloneStyleFrom(sheet.getColumnStyle(5));

        for (FacilityNAICSBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getCode() != null) {
                row.getCell(3).setCellValue(dto.getCode());

                row.getCell(4).setCellStyle(unlockedStyle);
                row.getCell(4).setCellFormula(generateLookupFormula(wb, "NaicsCode", dto.getCode(), false));
                formulaEvaluator.evaluateInCell(row.getCell(4));
            }
            row.getCell(5).setCellStyle(tfStyle);
            row.getCell(5).setCellValue("" + dto.isPrimaryFlag());

            currentRow++;

        }

    }

    /**
     * Map release points into the release points excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateReleasePointsExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet, List<ReleasePointBulkUploadDto> dtos) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (ReleasePointBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            row.getCell(2).setCellValue(dto.getReleasePointIdentifier());
            if (dto.getTypeCode() != null) {
                row.getCell(4).setCellValue(dto.getTypeCode());
                row.getCell(5).setCellFormula(generateLookupFormula(wb, "ReleasePointTypeCode", dto.getTypeCode(), false));
                formulaEvaluator.evaluateInCell(row.getCell(5));
            }
            row.getCell(6).setCellValue(dto.getDescription());
            if (dto.getOperatingStatusCode() != null) {
                row.getCell(7).setCellValue(dto.getOperatingStatusCode());
                row.getCell(8).setCellFormula(generateLookupFormula(wb, "OperatingStatusCode", dto.getOperatingStatusCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(8));
            }
            setCellNumberValue(row.getCell(9), dto.getStatusYear());
            setCellNumberValue(row.getCell(10), dto.getLatitude());
            setCellNumberValue(row.getCell(11), dto.getLongitude());
            setCellNumberValue(row.getCell(12), dto.getFugitiveLine1Latitude());
            setCellNumberValue(row.getCell(13), dto.getFugitiveLine1Longitude());
            setCellNumberValue(row.getCell(14), dto.getFugitiveLine2Latitude());
            setCellNumberValue(row.getCell(15), dto.getFugitiveLine2Longitude());
            setCellNumberValue(row.getCell(16), dto.getStackHeight());
            row.getCell(17).setCellValue(dto.getStackHeightUomCode());
            setCellNumberValue(row.getCell(18), dto.getStackDiameter());
            row.getCell(19).setCellValue(dto.getStackDiameterUomCode());
            setCellNumberValue(row.getCell(20), dto.getExitGasVelocity());
            row.getCell(21).setCellValue(dto.getExitGasVelocityUomCode());
            setCellNumberValue(row.getCell(22), dto.getExitGasTemperature());
            setCellNumberValue(row.getCell(23), dto.getExitGasFlowRate());
            row.getCell(24).setCellValue(dto.getExitGasFlowUomCode());
            setCellNumberValue(row.getCell(25), dto.getFenceLineDistance());
            row.getCell(26).setCellValue(dto.getFenceLineUomCode());
            setCellNumberValue(row.getCell(27), dto.getFugitiveHeight());
            row.getCell(28).setCellValue(dto.getFugitiveHeightUomCode());
            setCellNumberValue(row.getCell(29), dto.getFugitiveWidth());
            row.getCell(30).setCellValue(dto.getFugitiveWidthUomCode());
            setCellNumberValue(row.getCell(31), dto.getFugitiveLength());
            row.getCell(32).setCellValue(dto.getFugitiveLengthUomCode());
            setCellNumberValue(row.getCell(33), dto.getFugitiveAngle());
            row.getCell(34).setCellValue(dto.getComments());

            currentRow++;

            dto.setRow(currentRow);

        }

    }

    /**
     * Map emissions units into the emissions units excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateEmissionUnitExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet, List<EmissionsUnitBulkUploadDto> dtos) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (EmissionsUnitBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            row.getCell(2).setCellValue(dto.getUnitIdentifier());
            row.getCell(4).setCellValue(dto.getDescription());
            if (dto.getTypeCode() != null) {
                row.getCell(6).setCellFormula(generateLookupFormula(wb, "UnitTypeCode", dto.getTypeCode(), false));
                formulaEvaluator.evaluateInCell(row.getCell(6));
            }
            if (dto.getOperatingStatusCodeDescription() != null) {
                row.getCell(8).setCellFormula(generateLookupFormula(wb, "OperatingStatusCode", dto.getOperatingStatusCodeDescription(), true));
                formulaEvaluator.evaluateInCell(row.getCell(8));
            }
            setCellNumberValue(row.getCell(9), dto.getStatusYear());
            setCellNumberValue(row.getCell(10), dto.getDesignCapacity());
            row.getCell(11).setCellValue(dto.getUnitOfMeasureCode());
            row.getCell(12).setCellValue(dto.getComments());

            currentRow++;

            dto.setRow(currentRow);

        }

    }

    /**
     * Map emissions processes into the emissions processes excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateProcessesExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet, 
            List<EmissionsProcessBulkUploadDto> dtos, Map<Long, EmissionsUnitBulkUploadDto> euMap) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (EmissionsProcessBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            row.getCell(2).setCellValue(euMap.get(dto.getEmissionsUnitId()).getUnitIdentifier());
            row.getCell(3).setCellValue(dto.getEmissionsProcessIdentifier());
            row.getCell(6).setCellValue(dto.getDescription());
            if (dto.getOperatingStatusCode() != null) {
                row.getCell(7).setCellValue(dto.getOperatingStatusCode());
                row.getCell(8).setCellFormula(generateLookupFormula(wb, "OperatingStatusCode", dto.getOperatingStatusCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(8));
            }
            setCellNumberValue(row.getCell(9), dto.getStatusYear());
            // using the double version of setCellValue since the spreadsheet expects this value to display as a number
            setCellNumberValue(row.getCell(11), dto.getSccCode());
            if (dto.getAircraftEngineTypeCode() != null) {
                row.getCell(12).setCellValue(dto.getAircraftEngineTypeCode());
                row.getCell(13).setCellFormula(generateLookupFormula(wb, "AircraftEngineTypeCode", dto.getAircraftEngineTypeCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(13));
            }
            row.getCell(14).setCellValue(dto.getComments());

            currentRow++;

            // store values to be used in later sheets; after row increments to deal with difference between 0-based and 1-based
            dto.setRow(currentRow);
            dto.setDisplayName(euMap.get(dto.getEmissionsUnitId()).getUnitIdentifier() + "-" + dto.getEmissionsProcessIdentifier());

        }

    }

    /**
     * Map controls into the controls excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateControlsExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet, List<ControlBulkUploadDto> dtos) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (ControlBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            row.getCell(2).setCellValue(dto.getIdentifier());
            row.getCell(4).setCellValue(dto.getDescription());
            setCellNumberValue(row.getCell(5), dto.getPercentCapture());
            setCellNumberValue(row.getCell(6), dto.getPercentControl());
            if (dto.getOperatingStatusCode() != null) {
                row.getCell(7).setCellValue(dto.getOperatingStatusCode());
                row.getCell(8).setCellFormula(generateLookupFormula(wb, "OperatingStatusCode", dto.getOperatingStatusCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(8));
            }
            if (dto.getControlMeasureCode() != null) {
                row.getCell(9).setCellValue(dto.getControlMeasureCode());
                row.getCell(10).setCellFormula(generateLookupFormula(wb, "ControlMeasureCode", dto.getControlMeasureCode(), false));
                formulaEvaluator.evaluateInCell(row.getCell(10));
            }
            row.getCell(11).setCellValue(dto.getComments());

            currentRow++;

            dto.setRow(currentRow);

        }

    }

    /**
     * Map control paths into the control path excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateControlPathsExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet, List<ControlPathBulkUploadDto> dtos) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (ControlPathBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            row.getCell(2).setCellValue(dto.getPathId());
            row.getCell(3).setCellValue(dto.getDescription());

            currentRow++;

            dto.setRow(currentRow);

        }

    }

    /**
     * Map control assignments into the control assignments excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateControlAssignmentsExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet,
            List<ControlAssignmentBulkUploadDto> dtos, Map<Long, ControlBulkUploadDto> controlMap,
            Map<Long, ControlPathBulkUploadDto> pathMap) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (ControlAssignmentBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getControlPathId() != null) {
                row.getCell(2).setCellValue(pathMap.get(dto.getControlPathId()).getRow());
                row.getCell(3).setCellValue(pathMap.get(dto.getControlPathId()).getPathId());
            }
            if (dto.getControlId() != null) {
                row.getCell(4).setCellValue(controlMap.get(dto.getControlId()).getRow());
                row.getCell(5).setCellValue(controlMap.get(dto.getControlId()).getIdentifier());
            }
            if (dto.getControlPathChildId() != null) {
                row.getCell(6).setCellValue(pathMap.get(dto.getControlPathChildId()).getRow());
                row.getCell(7).setCellValue(pathMap.get(dto.getControlPathChildId()).getPathId());
            }
            setCellNumberValue(row.getCell(8), dto.getSequenceNumber());
            setCellNumberValue(row.getCell(9), dto.getPercentApportionment());

            currentRow++;

        }

    }

    /**
     * Map control pollutants into the control pollutant excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateControlPollutantExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet,
            List<ControlPollutantBulkUploadDto> dtos, Map<Long, ControlBulkUploadDto> controlMap) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (ControlPollutantBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getControlId() != null) {
                row.getCell(2).setCellValue(controlMap.get(dto.getControlId()).getRow());
                row.getCell(3).setCellValue(controlMap.get(dto.getControlId()).getIdentifier());
            }
            if (dto.getPollutantCode() != null) {
                row.getCell(4).setCellValue(dto.getPollutantCode());
                // check if the code is a number or not when looking it up
                row.getCell(5).setCellFormula(generateLookupFormula(wb, "Pollutant", dto.getPollutantCode(), !NumberUtils.isCreatable(dto.getPollutantCode())));
                formulaEvaluator.evaluateInCell(row.getCell(5));
            }
            setCellNumberValue(row.getCell(6), dto.getPercentReduction());

            currentRow++;

        }

    }

    /**
     * Map apportionments into the apportionment excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateApportionmentExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet,
            List<ReleasePointApptBulkUploadDto> dtos, Map<Long, ReleasePointBulkUploadDto> rpMap,
            Map<Long, EmissionsProcessBulkUploadDto> epMap, Map<Long, ControlPathBulkUploadDto> pathMap) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (ReleasePointApptBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getReleasePointId() != null) {
                row.getCell(2).setCellValue(rpMap.get(dto.getReleasePointId()).getRow());
                row.getCell(3).setCellValue(rpMap.get(dto.getReleasePointId()).getReleasePointIdentifier());
            }
            if (dto.getEmissionProcessId() != null) {
                row.getCell(4).setCellValue(epMap.get(dto.getEmissionProcessId()).getRow());
                row.getCell(5).setCellValue(epMap.get(dto.getEmissionProcessId()).getDisplayName());
            }
            if (dto.getControlPathId() != null) {
                row.getCell(6).setCellValue(pathMap.get(dto.getControlPathId()).getRow());
                row.getCell(7).setCellValue(pathMap.get(dto.getControlPathId()).getPathId());
            }
            setCellNumberValue(row.getCell(8), dto.getPercent());

            currentRow++;

        }

    }

    /**
     * Map reporting periods into the reporting period excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateReportingPeriodExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet,
            List<ReportingPeriodBulkUploadDto> dtos, Map<Long, EmissionsProcessBulkUploadDto> epMap) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (ReportingPeriodBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getEmissionsProcessId() != null) {
                row.getCell(1).setCellValue(epMap.get(dto.getEmissionsProcessId()).getRow());
                row.getCell(2).setCellValue(epMap.get(dto.getEmissionsProcessId()).getDisplayName());
            }
            if (dto.getReportingPeriodTypeCode() != null) {
                row.getCell(5).setCellValue(dto.getReportingPeriodTypeCode());
                row.getCell(6).setCellFormula(generateLookupFormula(wb, "ReportingPeriodTypeCode", dto.getReportingPeriodTypeCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(6));
            }
            if (dto.getEmissionsOperatingTypeCode() != null) {
                row.getCell(7).setCellValue(dto.getEmissionsOperatingTypeCode());
                row.getCell(8).setCellFormula(generateLookupFormula(wb, "EmissionsOperatingTypeCode", dto.getEmissionsOperatingTypeCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(8));
            }
            if (dto.getCalculationParameterTypeCode() != null) {
                row.getCell(9).setCellValue(dto.getCalculationParameterTypeCode());
                row.getCell(10).setCellFormula(generateLookupFormula(wb, "CalculationParameterTypeCode", dto.getCalculationParameterTypeCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(10));
            }
            setCellNumberValue(row.getCell(11), dto.getCalculationParameterValue());
            row.getCell(12).setCellValue(dto.getCalculationParameterUom());
            if (dto.getCalculationMaterialCode() != null) {
                row.getCell(13).setCellValue(dto.getCalculationMaterialCode());
                row.getCell(14).setCellFormula(generateLookupFormula(wb, "CalculationMaterialCode", dto.getCalculationMaterialCode(), false));
                formulaEvaluator.evaluateInCell(row.getCell(14));
            }
            row.getCell(15).setCellValue(dto.getComments());

            currentRow++;

            dto.setRow(currentRow);
            // have to pull value from cell since we don't have this value anywhere else
            dto.setDisplayName(epMap.get(dto.getEmissionsProcessId()).getDisplayName() + "-" + row.getCell(6).getStringCellValue());

        }

    }

    /**
     * Map operating details into the operating details excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateOperatingDetailExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet,
            List<OperatingDetailBulkUploadDto> dtos, Map<Long, ReportingPeriodBulkUploadDto> periodMap) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (OperatingDetailBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getReportingPeriodId() != null) {
                row.getCell(2).setCellValue(periodMap.get(dto.getReportingPeriodId()).getRow());
                row.getCell(3).setCellValue(periodMap.get(dto.getReportingPeriodId()).getDisplayName());
            }
            setCellNumberValue(row.getCell(4), dto.getActualHoursPerPeriod());
            setCellNumberValue(row.getCell(5), dto.getAverageHoursPerDay());
            setCellNumberValue(row.getCell(6), dto.getAverageDaysPerWeek());
            setCellNumberValue(row.getCell(7), dto.getAverageWeeksPerPeriod());
            setCellNumberValue(row.getCell(8), dto.getPercentWinter());
            setCellNumberValue(row.getCell(9), dto.getPercentSpring());
            setCellNumberValue(row.getCell(10), dto.getPercentSummer());
            setCellNumberValue(row.getCell(11), dto.getPercentFall());

            currentRow++;

        }

    }

    /**
     * Map emissions into the emission excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateEmissionExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet,
            List<EmissionBulkUploadDto> dtos, Map<Long, ReportingPeriodBulkUploadDto> periodMap) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (EmissionBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getReportingPeriodId() != null) {
                row.getCell(1).setCellValue(periodMap.get(dto.getReportingPeriodId()).getRow());
                row.getCell(2).setCellValue(periodMap.get(dto.getReportingPeriodId()).getDisplayName());
            }
            if (dto.getPollutantCode() != null) {
                row.getCell(3).setCellValue(dto.getPollutantCode());
                // check if the code is a number or not when looking it up
                row.getCell(4).setCellFormula(generateLookupFormula(wb, "Pollutant", dto.getPollutantCode(), !NumberUtils.isCreatable(dto.getPollutantCode())));
                formulaEvaluator.evaluateInCell(row.getCell(4));
            }
            row.getCell(5).setCellValue("" + dto.isTotalManualEntry());
            setCellNumberValue(row.getCell(6), dto.getTotalEmissions());
            row.getCell(7).setCellValue(dto.getEmissionsUomCode());
            setCellNumberValue(row.getCell(8), dto.getOverallControlPercent());
            // don't include EF for formula emissions
            if (Strings.emptyToNull(dto.getEmissionsFactorFormula()) == null) {
                setCellNumberValue(row.getCell(9), dto.getEmissionsFactor());
            }
            row.getCell(10).setCellValue(dto.getEmissionsFactorText());
            
            row.getCell(13).setCellValue(dto.getEmissionsFactorFormula());
            if (dto.getEmissionsCalcMethodCode() != null) {
                row.getCell(14).setCellValue(dto.getEmissionsCalcMethodCode());
                row.getCell(15).setCellFormula(generateLookupFormula(wb, "CalculationMethodCode", dto.getEmissionsCalcMethodCode(), false));
                formulaEvaluator.evaluateInCell(row.getCell(15));
            }
            row.getCell(16).setCellValue(dto.getEmissionsNumeratorUom());
            row.getCell(17).setCellValue(dto.getEmissionsDenominatorUom());
            row.getCell(18).setCellValue(dto.getCalculationComment());
            row.getCell(19).setCellValue(dto.getComments());

            currentRow++;
            
            dto.setRow(currentRow);
            // have to pull value from cell since we don't have this value anywhere else
            dto.setDisplayName(String.format("%s (%s)", periodMap.get(dto.getReportingPeriodId()).getDisplayName(), row.getCell(4).getStringCellValue()));

        }

    }

    /**
     * Map emission formula variables into the emission formula variable excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateEmissionFormulaVariableExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet,
            List<EmissionFormulaVariableBulkUploadDto> dtos, Map<Long, EmissionBulkUploadDto> emissionMap) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (EmissionFormulaVariableBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getEmissionId() != null) {
                row.getCell(2).setCellValue(emissionMap.get(dto.getEmissionId()).getRow());
                row.getCell(4).setCellValue(emissionMap.get(dto.getEmissionId()).getDisplayName());
            }
            if (dto.getEmissionFormulaVariableCode() != null) {
                row.getCell(5).setCellValue(dto.getEmissionFormulaVariableCode());
                row.getCell(6).setCellFormula(generateLookupFormula(wb, "EmissionFormulaVariable", dto.getEmissionFormulaVariableCode(), true));
                formulaEvaluator.evaluateInCell(row.getCell(6));
            }
            setCellNumberValue(row.getCell(7), dto.getValue());

            currentRow++;

        }

    }

    /**
     * Set the value of a cell as a number for formatting
     * @param cell
     * @param value
     */
    private void setCellNumberValue(Cell cell, String value) {
        Double numVal = toDouble(value);
        if (numVal != null) {
            cell.setCellValue(numVal);
        }
    }

    /**
     * Generate a basic reverse lookup formula for creating excel exports.
     * The formula will find the dropdown value for a code in a basic lookup sheet in excel
     * @param workbook
     * @param sheetName
     * @param value the code to lookup
     * @param text if the code is text or number in excel
     * @return
     */
    private String generateLookupFormula(Workbook workbook, String sheetName, String value, boolean text) {

        int rowCount = workbook.getSheet(sheetName).getLastRowNum() + 1;
        String result;
        // if the code is a number in excel we need to make sure it's a number here too so it will match
        if (text) {
            result = String.format(EXCEL_GENERIC_LOOKUP_TEXT, sheetName, rowCount, value, sheetName, rowCount);
        } else {
            result = String.format(EXCEL_GENERIC_LOOKUP_NUMBER, sheetName, rowCount, value, sheetName, rowCount);
        }
//        logger.info(result);
        return result;
    }

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
        Optional<EmissionsReport> previousReport = this.emissionsReportService.retrieveByEisProgramIdAndYear(bulkEmissionsReport.getEisProgramId(), bulkEmissionsReport.getYear());
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
        	reportToUpdate.setStatus(ReportStatus.IN_PROGRESS);
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

        emissionsReport.setAgencyCode(bulkEmissionsReport.getAgencyCode());
        emissionsReport.setEisProgramId(bulkEmissionsReport.getEisProgramId());
        emissionsReport.setFrsFacilityId(bulkEmissionsReport.getFrsFacilityId());
        emissionsReport.setYear(bulkEmissionsReport.getYear());
        emissionsReport.setStatus(ReportStatus.valueOf(bulkEmissionsReport.getStatus()));
        emissionsReport.setEisLastSubmissionStatus(EisSubmissionStatus.NotStarted);

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

        facility.setFrsFacilityId(bulkFacility.getFrsFacilityId());
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

    private EmissionsReportBulkUploadDto parseWorkbookJson(ExcelParserResponse response,
                                                           EmissionsReportStarterDto metadata) {

       return parseJsonNode(true).andThen(result -> {

           result.setAgencyCode(EmissionsReportService.__HARD_CODED_AGENCY_CODE__);
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
