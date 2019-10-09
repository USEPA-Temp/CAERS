package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.validation.ValidationFeature;
import gov.epa.cef.web.service.validation.ValidationResult;

public interface EmissionsReportValidationService {

    /**
     * Validate emissions report
     *
     * @param reportId
     * @return
     */
    ValidationResult validate(long reportId, ValidationFeature... features);

    /**
     * Validate emissions report
     *
     * @param report
     * @return
     */
    ValidationResult validate(EmissionsReport report, ValidationFeature... features);

    /**
     * Validate emissions report, updating validation status accordingly
     *
     * @param reportId
     * @return
     */
    ValidationResult validateAndUpdateStatus(long reportId, ValidationFeature... features);
}
