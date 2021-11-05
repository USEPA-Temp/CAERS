/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.domain.CalculationMaterialCode;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsOperatingTypeCode;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySourceTypeCode;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.PointSourceSccCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.domain.UnitTypeCode;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.repository.PointSourceSccCodeRepository;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsUnitValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionsUnitValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private EmissionsUnitValidator validator;
    
    @Mock
	private PointSourceSccCodeRepository sccRepo;
    
    @Mock
    private EmissionsReportRepository reportRepo;
    
    @Mock
    private EmissionsUnitRepository unitRepo;
    
    @Before
    public void init(){

		List<PointSourceSccCode> sccList = new ArrayList<PointSourceSccCode>();
		PointSourceSccCode scc1 = new PointSourceSccCode();
		scc1.setCode("10200302");
		scc1.setFuelUseRequired(true);
		sccList.add(scc1);
		
		PointSourceSccCode scc2 = new PointSourceSccCode();
		scc2.setCode("10200301");
		scc2.setFuelUseRequired(false);
		sccList.add(scc2);
		
		PointSourceSccCode scc3 = new PointSourceSccCode();
		scc3.setCode("30503506");
		scc3.setFuelUseRequired(false);
		sccList.add(scc3);

    	when(sccRepo.findById("10200302")).thenReturn(Optional.of(scc1));
    	when(sccRepo.findById("10200301")).thenReturn(Optional.of(scc2));
    	when(sccRepo.findById("30503506")).thenReturn(Optional.of(scc3));
    	
    	List<EmissionsReport> erList = new ArrayList<EmissionsReport>();
        MasterFacilityRecord mfr = new MasterFacilityRecord();
        mfr.setId(1L);
        EmissionsReport er1 = new EmissionsReport();
        EmissionsReport er2 = new EmissionsReport();
        er1.setId(1L);
        er2.setId(2L);
        er1.setYear((short) 2018);
        er2.setYear((short) 2020);
        er1.setEisProgramId("1");
        er2.setEisProgramId("1");
        er1.setMasterFacilityRecord(mfr);
        er2.setMasterFacilityRecord(mfr);
        erList.add(er1);
        erList.add(er2);
        
        OperatingStatusCode os = new OperatingStatusCode();
        os.setCode("OP");
        
        EmissionsUnit eu = new EmissionsUnit();
        eu.setId(1L);
        eu.setOperatingStatusCode(os);
        eu.setStatusYear((short)2018);
        eu.setUnitIdentifier("Boiler 001");
        
    	when(reportRepo.findByMasterFacilityRecordId(1L)).thenReturn(erList);
    	when(reportRepo.findByMasterFacilityRecordId(2L)).thenReturn(Collections.emptyList());
    	when(unitRepo.retrieveByIdentifierFacilityYear(
          		"Boiler 001",1L,(short) 2018)).thenReturn(Collections.singletonList(eu));
    	when(unitRepo.retrieveByIdentifierFacilityYear(
                "test new",1L,(short) 2018)).thenReturn(Collections.emptyList());
    }

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void simpleValidateOperationStatusTypeFailTest() {
    	// fails when operating status is not OP and status year is null
    	// fails when current year operating status for PS/TS is null and previous year status is OP 
        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        
        OperatingStatusCode opStatusCode = new OperatingStatusCode();
        opStatusCode.setCode("TS");
        testData.setOperatingStatusCode(opStatusCode);
        testData.setStatusYear(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()).size() == 1);
    }
    
    @Test
    public void operationStatusPSCopyForwardWarningTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        
        OperatingStatusCode opStatusCode = new OperatingStatusCode();
        opStatusCode.setCode("PS");
        testData.setOperatingStatusCode(opStatusCode);
        testData.setStatusYear((short) 2020);
        
        // Operating status code of PS will generate a warning to inform users component will not be copied forward
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        FacilitySourceTypeCode stc = new FacilitySourceTypeCode();
        stc.setCode("104");
        testData.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().setFacilitySourceTypeCode(stc);
        
        // When Unit status code is PS, no processes, and source type code is landfill will generate a warning to inform users component will not be copied forward
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        cefContext = createContext();
        EmissionsProcess ep1 = new EmissionsProcess();
		ep1.setStatusYear((short) 2019);
        ep1.setOperatingStatusCode(opStatusCode);
        ep1.setSccCode("10200301"); // not required
        ep1.setEmissionsProcessIdentifier("Boiler 001");
        ep1.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep1);
        
        // When Unit status code is PS and Process status code is PS, warnings are generated to inform users components will not be copied forward
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
        
        cefContext = createContext();
        ep1.getOperatingStatusCode().setCode("OP");
        
        // No warning when Unit status code is PS, Process status code is OP, and source type code is landfill
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        testData.setOperatingStatusCode(opStatCode);
        ep1.getOperatingStatusCode().setCode("PS");
        
        // When Unit status code is OP, processes is PS will generate a warning to inform users process will not be copied forward
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void simpleValidateStatusYearRangeTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        
        testData.setStatusYear((short) 1900);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.setStatusYear((short) 2020);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void simpleValidateStatusYearRangeFailTest() {
	      
        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        
        testData.setStatusYear((short) 1800);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()).size() == 1);
        
        cefContext = createContext();
        testData.setStatusYear((short) 2021);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()).size() == 1);
    }
    
    @Test
    public void designCapacityCheckCalculationFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();

        cefContext = createContext();
        testData.setDesignCapacity(null);
        testData.setUnitOfMeasureCode(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_CAPACITY.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_CAPACITY.value()).size() == 1);
    
        //Verify QA Checks are NOT run when Unit is NOT Operating
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("TS");
        testData.setOperatingStatusCode(opStatCode);
        
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
    }
    
    @Test
    public void designCapacityFailTest() {
    	
    	CefValidatorContext cefContext = createContext();
    	EmissionsUnit testData = createBaseEmissionsUnit();
    	testData.setDesignCapacity(BigDecimal.valueOf(0.0001));
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null || cefContext.result.getErrors().size() == 1);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_CAPACITY.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_CAPACITY.value()).size() == 1);
    	
    	cefContext = createContext();
    	testData.setDesignCapacity(BigDecimal.valueOf(200000000));
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null || cefContext.result.getErrors().size() == 1);
   
        //Verify QA Checks are NOT run when Unit is NOT Operating
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("TS");
        testData.setOperatingStatusCode(opStatCode);
    	testData.setDesignCapacity(BigDecimal.valueOf(0.0001));
        
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
        
        testData.setDesignCapacity(BigDecimal.valueOf(200000000));
        
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
    }
    
    @Test
    public void uniqueIdentifierCheckFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        testData.setUnitIdentifier("test");

        EmissionsUnit em1 = new EmissionsUnit();
        em1.setUnitIdentifier("test");
        em1.setId(2L);

        testData.getFacilitySite().getEmissionsUnits().add(em1);
        testData.getFacilitySite().getEmissionsUnits().add(testData);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_IDENTIFIER.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_IDENTIFIER.value()).size() == 1);
    }
    
    @Test
    public void designCapacityAndUomRequiredFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        UnitTypeCode utc = new UnitTypeCode();
        utc.setCode("200");
        testData.setUnitTypeCode(utc);
        testData.setDesignCapacity(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_CAPACITY.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_CAPACITY.value()).size() == 1);
        
        cefContext = createContext();
        testData.setUnitOfMeasureCode(null);
        testData.setDesignCapacity(BigDecimal.valueOf(1000.0));
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_CAPACITY.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_CAPACITY.value()).size() == 1);
    
        //Verify QA Checks are NOT run when Unit is NOT Operating
        testData = createBaseEmissionsUnit();
        utc = new UnitTypeCode();
        utc.setCode("200");
        testData.setUnitTypeCode(utc);
        testData.setDesignCapacity(null);
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("TS");
        testData.setOperatingStatusCode(opStatCode);
        
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
        
        cefContext = createContext();
        testData.setUnitOfMeasureCode(null);
        testData.setDesignCapacity(BigDecimal.valueOf(1000.0));
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
    }

    @Test
    public void legacyUomFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        testData.getUnitOfMeasureCode().setLegacy(true);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_UOM.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_UOM.value()).size() == 1);
    
        //Verify QA Checks are NOT run when Unit is NOT Operating
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("TS");
        testData.setOperatingStatusCode(opStatCode);
        
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
    }

    @Test
    public void duplicateProcessIdentifierFailTest() {
    	
    	CefValidatorContext cefContext = createContext();
    	EmissionsUnit testData = createBaseEmissionsUnit();
    	OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
    	EmissionsProcess ep1 = new EmissionsProcess();
    	EmissionsProcess ep2 = new EmissionsProcess();
    	ep1.setOperatingStatusCode(opStatCode);
    	ep2.setOperatingStatusCode(opStatCode);
    	ep1.setEmissionsProcessIdentifier("AbC  ");
    	ep2.setEmissionsProcessIdentifier("ABC");
    	ep1.setEmissionsUnit(testData);
    	ep2.setEmissionsUnit(testData);
    	ep1.setSccCode("30503506");
    	ep2.setSccCode("10200301");
    	testData.getEmissionsProcesses().add(ep1);
    	testData.getEmissionsProcesses().add(ep2);
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null || cefContext.result.getErrors().size() == 1);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_PROCESS.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_PROCESS.value()).size() == 1);
    }
    
    /**
     * There should be no errors when emissions unit operating status is not PS.
     * There should be three errors when emissions unit operating status is PS and emission process operating status is not PS, 
     * PS unit and underlying process not copied forward warnings.
     * There should be two errors when emissions unit operating status is PS and emission process operating status is PS,
     * PS unit and PS process not copied forward warnings.
     */
    @Test
    public void emissionUnitOperatingStatusPSEmissionProcessStatusTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        EmissionsProcess ep = new EmissionsProcess();
        ep.setOperatingStatusCode(opStatCode);
        ep.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("PS");
        FacilitySourceTypeCode stc = new FacilitySourceTypeCode();
        stc.setCode("100");
        testData.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().setFacilitySourceTypeCode(stc);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 3);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_STATUS_CODE.value()) && errorMap.get(ValidationField.PROCESS_STATUS_CODE.value()).size() == 2);
        
        cefContext = createContext();
        ep.getOperatingStatusCode().setCode("PS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
    }
    
    /**
     * There should be no errors when emissions unit operating status is not TS or PS.
     * There should be one error when emissions unit operating status is TS and emission process operating status is not PS and not TS.
     * There should be no errors when emissions unit operating status is TS and emission process operating status is TS.
     * There should be one errors when emissions unit operating status is TS and emission process operating status is PS.
     */
    @Test
    public void emissionUnitOperatingStatusTSEmissionUnitStatusTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        EmissionsProcess ep = new EmissionsProcess();
        ep.setOperatingStatusCode(opStatCode);
        ep.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("TS");
        FacilitySourceTypeCode stc = new FacilitySourceTypeCode();
        stc.setCode("100");
        testData.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().setFacilitySourceTypeCode(stc);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_STATUS_CODE.value()) && errorMap.get(ValidationField.PROCESS_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        ep.getOperatingStatusCode().setCode("TS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        ep.getOperatingStatusCode().setCode("PS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    /**
     * There should be no errors when emissions unit design capacity uom is a valid uom for eis.
     * There should be no error when emissions unit design capacity uom is not a valid uom for eis and the unit operating status is PS.
     * There should be one error when emissions unit design capacity uom is not a valid uom for eis.
     * There should be one error when emissions unit operating status is PS and source type is not landfill.
     */
    @Test
    public void designCapacityUomFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        testData.getUnitOfMeasureCode().setUnitDesignCapacity(true);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("PS");
        testData.getUnitOfMeasureCode().setUnitDesignCapacity(false);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("OP");
        testData.getUnitOfMeasureCode().setUnitDesignCapacity(false);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_UOM.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_UOM.value()).size() == 1);
    }
    
    @Test
    public void fuelUsePassTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
        ReportingPeriod rperiod1 = new ReportingPeriod();
        EmissionsProcess ep1 = new EmissionsProcess();
        ep1.setOperatingStatusCode(opStatCode);
        ep1.setSccCode("10200301"); // not required
        ep1.getReportingPeriods().add(rperiod1);
        ep1.setEmissionsProcessIdentifier("Boiler 001");
        ep1.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep1);
        
        UnitMeasureCode uom = new UnitMeasureCode();
        uom.setCode("BTU");
        
        CalculationMaterialCode cmc = new CalculationMaterialCode();
        cmc.setFuelUseMaterial(true);
		cmc.setCode("794");
		
		rperiod1.setFuelUseMaterialCode(cmc);
		rperiod1.setFuelUseUom(uom);
		rperiod1.setFuelUseValue(BigDecimal.valueOf(100));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
	
	@Test
    public void fuelDataValuesFailTest() {
		
		CefValidatorContext cefContext = createContext();
		EmissionsUnit testData = createBaseEmissionsUnit();
		
		OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");

        ReportingPeriod rperiod1 = new ReportingPeriod();
        EmissionsProcess ep1 = new EmissionsProcess();
        ep1.setOperatingStatusCode(opStatCode);
        ep1.setSccCode("10200301"); // not required
        ep1.getReportingPeriods().add(rperiod1);
        ep1.setEmissionsProcessIdentifier("Boiler 001");
        ep1.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep1);
        
        CalculationMaterialCode cmc = new CalculationMaterialCode();
        cmc.setFuelUseMaterial(true);
		cmc.setCode("794");
		
		rperiod1.setFuelUseMaterialCode(cmc);
		rperiod1.setFuelUseUom(null);
		rperiod1.setFuelUseValue(BigDecimal.valueOf(100));
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_FUEL_USE_VALUES.value()) && errorMap.get(ValidationField.PERIOD_FUEL_USE_VALUES.value()).size() == 1);
        
        cefContext = createContext();
        testData = createBaseEmissionsUnit();
        
        rperiod1 = new ReportingPeriod();
        ep1 = new EmissionsProcess();
        ep1.setOperatingStatusCode(opStatCode);
        ep1.setSccCode("10200302"); // required
        ep1.getReportingPeriods().add(rperiod1);
        ep1.setEmissionsProcessIdentifier("Boiler 001");
        ep1.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep1);
        
        UnitMeasureCode uom = new UnitMeasureCode();
		uom.setCode("BTU");
        
		rperiod1.setFuelUseMaterialCode(null);
		rperiod1.setFuelUseUom(null);
		rperiod1.setFuelUseValue(null);
		rperiod1.setHeatContentUom(uom);
		rperiod1.setHeatContentValue(BigDecimal.valueOf(100));
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_FUEL_USE_VALUES.value()) && errorMap.get(ValidationField.PERIOD_FUEL_USE_VALUES.value()).size() == 1);
        
	}
	
	@Test
    public void heatContentValuesFailTest() {
		
		CefValidatorContext cefContext = createContext();
		EmissionsUnit testData = createBaseEmissionsUnit();
		
		OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
		ReportingPeriod rperiod1 = new ReportingPeriod();
        rperiod1.setHeatContentUom(null);
        rperiod1.setHeatContentValue(BigDecimal.valueOf(100));
		EmissionsProcess ep1 = new EmissionsProcess();
		ep1.setStatusYear((short) 2020);
        ep1.setOperatingStatusCode(opStatCode);
        ep1.setSccCode("10200301"); // not required
        ep1.getReportingPeriods().add(rperiod1);
        ep1.setEmissionsProcessIdentifier("Boiler 001");
        ep1.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_HEAT_CONTENT_VALUES.value()) && errorMap.get(ValidationField.PERIOD_HEAT_CONTENT_VALUES.value()).size() == 1);
        
        cefContext = createContext();
        testData = createBaseEmissionsUnit();
        
        ReportingPeriod rperiod = new ReportingPeriod();
        CalculationMaterialCode cmc = new CalculationMaterialCode();
        cmc.setFuelUseMaterial(true);
		cmc.setCode("794");
		
		UnitMeasureCode uom = new UnitMeasureCode();
		uom.setCode("BTU");
		
		rperiod.setFuelUseMaterialCode(cmc);
		rperiod.setFuelUseUom(uom);
		rperiod.setFuelUseValue(BigDecimal.valueOf(100));
		rperiod.setHeatContentUom(null);
		rperiod.setHeatContentValue(null);
        
        EmissionsProcess ep = new EmissionsProcess();
        ep.setStatusYear((short) 2020);
        ep.setOperatingStatusCode(opStatCode);
        ep.setSccCode("10200302"); // required
        ep.getReportingPeriods().add(rperiod);
        ep.setEmissionsProcessIdentifier("Boiler 001");
        ep.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_HEAT_CONTENT_VALUES.value()) && errorMap.get(ValidationField.PERIOD_HEAT_CONTENT_VALUES.value()).size() == 1);
	}
	
	@Test
	public void processesWithSameSccFuelDataFail() {
		// fails if the process is considered a duplicate and more than one process has fuel data
		CefValidatorContext cefContext = createContext();
		EmissionsUnit testData = createBaseEmissionsUnit();
		
		OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
        ReportingPeriod rperiod = new ReportingPeriod();
        OperatingDetail od = new OperatingDetail();
        od.setActualHoursPerPeriod((short)1);
        od.setAvgDaysPerWeek(BigDecimal.ONE);
        od.setAvgHoursPerDay(BigDecimal.ONE);
        od.setAvgWeeksPerPeriod((short)1);
        od.setPercentFall(BigDecimal.ZERO);
        od.setPercentSpring(BigDecimal.ZERO);
        od.setPercentSummer(BigDecimal.ZERO);
        od.setPercentWinter(BigDecimal.valueOf(100.0));
        od.setReportingPeriod(rperiod);
        rperiod.getOperatingDetails().add(od);
        
        CalculationMaterialCode cmc = new CalculationMaterialCode();
        cmc.setFuelUseMaterial(true);
		cmc.setCode("794");
		
		UnitMeasureCode uom = new UnitMeasureCode();
		uom.setCode("BTU");
		ReportingPeriodCode rpc = new ReportingPeriodCode();
		rpc.setCode("A");
		EmissionsOperatingTypeCode otc = new EmissionsOperatingTypeCode();
		otc.setCode("U");
		
		rperiod.setFuelUseMaterialCode(cmc);
		rperiod.setFuelUseUom(uom);
		rperiod.setFuelUseValue(BigDecimal.valueOf(100));
		rperiod.setHeatContentUom(uom);
		rperiod.setHeatContentValue(BigDecimal.valueOf(100));
		rperiod.setReportingPeriodTypeCode(rpc);
        rperiod.setEmissionsOperatingTypeCode(otc); 

        EmissionsProcess ep = new EmissionsProcess();
        ep.setStatusYear((short) 2000);
        ep.setOperatingStatusCode(opStatCode);
        ep.setSccCode("10200302"); 
        ep.getReportingPeriods().add(rperiod);
        ep.setEmissionsProcessIdentifier("Boiler 001");
        ep.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep);
        
        ReportingPeriod rperiod2 = new ReportingPeriod();
        OperatingDetail od2 = new OperatingDetail();
        od2.setActualHoursPerPeriod((short)1);
        od2.setAvgDaysPerWeek(BigDecimal.ONE);
        od2.setAvgHoursPerDay(BigDecimal.ONE);
        od2.setAvgWeeksPerPeriod((short)1);
        od2.setPercentFall(BigDecimal.ZERO);
        od2.setPercentSpring(BigDecimal.ZERO);
        od2.setPercentSummer(BigDecimal.ZERO);
        od2.setPercentWinter(BigDecimal.valueOf(100.0));
        od2.setReportingPeriod(rperiod2);
        rperiod2.getOperatingDetails().add(od2);
        
		rperiod2.setFuelUseMaterialCode(cmc);
		rperiod2.setFuelUseUom(uom);
		rperiod2.setFuelUseValue(BigDecimal.valueOf(100));
		rperiod2.setHeatContentUom(uom);
		rperiod2.setHeatContentValue(BigDecimal.valueOf(100));
		rperiod2.setReportingPeriodTypeCode(rpc);
		rperiod2.setEmissionsOperatingTypeCode(otc); 

        EmissionsProcess ep2 = new EmissionsProcess();
        ep2.setStatusYear((short) 2000);
        ep2.setOperatingStatusCode(opStatCode);
        ep2.setSccCode("10200302"); 
        ep2.getReportingPeriods().add(rperiod2);
        ep2.setEmissionsProcessIdentifier("Boiler 002");
        ep2.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep2);
		
		assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_DUP_SCC_FUEL_USE.value()) && errorMap.get(ValidationField.PERIOD_DUP_SCC_FUEL_USE.value()).size() == 1);
        
        // fails if the process is considered a duplicate and both have the same pollutant
        cefContext = createContext();
        Emission e1 = new Emission();
        Emission e2 = new Emission();
        Pollutant p = new Pollutant();
        p.setPollutantCode("1308389");
        p.setPollutantName("Chromic Oxide");
        e1.setPollutant(p);
        e2.setPollutant(p);
        rperiod2.getEmissions().add(e1);
        rperiod.getEmissions().add(e1);
        
        rperiod2.setFuelUseMaterialCode(null);
		rperiod2.setFuelUseUom(null);
		rperiod2.setFuelUseValue(null);
		rperiod2.setHeatContentUom(null);
		rperiod2.setHeatContentValue(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_PROCESS.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_PROCESS.value()).size() == 1);
	}
	
	@Test
	public void processesWithSameSccPass() {
		// pass if the process details are the same and operating type is different
		CefValidatorContext cefContext = createContext();
		EmissionsUnit testData = createBaseEmissionsUnit();
		
		OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
        ReportingPeriod rperiod = new ReportingPeriod();
        OperatingDetail od = new OperatingDetail();
        od.setActualHoursPerPeriod((short)1);
        od.setAvgDaysPerWeek(BigDecimal.ONE);
        od.setAvgHoursPerDay(BigDecimal.ONE);
        od.setPercentFall(BigDecimal.ZERO);
        od.setPercentSpring(BigDecimal.ZERO);
        od.setPercentSummer(BigDecimal.ZERO);
        od.setPercentWinter(BigDecimal.valueOf(100.0));
        od.setAvgWeeksPerPeriod((short)1);
        od.setReportingPeriod(rperiod);
        rperiod.getOperatingDetails().add(od);
        
        CalculationMaterialCode cmc = new CalculationMaterialCode();
        cmc.setFuelUseMaterial(true);
		cmc.setCode("794");
		
		UnitMeasureCode uom = new UnitMeasureCode();
		uom.setCode("BTU");
		ReportingPeriodCode rpc = new ReportingPeriodCode();
		rpc.setCode("A");
		EmissionsOperatingTypeCode otc = new EmissionsOperatingTypeCode();
		otc.setCode("U");
		
		rperiod.setFuelUseMaterialCode(cmc);
		rperiod.setFuelUseUom(uom);
		rperiod.setFuelUseValue(BigDecimal.valueOf(100));
		rperiod.setHeatContentUom(uom);
		rperiod.setHeatContentValue(BigDecimal.valueOf(100));
		rperiod.setReportingPeriodTypeCode(rpc);
        rperiod.setEmissionsOperatingTypeCode(otc); 

        EmissionsProcess ep = new EmissionsProcess();
        ep.setStatusYear((short) 2000);
        ep.setOperatingStatusCode(opStatCode);
        ep.setSccCode("10200302"); 
        ep.getReportingPeriods().add(rperiod);
        ep.setEmissionsProcessIdentifier("Boiler 001");
        ep.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep);
        
        ReportingPeriod rperiod2 = new ReportingPeriod();
        OperatingDetail od2 = new OperatingDetail();
        od2.setActualHoursPerPeriod((short)1);
        od2.setAvgWeeksPerPeriod((short)1);
        od2.setAvgDaysPerWeek(BigDecimal.ONE);
        od2.setAvgHoursPerDay(BigDecimal.ONE);
        od2.setPercentFall(BigDecimal.ZERO);
        od2.setPercentSpring(BigDecimal.ZERO);
        od2.setPercentSummer(BigDecimal.ZERO);
        od2.setPercentWinter(BigDecimal.valueOf(100.0));
        od2.setReportingPeriod(rperiod2);
        rperiod2.getOperatingDetails().add(od2);
        
        EmissionsOperatingTypeCode otc2 = new EmissionsOperatingTypeCode();
		otc.setCode("US");
		
		rperiod2.setFuelUseMaterialCode(cmc);
		rperiod2.setFuelUseUom(uom);
		rperiod2.setFuelUseValue(BigDecimal.valueOf(100));
		rperiod2.setHeatContentUom(uom);
		rperiod2.setHeatContentValue(BigDecimal.valueOf(100));
		rperiod2.setReportingPeriodTypeCode(rpc);
		rperiod2.setEmissionsOperatingTypeCode(otc2); 

        EmissionsProcess ep2 = new EmissionsProcess();
        ep2.setStatusYear((short) 2000);
        ep2.setOperatingStatusCode(opStatCode);
        ep2.setSccCode("10200302"); 
        ep2.getReportingPeriods().add(rperiod2);
        ep2.setEmissionsProcessIdentifier("Boiler 002");
        ep2.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep2);
		
		assertTrue(this.validator.validate(cefContext, testData));
	    assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
	}

	
	@Test
	public void processWithDupSccWarning() {
		// fails (warning) if process details and reporting period operating types are the same, but operating details are different
		CefValidatorContext cefContext = createContext();
		EmissionsUnit testData = createBaseEmissionsUnit();
		
		OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
        ReportingPeriod rperiod = new ReportingPeriod();
        OperatingDetail od = new OperatingDetail();
        od.setActualHoursPerPeriod((short)1);
        od.setAvgWeeksPerPeriod((short)1);
        od.setAvgDaysPerWeek(BigDecimal.ONE);
        od.setAvgHoursPerDay(BigDecimal.valueOf(1.5));
        od.setPercentFall(BigDecimal.ZERO);
        od.setPercentSpring(BigDecimal.ZERO);
        od.setPercentSummer(BigDecimal.ZERO);
        od.setPercentWinter(BigDecimal.valueOf(100.0));
        od.setReportingPeriod(rperiod);
        rperiod.getOperatingDetails().add(od);
        
        CalculationMaterialCode cmc = new CalculationMaterialCode();
        cmc.setFuelUseMaterial(true);
		cmc.setCode("794");
		
		UnitMeasureCode uom = new UnitMeasureCode();
		uom.setCode("BTU");
		ReportingPeriodCode rpc = new ReportingPeriodCode();
		rpc.setCode("A");
		EmissionsOperatingTypeCode otc = new EmissionsOperatingTypeCode();
		otc.setCode("U");
		
		rperiod.setFuelUseMaterialCode(cmc);
		rperiod.setFuelUseUom(uom);
		rperiod.setFuelUseValue(BigDecimal.valueOf(100));
		rperiod.setHeatContentUom(uom);
		rperiod.setHeatContentValue(BigDecimal.valueOf(100));
		rperiod.setReportingPeriodTypeCode(rpc);
        rperiod.setEmissionsOperatingTypeCode(otc); 

        EmissionsProcess ep = new EmissionsProcess();
        ep.setStatusYear((short) 2000);
        ep.setOperatingStatusCode(opStatCode);
        ep.setSccCode("10200302"); 
        ep.getReportingPeriods().add(rperiod);
        ep.setEmissionsProcessIdentifier("Boiler 001");
        ep.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep);
        
        ReportingPeriod rperiod2 = new ReportingPeriod();
        OperatingDetail od2 = new OperatingDetail();
        od2.setActualHoursPerPeriod((short)1);
        od2.setAvgWeeksPerPeriod((short)1);
        od2.setAvgDaysPerWeek(BigDecimal.ONE);
        od2.setAvgHoursPerDay(BigDecimal.ONE);
        od2.setPercentFall(BigDecimal.ZERO);
        od2.setPercentSpring(BigDecimal.ZERO);
        od2.setPercentSummer(BigDecimal.ZERO);
        od2.setPercentWinter(BigDecimal.valueOf(100.0));
        od2.setReportingPeriod(rperiod2);
        rperiod2.getOperatingDetails().add(od2);
        
		rperiod2.setFuelUseMaterialCode(cmc);
		rperiod2.setFuelUseUom(uom);
		rperiod2.setFuelUseValue(BigDecimal.valueOf(100));
		rperiod2.setHeatContentUom(uom);
		rperiod2.setHeatContentValue(BigDecimal.valueOf(100));
		rperiod2.setReportingPeriodTypeCode(rpc);
		rperiod2.setEmissionsOperatingTypeCode(otc); 

        EmissionsProcess ep2 = new EmissionsProcess();
        ep2.setStatusYear((short) 2000);
        ep2.setOperatingStatusCode(opStatCode);
        ep2.setSccCode("10200302"); 
        ep2.getReportingPeriods().add(rperiod2);
        ep2.setEmissionsProcessIdentifier("Boiler 002");
        ep2.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep2);
		
		assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_PROCESS.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_PROCESS.value()).size() == 1);
	}

    @Test
    public void previousOpYearCurrentShutdownYear() {
        // pass when previous and current op status is OP
        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        testData.setOperatingStatusCode(opStatCode);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        // pass when previous op status is OP, current op status is PS/TS, and current op status year is after previous year
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("TS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
         
        // fail when previous op status is OP, current op status is PS/TS, and current op status year is prior to previous year
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("TS");
        testData.setStatusYear((short) 2000);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()).size() == 1);
        
        // fail when previous op status is OP, current op status is PS/TS, and both have same status years
        // fail PS unit not copied forward warning.
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("PS");
        testData.setStatusYear((short) 2018);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
        
        // pass when facility source type code is landfill, QA is not checked if it is landfill
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("TS");
        testData.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().getFacilitySourceTypeCode().setCode("104");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
         
    }

	@Test
    public void newUnitShutdownPassTest() {
		// pass when previous exists and current op status is OP
        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        testData.setOperatingStatusCode(opStatCode);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        // pass when previous exists and current op status is PS/TS
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("TS");

		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        // pass when previous report exists, but unit doesn't and current op status is OP
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("OP");
        testData.setUnitIdentifier("test new");

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        // pass when previous report doesn't exist and current op status is OP
        cefContext = createContext();
        testData.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().setId(2L);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

	@Test
    public void newUnitShutdownPassFail() {
	    // fail when previous report exists, but unit doesn't and current op status is TS
        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("TS");
        testData.setOperatingStatusCode(opStatCode);
        testData.setUnitIdentifier("test new");

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()).size() == 1);

        // fail when previous report doesn't exist and current op status is TS
        cefContext = createContext();
        testData.getFacilitySite().getEmissionsReport().getMasterFacilityRecord().setId(2L);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()).size() == 1);
    }
	
	@Test
    public void operationStatusPSPreviousYearOpCurrentYearFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();

        OperatingStatusCode opStatusCode = new OperatingStatusCode();
        OperatingStatusCode psStatusCode = new OperatingStatusCode();
        opStatusCode.setCode("OP");
        psStatusCode.setCode("PS");
        testData.setOperatingStatusCode(opStatusCode);
        testData.setPreviousYearOperatingStatusCode(psStatusCode);
        testData.setStatusYear((short) 2019);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()).size() == 1);
    }

    private EmissionsUnit createBaseEmissionsUnit() {

        EmissionsUnit result = new EmissionsUnit();

        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
        UnitTypeCode utc = new UnitTypeCode();
        utc.setCode("100");
        
        UnitMeasureCode capUom = new UnitMeasureCode();
        capUom.setCode("CURIE");
        
        FacilitySourceTypeCode stc = new FacilitySourceTypeCode();
        stc.setCode("137");
        
        MasterFacilityRecord mfr = new MasterFacilityRecord();
        mfr.setId(1L);
        mfr.setFacilitySourceTypeCode(stc);
        
        EmissionsReport er = new EmissionsReport();
        er.setId(1L);
        er.setYear(new Short("2020"));
        er.setMasterFacilityRecord(mfr);
        
        FacilitySite facility = new FacilitySite();
        facility.setId(1L);
        facility.setOperatingStatusCode(opStatCode);
        facility.setEmissionsReport(er);
        
        result.setStatusYear((short) 2020);
        result.setOperatingStatusCode(opStatCode);
        result.setUnitIdentifier("Boiler 001");
        result.setUnitTypeCode(utc);
        result.setUnitOfMeasureCode(capUom);
        result.setFacilitySite(facility);
        result.setDesignCapacity(BigDecimal.valueOf(100));
        result.setId(1L);
        
        return result;
    }
    
}
