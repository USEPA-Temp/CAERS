package gov.epa.cef.web.service;

import java.io.OutputStream;

import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;

public interface EmissionsReportExportService {

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

}