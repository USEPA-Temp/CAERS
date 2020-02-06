package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.CalculationMaterialCode;
import gov.epa.cef.web.domain.CalculationParameterTypeCode;
import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.ControlMeasureCode;
import gov.epa.cef.web.domain.EmissionsOperatingTypeCode;
import gov.epa.cef.web.domain.FipsStateCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ProgramSystemCode;
import gov.epa.cef.web.domain.ReleasePointTypeCode;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.TribalCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.service.dto.AircraftEngineTypeCodeDto;
import gov.epa.cef.web.service.dto.CalculationMethodCodeDto;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.EisLatLongToleranceLookupDto;
import gov.epa.cef.web.service.dto.FipsStateCodeDto;
import gov.epa.cef.web.service.dto.PollutantDto;
import gov.epa.cef.web.service.dto.UnitMeasureCodeDto;

import java.util.List;

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
     * Retrieve Calculation Method codes
     * @return
     */
    List<CalculationMethodCodeDto> retrieveCalcMethodCodes();

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
     * Retrieve Pollutants
     * @return
     */
    List<PollutantDto> retrievePollutants();

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
    List<UnitMeasureCodeDto> retrieveUnitMeasureCodes();

    /**
     * Retrieve UoM code database object by code
     * @param code
     * @return
     */
    UnitMeasureCode retrieveUnitMeasureCodeEntityByCode(String code);

    /**
     * Retrieve the list of Emission Operating Type Codes
     * @return
     */
    List<CodeLookupDto> retrieveEmissionOperatingTypeCodes();
    
    /**
     * Retrieve the Emissions Operating Type Code entity by code
     * 
     * @param code
     * @return
     */
    EmissionsOperatingTypeCode retrieveEmissionsOperatingTypeCodeEntityByCode(String code);
    
    /**
     * Retrieve Contact Type codes
     * @return
     */
    List<CodeLookupDto> retrieveContactTypeCodes();
    
    /**
     * Retrieve Contact Type code database object by code
     * @param code
     * @return
     */
    ContactTypeCode retrieveContactTypeEntityByCode(String code);
    /**
     * Retrieve Unit Type codes
     * @return
     */
    List<CodeLookupDto> retrieveUnitTypeCodes();

    /**
     * Retrieve Fips State codes
     * @return
     */
    List<FipsStateCodeDto> retrieveStateCodes();
    
    /**
     * Retrieve Fips State code database object by code
     * @param code
     * @return
     */
    FipsStateCode retrieveStateCodeEntityByCode(String code);
    
    /**
     * Retrieve the list of Release Point Type Codes
     * @return
     */
    List<CodeLookupDto> retrieveReleasePointTypeCodes();
    
    /**
     * Retrieve Release Point Type code database object by code
     * @param code
     * @return
     */
    ReleasePointTypeCode retrieveReleasePointTypeCodeEntityByCode(String code);
    
    /**
     * Retrieve the list of Program System Type Codes
     * @return
     */
    List<CodeLookupDto> retrieveProgramSystemTypeCodes();
    
    /**
     * Retrieve Program System Type code database object by code
     * @param code
     * @return
     */
    ProgramSystemCode retrieveProgramSystemTypeCodeEntityByCode(String code);
    
    /**
     * Retrieve Control Measure codes
     * @return
     */
    List<CodeLookupDto> retrieveControlMeasureCodes();
    
    /**
    * Retrieve Control Measure code database object by code
    * @param code
    * @return
    */
    ControlMeasureCode retrieveControlMeasureCodeEntityByCode(String code);
    
    /**
     * Retrieve Tribal Codes
     * @return
     */
    List<CodeLookupDto> retrieveTribalCodes();
    
    /**
     * Retrieve Tribal code database object by code
     * @param code
     * @return
     */
    TribalCode retrieveTribalCodeEntityByCode(String code);
    
    /**
     * Retrieve Facility NAICS Codes
     * @return
     */
    List<CodeLookupDto> retrieveNaicsCode();
    
    /**
     * Retrieve Aircraft Engine Type Codes
     * @return
     */
    List<AircraftEngineTypeCodeDto> retrieveAircraftEngineCodes();
    
    /**
     * Retrieve EIS latitude/longitude tolerance by EIS program id
     * @param eisProgramId
     * @return
     */
    EisLatLongToleranceLookupDto retrieveLatLongTolerance(String eisProgramId);
    
}
