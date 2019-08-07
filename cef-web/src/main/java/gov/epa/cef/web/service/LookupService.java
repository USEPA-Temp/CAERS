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

    /**
     * Retrieve Calculation Material code database object by code
     * @param code
     * @return
     */
    CalculationMaterialCode retrieveCalcMaterialCodeEntityByCode(String code);

    /**
     * Retrieve Calculation Parameter Type codes
     * @return
     */
    List<CodeLookupDto> retrieveCalcParamTypeCodes();

    /**
     * Retrieve Calculation Parameter Type code database object by code
     * @param code
     * @return
     */
    CalculationParameterTypeCode retrieveCalcParamTypeCodeEntityByCode(String code);

    /**
     * Retrieve Operating Status codes
     * @return
     */
    List<CodeLookupDto> retrieveOperatingStatusCodes();

    /**
     * Retrieve Operating Status code database object by code
     * @param code
     * @return
     */
    OperatingStatusCode retrieveOperatingStatusCodeEntityByCode(String code);

    /**
     * Retrieve Reporting Period codes
     * @return
     */
    List<CodeLookupDto> retrieveReportingPeriodCodes();

    /**
     * Retrieve Reporting Period code database object by code
     * @param code
     * @return
     */
    ReportingPeriodCode retrieveReportingPeriodCodeEntityByCode(String code);

    /**
     * Retrieve UoM codes
     * @return
     */
    List<CodeLookupDto> retrieveUnitMeasureCodes();

    /**
     * Retrieve UoM code database object by code
     * @param code
     * @return
     */
    UnitMeasureCode retrieveUnitMeasureCodeEntityByCode(String code);

}