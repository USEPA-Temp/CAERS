package gov.epa.cef.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EmissionsReportStarterDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.util.TempFile;

import java.io.OutputStream;
import java.util.function.Function;

public interface BulkUploadService {

    /**
     * Testing method for generating upload JSON for a report
     *
     * @param reportId
     * @return
     */
    EmissionsReportBulkUploadDto generateBulkUploadDto(Long reportId);

    /**
     * Generate an excel spreadsheet export for a report
     * @param reportId
     * @param outputStream
     */
    void generateExcel(Long reportId, OutputStream outputStream);

    /**
     * Converts JSON to EmissionsReportUploadDto.
     * @return
     */
    Function<JsonNode, EmissionsReportBulkUploadDto> parseJsonNode(boolean failUnknownProperties);

    /**
     * Save the emissions report to the database.
     *
     * @param bulkEmissionsReport
     * @return
     */
    EmissionsReportDto saveBulkEmissionsReport(EmissionsReportBulkUploadDto bulkEmissionsReport);

    /**
     * Upload, Parse and Save the emissions report to the database.
     *
     * @param metadata
     * @param workbook
     * @return
     */
    EmissionsReportDto saveBulkWorkbook(EmissionsReportStarterDto metadata, TempFile workbook);
}
