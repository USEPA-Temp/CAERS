package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointTypeCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.ReleasePointValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReleasePointValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private ReleasePointValidator validator;

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void exitGasTemperatureFailTest() {

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
		public void exitGasTemperatureTest() {
			
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
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_RELEASE.value()) && errorMap.get(ValidationField.RP_GAS_RELEASE.value()).size() == 1);
        
        cefContext = createContext();
        ReleasePointTypeCode releasePointTypeCode = new ReleasePointTypeCode();
        releasePointTypeCode.setCode("1");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
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
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_FLOW.value()) && errorMap.get(ValidationField.RP_GAS_FLOW.value()).size() == 1);
        
        cefContext = createContext();
        testData.setExitGasFlowUomCode(null);
        testData.setExitGasFlowRate(1000.0);
        
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
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_VELOCITY.value()) && errorMap.get(ValidationField.RP_GAS_VELOCITY.value()).size() == 1);
        
        cefContext = createContext();
        testData.setExitGasVelocityUomCode(null);
        testData.setExitGasVelocity(1000.0);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

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
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    	
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
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    	
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
    }
    
    
    private ReleasePoint createBaseReleasePoint() {
    	
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
        result.setExitGasFlowRate(1000.0);
        result.setExitGasFlowUomCode(flowUom);
        result.setExitGasVelocity(100.0);
        result.setExitGasVelocityUomCode(velUom);
        result.setFenceLineUomCode(distUom);
        result.setTypeCode(releasePointTypeCode);

        return result;
    }
    
    private ReleasePoint createBaseFugitiveReleasePoint() {
    	
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
    	
    	return result;
  }
}
