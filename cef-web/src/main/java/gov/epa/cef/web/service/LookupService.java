package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.domain.CalculationMaterialCode;
import gov.epa.cef.web.domain.CalculationParameterTypeCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.service.dto.CodeLookupDto;

public interface LookupService {

    /**
     * Retrieve Calculation Material codes
     * @return
     */
    List<CodeLookupDto> retrieveCalcMaterialCodes();

    CalculationMaterialCode retrieveCalcMaterialCodeEntityByCode(String code);

    /**
     * Retrieve Calculation Parameter Type codes
     * @return
     */
    List<CodeLookupDto> retrieveCalcParamTypeCodes();

    CalculationParameterTypeCode retrieveCalcParamTypeCodeEntityByCode(String code);

    /**
     * Retrieve Operating Status codes
     * @return
     */
    List<CodeLookupDto> retrieveOperatingStatusCodes();

    OperatingStatusCode retrieveOperatingStatusCodeEntityByCode(String code);

    /**
     * Retrieve Reporting Period codes
     * @return
     */
    List<CodeLookupDto> retrieveReportingPeriodCodes();

    ReportingPeriodCode retrieveReportingPeriodCodeEntityByCode(String code);

    /**
     * Retrieve UoM codes
     * @return
     */
    List<CodeLookupDto> retrieveUnitMeasureCodes();

    UnitMeasureCode retrieveUnitMeasureCodeEntityByCode(String code);

}