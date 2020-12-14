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
import gov.epa.cef.web.service.dto.FacilityCategoryCodeDto;
import gov.epa.cef.web.service.dto.FipsCountyDto;
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
     * Retrieve Fuel Use Material codes
     * @return
     */
    @GetMapping(value = "/fuelUse/material")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveFuelUseMaterialCodes() {

        List<CodeLookupDto> result = lookupService.retrieveFuelUseMaterialCodes();
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
     * Retrieve Operating Status codes for sub-facility components
     * @return
     */
    @GetMapping(value = "/subFacilityOperatingStatus")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveSubFacilityOperatingStatusCodes() {

        List<CodeLookupDto> result = lookupService.retrieveSubFacilityOperatingStatusCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Operating Status codes for facilities
     * @return
     */
    @GetMapping(value = "/facilityOperatingStatus")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveFacilityOperatingStatusCodes() {

        List<CodeLookupDto> result = lookupService.retrieveFacilityOperatingStatusCodes();
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
     * Retrieve Pollutants for a specific year
     * @return
     */
    @GetMapping(value = "/pollutant/{year}")
    @ResponseBody
    public ResponseEntity<List<PollutantDto>> retrieveCurrentPollutants(@NotNull @PathVariable Integer year) {

        List<PollutantDto> result = lookupService.retrieveCurrentPollutants(year);
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
     * Retrieve Fuel Use UoM codes
     * @return
     */
    @GetMapping(value = "/fuelUse/uom")
    @ResponseBody
    public ResponseEntity<List<UnitMeasureCodeDto>> retrieveFuelUseUnitMeasureCodes() {

        List<UnitMeasureCodeDto> result = lookupService.retrieveFuelUseUnitMeasureCodes();
        return new ResponseEntity<List<UnitMeasureCodeDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve UoM codes
     * @return
     */
    @GetMapping(value = "/uom")
    @ResponseBody
    public ResponseEntity<List<UnitMeasureCodeDto>> retrieveUnitMeasureCodes() {

        List<UnitMeasureCodeDto> result = lookupService.retrieveCurrentUnitMeasureCodes();
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
     * Retrieve Fips Counties
     * @return
     */
    @GetMapping(value = "/county")
    @ResponseBody
    public ResponseEntity<List<FipsCountyDto>> retrieveCounties() {

        List<FipsCountyDto> result = lookupService.retrieveCountyCodes();
        return new ResponseEntity<List<FipsCountyDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Fips Counties valid for a specific year
     * @param year
     * @return
     */
    @GetMapping(value = "/county/{year}")
    @ResponseBody
    public ResponseEntity<List<FipsCountyDto>> retrieveCurrentCounties(@NotNull @PathVariable Integer year) {

        List<FipsCountyDto> result = lookupService.retrieveCurrentCounties(year);
        return new ResponseEntity<List<FipsCountyDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Fips Counties by state
     * @param stateCode
     * @return
     */
    @GetMapping(value = "/county/state/{stateCode}")
    @ResponseBody
    public ResponseEntity<List<FipsCountyDto>> retrieveCountiesForState(@PathVariable String stateCode) {

        List<FipsCountyDto> result = lookupService.retrieveCountyCodesByState(stateCode);
        return new ResponseEntity<List<FipsCountyDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Fips Counties valid for a specific year by state
     * @param stateCode
     * @return
     */
    @GetMapping(value = "/county/state/{stateCode}/{year}")
    @ResponseBody
    public ResponseEntity<List<FipsCountyDto>> retrieveCurrentCountiesForState(@PathVariable String stateCode, @NotNull @PathVariable Integer year) {

        List<FipsCountyDto> result = lookupService.retrieveCurrentCountyCodesByState(stateCode, year);
        return new ResponseEntity<List<FipsCountyDto>>(result, HttpStatus.OK);
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
     * Retrieve Program System code by description
     * @return
     */
    @GetMapping(value = "/programSystem/description/{description}")
    @ResponseBody
    public ResponseEntity<CodeLookupDto> retrieveProgramSystemCodeByDescription(@NotNull @PathVariable String description) {

        CodeLookupDto result = lookupService.retrieveProgramSystemCodeByDescription(description);
        return new ResponseEntity<CodeLookupDto>(result, HttpStatus.OK);
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
     * Retrieve Control Measure codes valid for a specific year
     * @param year
     * @return
     */
    @GetMapping(value = "/controlMeasure/{year}")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveCurrentControlMeasureCodes(@NotNull @PathVariable Integer year) {

        List<CodeLookupDto> result = lookupService.retrieveCurrentControlMeasureCodes(year);
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
     * Retrieve Facility NAICS codes valid for a specific year
     * @param year
     * @return
     */
    @GetMapping(value = "/naicsCode/{year}")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveCurrentNaicsCodes(@NotNull @PathVariable Integer year) {

        List<CodeLookupDto> result = lookupService.retrieveCurrentNaicsCodes(year);
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Aircraft Engine Type codes
     * @return
     */
    @GetMapping(value = "/aircraftEngineCode/{scc}")
    @ResponseBody
    public ResponseEntity<List<AircraftEngineTypeCodeDto>> retrieveAircraftEngineCodes(@NotNull @PathVariable String scc) {

        List<AircraftEngineTypeCodeDto> result = lookupService.retrieveAircraftEngineCodes(scc);
        return new ResponseEntity<List<AircraftEngineTypeCodeDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Aircraft Engine Type codes valid for a specific year
     * @param scc
     * @param year
     * @return
     */
    @GetMapping(value = "/aircraftEngineCode/{scc}/{year}")
    @ResponseBody
    public ResponseEntity<List<AircraftEngineTypeCodeDto>> retrieveAircraftEngineCodes(@NotNull @PathVariable String scc, @NotNull @PathVariable Integer year) {

        List<AircraftEngineTypeCodeDto> result = lookupService.retrieveCurrentAircraftEngineCodes(scc, year);
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
    
    /**
     * Retrieve Facility Category codes
     * @return
     */
    @GetMapping(value = "/facility/category")
    @ResponseBody
    public ResponseEntity<List<FacilityCategoryCodeDto>> retrieveFacilityCategoryCodes() {

        List<FacilityCategoryCodeDto> result = lookupService.retrieveFacilityCategoryCodes();
        return new ResponseEntity<List<FacilityCategoryCodeDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Facility Source Type codes
     * @return
     */
    @GetMapping(value = "/facility/sourceType")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveFacilitySourceTypeCodes() {

        List<CodeLookupDto> result = lookupService.retrieveFacilitySourceTypeCodes();
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Facility Source Type codes
     * @return
     */
    @GetMapping(value = "/facility/sourceType/{year}")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveCurrentFacilitySourceTypeCodes(@NotNull @PathVariable Integer year) {

        List<CodeLookupDto> result = lookupService.retrieveCurrentFacilitySourceTypeCodes(year);
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve Release Point Type codes valid for a specific year
     * @param year
     * @return
     */
    @GetMapping(value = "/releasePointType/{year}")
    @ResponseBody
    public ResponseEntity<List<CodeLookupDto>> retrieveCurrentReleasePointTypeCodes(@NotNull @PathVariable Integer year) {

        List<CodeLookupDto> result = lookupService.retrieveCurrentReleasePointTypeCodes(year);
        return new ResponseEntity<List<CodeLookupDto>>(result, HttpStatus.OK);
    }
}
