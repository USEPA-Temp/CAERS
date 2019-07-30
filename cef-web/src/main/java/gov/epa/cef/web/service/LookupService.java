package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.CodeLookupDto;

public interface LookupService {

    /**
     * Retrieve Calculation Material codes
     * @return
     */
    List<CodeLookupDto> retrieveCalcMaterialCodes();

    /**
     * Retrieve Calculation Parameter Type codes
     * @return
     */
    List<CodeLookupDto> retrieveCalcParamTypeCodes();

    /**
     * Retrieve Operating Status codes
     * @return
     */
    List<CodeLookupDto> retrieveOperatingStatusCodes();

    /**
     * Retrieve Reporting Period codes
     * @return
     */
    List<CodeLookupDto> retrieveReportingPeriodCodes();

    /**
     * Retrieve UoM codes
     * @return
     */
    List<CodeLookupDto> retrieveUnitMeasureCodes();

}