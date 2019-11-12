package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;

public interface BulkUploadService {

    /**
     * Save the emissions report to the database.
     * @param bulkEmissionsReport
     * @return
     */
    EmissionsReportDto saveBulkEmissionReport(EmissionsReportBulkUploadDto bulkEmissionsReport);

    /**
     * Testing method for generating upload JSON for a report
     * @param reportId
     * @return
     */
    EmissionsReportBulkUploadDto generateBulkUploadDto(Long reportId);

}