package gov.epa.cef.web.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Functions;
import com.google.common.base.Strings;
import com.google.common.io.Resources;

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
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.service.EmissionsReportExportService;
import gov.epa.cef.web.service.EmissionsReportService;
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
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetName;
import gov.epa.cef.web.service.mapper.BulkUploadMapper;
import gov.epa.cef.web.util.TempFile;

@Service
public class EmissionsReportExportServiceImpl implements EmissionsReportExportService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String EXCEL_FILE_PATH = "excel/CEF_BulkUpload_Template.xlsx";
    private static final String EXCEL_GENERIC_LOOKUP_TEXT = "INDEX(%s!$A$2:$A$%d,MATCH(\"%s\",%s!$B$2:$B$%d,0))";
    private static final String EXCEL_GENERIC_LOOKUP_NUMBER = "INDEX(%s!$A$2:$A$%d,MATCH(%s,%s!$B$2:$B$%d,0))";
    private static final int EXCEL_MAPPING_HEADER_ROWS = 23;

    @Autowired
    private EmissionsReportService emissionsReportService;

    @Autowired
    private BulkUploadMapper uploadMapper;

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
        List<ControlPathPollutant> controlPathPollutants = controlPaths.stream()
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
        reportDto.setControlPathPollutants(uploadMapper.controlPathPollutantToDtoList(controlPathPollutants));
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
    @Override
    public synchronized void generateExcel(Long reportId, OutputStream outputStream) {

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
            generateControlPathPollutantExcelSheet(wb, formulaEvaluator, wb.getSheet(WorksheetName.ControlPathPollutant.sheetName()), uploadDto.getControlPathPollutants(), pathMap);
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
            setCellNumberValue(row.getCell(20), dto.getStackWidth());
            row.getCell(21).setCellValue(dto.getStackWidthUomCode());
            setCellNumberValue(row.getCell(22), dto.getStackLength());
            row.getCell(23).setCellValue(dto.getStackLengthUomCode());
            setCellNumberValue(row.getCell(24), dto.getExitGasVelocity());
            row.getCell(25).setCellValue(dto.getExitGasVelocityUomCode());
            setCellNumberValue(row.getCell(26), dto.getExitGasTemperature());
            setCellNumberValue(row.getCell(27), dto.getExitGasFlowRate());
            row.getCell(28).setCellValue(dto.getExitGasFlowUomCode());
            setCellNumberValue(row.getCell(29), dto.getFenceLineDistance());
            row.getCell(30).setCellValue(dto.getFenceLineUomCode());
            setCellNumberValue(row.getCell(31), dto.getFugitiveHeight());
            row.getCell(32).setCellValue(dto.getFugitiveHeightUomCode());
            setCellNumberValue(row.getCell(33), dto.getFugitiveWidth());
            row.getCell(34).setCellValue(dto.getFugitiveWidthUomCode());
            setCellNumberValue(row.getCell(35), dto.getFugitiveLength());
            row.getCell(36).setCellValue(dto.getFugitiveLengthUomCode());
            setCellNumberValue(row.getCell(37), dto.getFugitiveAngle());
            row.getCell(38).setCellValue(dto.getComments());

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
     * Map control path pollutants into the control path pollutant excel sheet
     * @param wb
     * @param formulaEvaluator
     * @param sheet
     * @param dtos
     */
    private void generateControlPathPollutantExcelSheet(Workbook wb, FormulaEvaluator formulaEvaluator, Sheet sheet,
            List<ControlPathPollutantBulkUploadDto> dtos, Map<Long, ControlPathBulkUploadDto> controlMap) {

        int currentRow = EXCEL_MAPPING_HEADER_ROWS;

        for (ControlPathPollutantBulkUploadDto dto : dtos) {
            Row row = sheet.getRow(currentRow);

            if (dto.getControlPathId() != null) {
                row.getCell(2).setCellValue(controlMap.get(dto.getControlPathId()).getRow());
                row.getCell(3).setCellValue(controlMap.get(dto.getControlPathId()).getPathId());
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
            setCellNumberValue(row.getCell(15), dto.getFuelUseValue());
            row.getCell(16).setCellValue(dto.getFuelUseUom());
            if (dto.getFuelUseMaterialCode() != null) {
                row.getCell(17).setCellValue(dto.getFuelUseMaterialCode());
                row.getCell(18).setCellFormula(generateLookupFormula(wb, "CalculationMaterialCode", dto.getFuelUseMaterialCode(), false));
                formulaEvaluator.evaluateInCell(row.getCell(18));
            }
            setCellNumberValue(row.getCell(19), dto.getHeatContentValue());
            row.getCell(20).setCellValue(dto.getHeatContentUom());
            row.getCell(21).setCellValue(dto.getComments());

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
            String query = String.format(EXCEL_GENERIC_LOOKUP_TEXT, sheetName, rowCount, value, sheetName, rowCount);
            // leave field blank if invalid value
            result = String.format("IF(ISNA(%s), \"\", %s)", query, query);
        } else {
            String query = String.format(EXCEL_GENERIC_LOOKUP_NUMBER, sheetName, rowCount, value, sheetName, rowCount);
            // leave field blank if invalid value
            result = String.format("IF(ISNA(%s), \"\", %s)", query, query);
        }
//        logger.info(result);
        return result;
    }

    private Double toDouble(String strval) {

        return Strings.isNullOrEmpty(strval) ? null : Double.parseDouble(strval);
    }
}
