package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EmissionsReportStarterDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.util.TempFile;

public interface BulkUploadService {

    /**
     * Upload, Parse and Save the emissions report to the database.
     * @param metadata
     * @param workbook
     * @return
     */
    EmissionsReportDto uploadBulkReport(EmissionsReportStarterDto metadata, TempFile workbook);

    /**
     * Save the emissions report to the database.
     * @param bulkEmissionsReport
     * @return
     */
    EmissionsReportDto saveBulkEmissionsReport(EmissionsReportBulkUploadDto bulkEmissionsReport);

    /**
     * Testing method for generating upload JSON for a report
     * @param reportId
     * @return
     */
    EmissionsReportBulkUploadDto generateBulkUploadDto(Long reportId);

}
