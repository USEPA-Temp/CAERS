package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import gov.epa.cef.web.domain.EisLatLongToleranceLookup;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointTypeCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.repository.EisLatLongToleranceLookupRepository;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.ReleasePointValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReleasePointValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private ReleasePointValidator validator;
    
    @Mock
    private EisLatLongToleranceLookupRepository latLongToleranceRepo;
    
    @Before
    public void init(){
    	
    	List<EisLatLongToleranceLookup> toleranceList = new ArrayList<EisLatLongToleranceLookup>();
    	
    	EisLatLongToleranceLookup fsTolerance = new EisLatLongToleranceLookup();
    	fsTolerance.setEisProgramId("536411");
    	fsTolerance.setCoordinateTolerance(new BigDecimal(0.0055));

    	toleranceList.add(fsTolerance);
    	
    	when(latLongToleranceRepo.findById("536411")).thenReturn(Optional.of(fsTolerance));
    	when(latLongToleranceRepo.findById("111111")).thenReturn(Optional.empty());
    }

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void exitGasTemperatureRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();

        cefContext = createContext();
        testData.setExitGasTemperature((short) -31);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_TEMP.value()) && errorMap.get(ValidationField.RP_GAS_TEMP.value()).size() == 1);

        cefContext = createContext();
        testData.setExitGasTemperature((short) 4001);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_TEMP.value()) && errorMap.get(ValidationField.RP_GAS_TEMP.value()).size() == 1);
    }

		@Test
		public void exitGasTemperatureRangeTest() {
			
			CefValidatorContext cefContext = createContext();
			ReleasePoint testData = createBaseReleasePoint();
			testData.setExitGasTemperature((short) -30);
	
			assertTrue(this.validator.validate(cefContext, testData));
			assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
	
			cefContext = createContext();
			testData.setExitGasTemperature((short) 4000);
	
			assertTrue(this.validator.validate(cefContext, testData));
			assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void exitGasFlowRateOrVelocityRequiredFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        testData.setExitGasFlowRate(null);
        testData.setExitGasVelocity(null);
        testData.setExitGasFlowUomCode(null);
        testData.setExitGasVelocityUomCode(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_RELEASE.value()) && errorMap.get(ValidationField.RP_GAS_RELEASE.value()).size() == 1);
    }
    
    @Test
    public void exitGasFlowRateRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        testData.setExitGasFlowRate(0.0);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_FLOW.value()) && errorMap.get(ValidationField.RP_GAS_FLOW.value()).size() == 1);
        
        cefContext = createContext();
        ReleasePointTypeCode releasePointTypeCode = new ReleasePointTypeCode();
        releasePointTypeCode.setCode("1");
        testData.setTypeCode(releasePointTypeCode);
        testData.setExitGasFlowRate(200000.1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    
        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_FLOW.value()) && errorMap.get(ValidationField.RP_GAS_FLOW.value()).size() == 1);
        
        cefContext = createContext();
        UnitMeasureCode flowUom = new UnitMeasureCode();
        flowUom.setCode("ACFM");
        UnitMeasureCode velocityUom = new UnitMeasureCode();
        velocityUom.setCode("FPM");    
        testData.setExitGasFlowUomCode(flowUom);
        testData.setExitGasVelocityUomCode(velocityUom);
        testData.setExitGasFlowRate(12000000.1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_FLOW.value()) && errorMap.get(ValidationField.RP_GAS_FLOW.value()).size() == 1);
    }
    
    @Test
    public void exitGasFlowRateAndUomRequiredFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        testData.setExitGasFlowRate(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_FLOW.value()) && errorMap.get(ValidationField.RP_GAS_FLOW.value()).size() == 1);
        
        cefContext = createContext();
        testData.setExitGasFlowUomCode(null);
        testData.setExitGasFlowRate(0.002);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_FLOW.value()) && errorMap.get(ValidationField.RP_GAS_FLOW.value()).size() == 1);
    }
    
    @Test
    public void exitGasVelocityRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        testData.setExitGasVelocity(0.0);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_VELOCITY.value()) && errorMap.get(ValidationField.RP_GAS_VELOCITY.value()).size() == 1);
        
        cefContext = createContext();
        ReleasePointTypeCode releasePointTypeCode = new ReleasePointTypeCode();
        releasePointTypeCode.setCode("1");
        testData.setTypeCode(releasePointTypeCode);
        testData.setExitGasVelocity(1500.1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_VELOCITY.value()) && errorMap.get(ValidationField.RP_GAS_VELOCITY.value()).size() == 1);
        
        cefContext = createContext();
        UnitMeasureCode flowUom = new UnitMeasureCode();
        flowUom.setCode("ACFM");
        UnitMeasureCode velocityUom = new UnitMeasureCode();
        velocityUom.setCode("FPM");    
        testData.setExitGasFlowUomCode(flowUom);
        testData.setExitGasVelocityUomCode(velocityUom);
        testData.setExitGasVelocity(90000.1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_VELOCITY.value()) && errorMap.get(ValidationField.RP_GAS_VELOCITY.value()).size() == 1);
    }
    
    @Test
    public void exitGasVelocityAndUomRequiredFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        testData.setExitGasVelocity(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_VELOCITY.value()) && errorMap.get(ValidationField.RP_GAS_VELOCITY.value()).size() == 1);
        
        cefContext = createContext();
        testData.setExitGasVelocityUomCode(null);
        testData.setExitGasVelocity(1000.0);
        testData.setExitGasFlowRate(null);
        testData.setExitGasFlowUomCode(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_VELOCITY.value()) && errorMap.get(ValidationField.RP_GAS_VELOCITY.value()).size() == 1);
    }
    
    @Test
    public void exitGasFlowRateUomAndVelocityUomTest() {
    	
    	CefValidatorContext cefContext = createContext();
    	ReleasePoint testData = createBaseReleasePoint();
    	
    	assertTrue(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void exitGasVelocityUomFailTest() {
    	
    	CefValidatorContext cefContext = createContext();
    	ReleasePoint testData = createBaseReleasePoint();
    	
    	UnitMeasureCode uom = new UnitMeasureCode();
    	uom.setCode("BTU");
    	
    	testData.setExitGasVelocityUomCode(uom);
    	testData.setExitGasVelocity(0.06);
    	testData.setExitGasFlowRate(null);
    	testData.setExitGasFlowUomCode(null);
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.RP_GAS_VELOCITY.value()) && errorMap.get(ValidationField.RP_GAS_VELOCITY.value()).size() == 1);

    }
    
    @Test
    public void exitGasFlowRateUomFailTest() {
    	
    	CefValidatorContext cefContext = createContext();
    	ReleasePoint testData = createBaseReleasePoint();
    	
    	UnitMeasureCode uom = new UnitMeasureCode();
    	uom.setCode("BTU");
    	
    	testData.setExitGasFlowUomCode(uom);
    	testData.setExitGasVelocity(null);
    	testData.setExitGasVelocityUomCode(null);
    	testData.setStackDiameter(0.1);
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.RP_GAS_FLOW.value()) && errorMap.get(ValidationField.RP_GAS_FLOW.value()).size() == 1);
    }
    
    @Test
    public void fenceLineDistanceRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();

        cefContext = createContext();
        testData.setFenceLineDistance((long) -1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_FENCELINE.value()) && errorMap.get(ValidationField.RP_FENCELINE.value()).size() == 1);

        cefContext = createContext();
        testData.setFenceLineDistance((long) 100000);
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void fugitiveHeightRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseFugitiveReleasePoint();

        cefContext = createContext();
        testData.setFugitiveHeight((long) -1);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_FUGITIVE.value()) && errorMap.get(ValidationField.RP_FUGITIVE.value()).size() == 1);

        cefContext = createContext();
        testData.setFugitiveHeight((long) 600);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void fugitiveLengthRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseFugitiveReleasePoint();

        cefContext = createContext();
        testData.setFugitiveLength((long) 0);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_FUGITIVE.value()) && errorMap.get(ValidationField.RP_FUGITIVE.value()).size() == 1);

        cefContext = createContext();
        testData.setFugitiveLength((long) 20000);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void fugitiveWidthRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseFugitiveReleasePoint();

        cefContext = createContext();
        testData.setFugitiveWidth((long) 0);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_FUGITIVE.value()) && errorMap.get(ValidationField.RP_FUGITIVE.value()).size() == 1);

        cefContext = createContext();
        testData.setFugitiveWidth((long) 20000);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void fugitiveAngleRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseFugitiveReleasePoint();

        cefContext = createContext();
        testData.setFugitiveAngle((long) -1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_FUGITIVE.value()) && errorMap.get(ValidationField.RP_FUGITIVE.value()).size() == 1);

        cefContext = createContext();
        testData.setFugitiveWidth((long) 90);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void stackHeightRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();

        cefContext = createContext();
        testData.setStackHeight(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STACK.value()) && errorMap.get(ValidationField.RP_STACK.value()).size() == 1);

        cefContext = createContext();
        testData.setStackHeight(0.5);
        testData.setStackDiameter(0.001);
        testData.setExitGasFlowRate(0.00000001);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        cefContext = createContext();
        testData.setStackHeight(1400.0);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void stackDiameterRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();

        cefContext = createContext();
        testData.setStackDiameter(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STACK.value()) && errorMap.get(ValidationField.RP_STACK.value()).size() == 1);

        cefContext = createContext();
        testData.setStackDiameter(0.0);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        cefContext = createContext();
        testData.setStackDiameter(400.0);
        testData.setStackHeight(1300.0);
        testData.setExitGasVelocity(0.0025);
        testData.setExitGasFlowRate(300.0);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void uomFeetFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseFugitiveReleasePoint();

        UnitMeasureCode uomFT = new UnitMeasureCode();
        uomFT.setCode("FT");  
        UnitMeasureCode uomM = new UnitMeasureCode();
        uomM.setCode("M");  
        
        cefContext = createContext();
        testData.setFenceLineUomCode(uomM);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_UOM_FT.value()) && errorMap.get(ValidationField.RP_UOM_FT.value()).size() == 1);
   
        cefContext = createContext();
        testData.setFenceLineUomCode(uomFT);
        testData.setFugitiveLengthUomCode(uomM);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_UOM_FT.value()) && errorMap.get(ValidationField.RP_UOM_FT.value()).size() == 1);
        
        cefContext = createContext();
        testData.setFugitiveLengthUomCode(uomFT);
        testData.setFugitiveWidthUomCode(uomM);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        cefContext = createContext();
        testData.setFugitiveWidthUomCode(uomFT);
        testData.setFugitiveHeightUomCode(uomM);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        cefContext = createContext();
        testData = createBaseReleasePoint();
        testData.setStackHeightUomCode(uomM);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_UOM_FT.value()) && errorMap.get(ValidationField.RP_UOM_FT.value()).size() == 1);
   
        cefContext = createContext();
        testData.setStackHeightUomCode(uomFT);
        testData.setStackDiameterUomCode(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void stackDiameterCompareHeightFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();

        cefContext = createContext();
        testData.setStackDiameter(10.0);
        testData.setStackHeight(5.0);
        testData.setExitGasVelocity(2.0);
        testData.setExitGasFlowRate(150.0);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STACK_WARNING.value()) && errorMap.get(ValidationField.RP_STACK_WARNING.value()).size() == 1);
    }

    @Test
    public void coordinateToleranceFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseFugitiveReleasePoint();
        
        cefContext = createContext();
        testData.setLatitude(32.959000);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_COORDINATE.value()) && errorMap.get(ValidationField.RP_COORDINATE.value()).size() == 1);
        
        cefContext = createContext();
        testData.setLongitude(-84.888000);
        testData.setLatitude(33.951000);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        cefContext = createContext();
        testData = createBaseReleasePoint();
        testData.setLatitude(32.959000);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_COORDINATE.value()) && errorMap.get(ValidationField.RP_COORDINATE.value()).size() == 1);
    }
    
    @Test
    public void uniqueIdentifierCheckFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        ReleasePoint rp1 = new ReleasePoint();
        rp1.setReleasePointIdentifier("test");
        rp1.setId(2L);
        testData.setReleasePointIdentifier("test");
        testData.getFacilitySite().getReleasePoints().add(rp1);
        testData.getFacilitySite().getReleasePoints().add(testData);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_IDENTIFIER.value()) && errorMap.get(ValidationField.RP_IDENTIFIER.value()).size() == 1);
    }
    
    @Test
    public void exitVelocityCalcCheckFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        
        cefContext = createContext();
        testData.setExitGasVelocity(250.0);
        testData.setExitGasFlowRate(0.00001);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_VELOCITY.value()) && errorMap.get(ValidationField.RP_GAS_VELOCITY.value()).size() == 1);
    }
    
    @Test
    public void exitVelocityCalcCheckPassTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        
        cefContext = createContext();
        testData.setExitGasVelocity(250.0);
        testData.setExitGasFlowRate(200.0);
        testData.setStackDiameter(1.0);
        testData.setStackHeight(5.0);
        
        assertTrue(this.validator.validate(cefContext, testData));
      	assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void exitFlowRateCalcCheckFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        
        cefContext = createContext();
        testData.setExitGasVelocity(100.0);
        testData.setStackDiameter(1.0);
        testData.setStackHeight(5.0);
        testData.setExitGasFlowRate(38.0);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_FLOW.value()) && errorMap.get(ValidationField.RP_GAS_FLOW.value()).size() == 1);
    }
    
    @Test
    public void exitFlowRateCalcCheckPassTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        
        cefContext = createContext();
        testData.setExitGasVelocity(0.001);
        testData.setStackDiameter(0.001);
        testData.setStackHeight(5.0);
        testData.setExitGasFlowRate(0.00000001);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.setExitGasVelocity(1500.0);
        testData.setStackDiameter(13.2);
        testData.setStackHeight(15.0);
        testData.setExitGasFlowRate(200000.0);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void stackCheckForFlowAndVelocityFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        
        cefContext = createContext();
        testData.setExitGasVelocity(null);
        testData.setExitGasVelocityUomCode(null);
        testData.setStackDiameter(1.0);
        testData.setStackHeight(5.0);
        testData.setExitGasFlowRate(38.0);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STACK_WARNING.value()) && errorMap.get(ValidationField.RP_STACK_WARNING.value()).size() == 1);
    }
    
    @Test
    public void FlowAndVelocityCheckForDiameterFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        
        cefContext = createContext();
        testData.setStackDiameter(null);
        testData.setStackHeight(5.0);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STACK_WARNING.value()) && errorMap.get(ValidationField.RP_STACK_WARNING.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateOperationStatusYearTypeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        
        OperatingStatusCode opStatusCode = new OperatingStatusCode();
        opStatusCode.setCode("TS");
        testData.setOperatingStatusCode(opStatusCode);
        testData.setStatusYear(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STATUS_CODE.value()) && errorMap.get(ValidationField.RP_STATUS_CODE.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateStatusYearRangeTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        
        testData.setStatusYear((short) 1900);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.setStatusYear((short) 2050);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void simpleValidateStatusYearRangeFailTest() {
	      
        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        
        testData.setStatusYear((short) 1800);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STATUS_YEAR.value()) && errorMap.get(ValidationField.RP_STATUS_YEAR.value()).size() == 1);
        
        cefContext = createContext();
        testData.setStatusYear((short) 2051);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STATUS_YEAR.value()) && errorMap.get(ValidationField.RP_STATUS_YEAR.value()).size() == 1);
    }
    
    @Test
    public void releasePointTypeCodeLegacyFailTest() {
	      
        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        testData.getTypeCode().setCode("99");
        testData.getTypeCode().setLastInventoryYear(2002);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_TYPE_CODE.value()) && errorMap.get(ValidationField.RP_TYPE_CODE.value()).size() == 1);
    }
    
    @Test
    public void releasePointLatLongWarningPassTest() {
	      
        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        testData.setLatitude(null);
        testData.setLongitude(null);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void releasePointLatLongWarningFailTest() {
	      
        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        testData.setLatitude(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_COORDINATE.value()) && errorMap.get(ValidationField.RP_COORDINATE.value()).size() == 1);
    }
    
    
    private ReleasePoint createBaseReleasePoint() {
    	
    	EmissionsReport er = new EmissionsReport();
    	er.setYear((short)2019);
    	
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("TS");
        
        FacilitySite facility = new FacilitySite();
        facility.setId(1L);
        facility.setEisProgramId("111111");
        facility.setLatitude(new BigDecimal(33.949000));
        facility.setLongitude(new BigDecimal(-84.388000));
        facility.setOperatingStatusCode(opStatCode);
        facility.setEmissionsReport(er);
        er.getFacilitySites().add(facility);
        
        ReleasePointTypeCode releasePointTypeCode = new ReleasePointTypeCode();
        releasePointTypeCode.setCode("2");
        
        UnitMeasureCode flowUom = new UnitMeasureCode();
        flowUom.setCode("ACFS");     
        
        UnitMeasureCode velUom = new UnitMeasureCode();
        velUom.setCode("FPS");    
        
        UnitMeasureCode distUom = new UnitMeasureCode();
        distUom.setCode("FT");    
        
        ReleasePoint result = new ReleasePoint();
        result.setExitGasTemperature((short) 50);
        result.setExitGasFlowRate(0.002);
        result.setExitGasFlowUomCode(flowUom);
        result.setExitGasVelocity(0.01);
        result.setExitGasVelocityUomCode(velUom);
        result.setFenceLineDistance((long) 1);
        result.setFenceLineUomCode(distUom);
        result.setStackHeightUomCode(distUom);
        result.setStackDiameterUomCode(distUom);
        result.setStackHeight(1.0);
        result.setStackDiameter(0.5);
        result.setTypeCode(releasePointTypeCode);
        result.setLatitude(33.949000);
        result.setLongitude(-84.388000);
        result.setFacilitySite(facility);
        result.setFugitiveLine1Latitude(33.949000);
        result.setFugitiveLine1Longitude(-84.388000);
        result.setFugitiveLine2Latitude(33.949000);
        result.setFugitiveLine2Longitude(-84.388000);
        result.setStatusYear((short) 2000);
        result.setOperatingStatusCode(opStatCode);
        result.setId(1L);
        
        return result;
    }
    
    private ReleasePoint createBaseFugitiveReleasePoint() {
    	
    	OperatingStatusCode opStatCode = new OperatingStatusCode();
    	opStatCode.setCode("OP");
    	
    	FacilitySite facility = new FacilitySite();
    	facility.setId(1L);
    	facility.setEisProgramId("536411");
    	facility.setLatitude(new BigDecimal(33.949000));
    	facility.setLongitude(new BigDecimal(-84.388000));
    	facility.setOperatingStatusCode(opStatCode);
    	
    	ReleasePointTypeCode releasePointTypeCode = new ReleasePointTypeCode();
    	releasePointTypeCode.setCode("1");
    	
    	UnitMeasureCode distUom = new UnitMeasureCode();
    	distUom.setCode("FT");
    	
    	ReleasePoint result = new ReleasePoint();
    	result.setTypeCode(releasePointTypeCode);
    	result.setFenceLineUomCode(distUom);
    	result.setFugitiveLengthUomCode(distUom);
    	result.setFugitiveWidthUomCode(distUom);
    	result.setFugitiveHeightUomCode(distUom);
    	result.setFenceLineDistance((long) 1);
    	result.setFugitiveLength((long) 1);
    	result.setFugitiveWidth((long) 1);
    	result.setFugitiveHeight((long) 1);
    	result.setLatitude(33.949000);
    	result.setLongitude(-84.388000);
    	result.setFacilitySite(facility);
    	result.setFugitiveLine1Latitude(33.949000);
    	result.setFugitiveLine1Longitude(-84.388000);
    	result.setFugitiveLine2Latitude(33.949000);
    	result.setFugitiveLine2Longitude(-84.388000);
    	result.setStatusYear((short) 2000);
    	result.setOperatingStatusCode(opStatCode);
    	
    	return result;
  }
}
