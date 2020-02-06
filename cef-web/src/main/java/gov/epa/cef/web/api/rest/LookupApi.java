package gov.epa.cef.web.api.rest;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.LookupService;
import gov.epa.cef.web.service.dto.AircraftEngineTypeCodeDto;
import gov.epa.cef.web.service.dto.CalculationMethodCodeDto;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.EisLatLongToleranceLookupDto;
import gov.epa.cef.web.service.dto.FacilityNAICSDto;
import gov.epa.cef.web.service.dto.FipsStateCodeDto;
import gov.epa.cef.web.service.dto.PointSourceSccCodeDto;
import gov.epa.cef.web.service.dto.PollutantDto;
import gov.epa.cef.web.service.dto.UnitMeasureCodeDto;

@RestController
@RequestMapping("/api/lookup")
public class LookupApi {

    @Autowired
    private LookupService lookupService;

    /**
     * Retrieve Calculation Material codes
     * @return
     */
    @GetMapping(value = "/calculation/material")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveCalcMaterialCodes() {

        List<CodeLookupDto> result = lookupService.retrieveCalcMaterialCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Calculation Method codes
     * @return
     */
    @GetMapping(value = "/calculation/method")
    @ResponseBody
    public ResponseEntity<List<CalculationMethodCodeDto>> retrieveCalcMethodCodes() {

        List<CalculationMethodCodeDto> result = lookupService.retrieveCalcMethodCodes();
        return new ResponseEntity<List<CalculationMethodCodeDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Calculation Parameter Type codes
     * @return
     */
    @GetMapping(value = "/calculation/parameter")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveCalcParamTypeCodes() {

        List<CodeLookupDto> result = lookupService.retrieveCalcParamTypeCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Operating Status codes
     * @return
     */
    @GetMapping(value = "/operatingStatus")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveOperatingStatusCodes() {

        List<CodeLookupDto> result = lookupService.retrieveOperatingStatusCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Emissions Operating Type Codes
     * @return
     */
    @GetMapping(value = "/emissionsOperatingType")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveEmissionOperatingTypeCodes() {

        List<CodeLookupDto> result = lookupService.retrieveEmissionOperatingTypeCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Pollutants
     * @return
     */
    @GetMapping(value = "/pollutant")
    @ResponseBody
    public ResponseEntity<List<PollutantDto>> retrievePollutants() {

        List<PollutantDto> result = lookupService.retrievePollutants();
        return new ResponseEntity<List<PollutantDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Reporting Period codes
     * @return
     */
    @GetMapping(value = "/reportingPeriod")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveReportingPeriodCodes() {

        List<CodeLookupDto> result = lookupService.retrieveReportingPeriodCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }

    
    /**
     * Retrieve Contact Types codes
     * @return
     */
    @GetMapping(value = "/unitType")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveUnitTypeCodes() {

        List<CodeLookupDto> result = lookupService.retrieveUnitTypeCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve UoM codes
     * @return
     */
    @GetMapping(value = "/uom")
    @ResponseBody
    public ResponseEntity<List<UnitMeasureCodeDto>> retrieveUnitMeasureCodes() {

        List<UnitMeasureCodeDto> result = lookupService.retrieveUnitMeasureCodes();
        return new ResponseEntity<List<UnitMeasureCodeDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Contact Types codes
     * @return
     */
    @GetMapping(value = "/contactType")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveContactTypeCodes() {

        List<CodeLookupDto> result = lookupService.retrieveContactTypeCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Fips State codes
     * @return
     */
    @GetMapping(value = "/stateCode")
    @ResponseBody
    public ResponseEntity<List<FipsStateCodeDto>> retrieveStateCodes() {

        List<FipsStateCodeDto> result = lookupService.retrieveStateCodes();
        return new ResponseEntity<List<FipsStateCodeDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Release Point Type codes
     * @return
     */
    @GetMapping(value = "/releaseType")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveReleasePointTypeCodes() {

        List<CodeLookupDto> result = lookupService.retrieveReleasePointTypeCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Program System Type codes
     * @return
     */
    @GetMapping(value = "/programSystemType")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveProgramSystemTypeCodes() {

        List<CodeLookupDto> result = lookupService.retrieveProgramSystemTypeCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Control Measure codes
     * @return
     */
    @GetMapping(value = "/controlMeasure")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveControlMeasureCodes() {

        List<CodeLookupDto> result = lookupService.retrieveControlMeasureCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Tribal Codes
     * @return
     */
    @GetMapping(value = "/tribalCode")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveTribalCodes() {

        List<CodeLookupDto> result = lookupService.retrieveTribalCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Facility NAICS Codes
     * @return
     */
    @GetMapping(value = "/naicsCode")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveNaicsCode() {

        List<CodeLookupDto> result = lookupService.retrieveNaicsCode();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Aircraft Engine Type codes
     * @return
     */
    @GetMapping(value = "/aircraftEngineCode")
    @ResponseBody
    public ResponseEntity<List<AircraftEngineTypeCodeDto>> retrieveAircraftEngineCodes() {

        List<AircraftEngineTypeCodeDto> result = lookupService.retrieveAircraftEngineCodes();
        return new ResponseEntity<List<AircraftEngineTypeCodeDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Point Source SCC code
     * @param code
     * @return
     */
    @GetMapping(value = "/pointSourceSccCode/{code}")
    @ResponseBody
    public ResponseEntity<PointSourceSccCodeDto> retrievePointSourceSccCode(@NotNull @PathVariable String code) {
    	
    	PointSourceSccCodeDto result = lookupService.retrievePointSourceSccCode(code);
    	return new ResponseEntity<PointSourceSccCodeDto>(result, HttpStatus.OK);
    }   
    
    /**
     * Retrieve coordinate tolerance by eisProgramId
     * @param eisProgramId
     * @return
     */
    @GetMapping(value = "/coordinateTolerance/{eisProgramId}")
    @ResponseBody
    public ResponseEntity<EisLatLongToleranceLookupDto> retrieveLatLongTolerance(@NotNull @PathVariable String eisProgramId) {
    	EisLatLongToleranceLookupDto result = lookupService.retrieveLatLongTolerance(eisProgramId);
    	return new ResponseEntity<EisLatLongToleranceLookupDto>(result, HttpStatus.OK);
    }   
}
