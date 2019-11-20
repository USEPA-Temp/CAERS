package gov.epa.cef.web.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.LookupService;
import gov.epa.cef.web.service.dto.CalculationMethodCodeDto;
import gov.epa.cef.web.service.dto.CodeLookupDto;
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

}
