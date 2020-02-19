package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.domain.CalculationMethodCode;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFormulaVariable;
import gov.epa.cef.web.domain.EmissionFormulaVariableCode;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.ValidationResult;
import gov.epa.cef.web.service.validation.validator.federal.EmissionValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private EmissionValidator validator;

    @Mock
    private CefConfig cefConfig;

    private UnitMeasureCode curieUom;
    private UnitMeasureCode lbUom;
    private UnitMeasureCode tonUom;

    @Before
    public void init() {
        curieUom = new UnitMeasureCode();
        curieUom.setCode("CURIE");
        curieUom.setDescription("CURIES");
        curieUom.setUnitType("RADIOACTIVITY");
        curieUom.setCalculationVariable("1");

        lbUom = new UnitMeasureCode();
        lbUom.setCode("LB");
        lbUom.setDescription("POUNDS");
        lbUom.setUnitType("MASS");
        lbUom.setCalculationVariable("[lb]");

        tonUom = new UnitMeasureCode();
        tonUom.setCode("TON");
        tonUom.setDescription("TONS");
        tonUom.setUnitType("MASS");
        tonUom.setCalculationVariable("sTon");

        when(cefConfig.getEmissionsTotalErrorTolerance()).thenReturn(new BigDecimal(".05"));
        when(cefConfig.getEmissionsTotalWarningTolerance()).thenReturn(new BigDecimal(".01"));
    }

    @Test
    public void totalDirectEntryFalse_PassTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void totalDirectEntryTrue_PassTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(true);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    /**
     * There should be one error when Calculation Method Code has true total direct entry and a null comment
     */
    @Test
    public void totalDirectEntryTrue_NullComment_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(true);
        testData.setComments(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_COMMENTS.value()) && errorMap.get(ValidationField.EMISSION_COMMENTS.value()).size() == 1);
    }

    /**
     * There should be one error when Calculation Method Code has true total direct entry and a non-null Emissions Factor
     */
    @Test
    public void totalDirectEntryTrue_NonNullEmissionsFactor_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(true);
        testData.setEmissionsFactor(BigDecimal.TEN);
        testData.setEmissionsDenominatorUom(new UnitMeasureCode());
        testData.setEmissionsNumeratorUom(new UnitMeasureCode());

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_EF.value()) && errorMap.get(ValidationField.EMISSION_EF.value()).size() == 1);
    }

    /**
     * There should be one error when Calculation Method Code has true total direct entry and 
     * the reporting period's calculation parameter value is zero but the total emissions is not zero
     */
    @Test
    public void totalDirectEntryTrue_NonZeroTotalEmissions_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(true);
        testData.getReportingPeriod().setCalculationParameterValue(BigDecimal.ZERO);
        testData.setTotalEmissions(BigDecimal.ONE);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_TOTAL_EMISSIONS.value()) && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).size() == 1);
    }

    /**
     * There should be one error when Calculation Method Code has false total direct entry and a null Emissions Factor
     */
    @Test
    public void totalDirectEntryFalse_NullEmissionsFactor_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);
        testData.setEmissionsFactor(null);
        testData.setEmissionsDenominatorUom(null);
        testData.setEmissionsNumeratorUom(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_EF.value()) && errorMap.get(ValidationField.EMISSION_EF.value()).size() == 1);
    }

    /**
     * There should be two errors when Emissions Factor is null and Numerator and Denominator UoM are non-null
     */
    @Test
    public void nullEmissionsFactor_NonNullDenomNum_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(true);
        testData.setEmissionsDenominatorUom(new UnitMeasureCode());
        testData.setEmissionsNumeratorUom(new UnitMeasureCode());

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_NUM_UOM.value()) && errorMap.get(ValidationField.EMISSION_NUM_UOM.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_DENOM_UOM.value()) && errorMap.get(ValidationField.EMISSION_DENOM_UOM.value()).size() == 1);
    }
    
    /**
     * There should be one error when Emissions Factor is less than or equal to zero and the Numerator and Denominator are non-null
     */
    @Test
    public void equalToOrLessThanZeroEmissionsFactor_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(true);
        testData.setEmissionsFactor(BigDecimal.valueOf(0));
        testData.setEmissionsDenominatorUom(new UnitMeasureCode());
        testData.setEmissionsNumeratorUom(new UnitMeasureCode());
        testData.setEmissionsUomCode(new UnitMeasureCode());
        testData.setEmissionsCalcMethodCode(new CalculationMethodCode());
        testData.getEmissionsCalcMethodCode().setTotalDirectEntry(false);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_EF.value()) && errorMap.get(ValidationField.EMISSION_EF.value()).size() == 1);
    }

    /**
     * There should be two errors when Emissions Factor is non-null and Numerator and Denominator UoM are null
     */
    @Test
    public void nonNullEmissionsFactor_NullDenomNum_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);
        testData.setEmissionsDenominatorUom(null);
        testData.setEmissionsNumeratorUom(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_NUM_UOM.value()) && errorMap.get(ValidationField.EMISSION_NUM_UOM.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_DENOM_UOM.value()) && errorMap.get(ValidationField.EMISSION_DENOM_UOM.value()).size() == 1);
    }

    /**
     * There should be one error when Calculation Method Code has false total direct entry and Emission Denominator UoM type doesn't equal Throughput UoM type
     * and there should be no errors when totalManualEntry is true
     */
    @Test
    public void totalDirectEntryFalse_DenomMismatch_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);
        testData.setEmissionsDenominatorUom(curieUom);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_DENOM_UOM.value()) && errorMap.get(ValidationField.EMISSION_DENOM_UOM.value()).size() == 1);

        cefContext = createContext();
        testData.setTotalManualEntry(true);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    /**
     * There should be one error when Calculation Method Code has false total direct entry and Emission Numerator UoM type doesn't equal Total Emissions UoM type
     * and there should be no errors when totalManualEntry is true
     */
    @Test
    public void totalDirectEntryFalse_NumMismatch_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);
        testData.setEmissionsNumeratorUom(curieUom);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_NUM_UOM.value()) && errorMap.get(ValidationField.EMISSION_NUM_UOM.value()).size() == 1);

        cefContext = createContext();
        testData.setTotalManualEntry(true);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    /**
     * There should be one error when Calculation Method Code has false total direct entry and Emission Total Emissions is not calculated correctly
     * and there should be no errors when totalManualEntry is true
     */
    @Test
    public void totalDirectEntryFalse_TotalEmissionsToleranceError_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);
        testData.setTotalEmissions(new BigDecimal("10.6"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_TOTAL_EMISSIONS.value()) && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).size() == 1
                && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).get(0).getErrorCode() == ValidationResult.FEDERAL_ERROR_CODE);

        cefContext = createContext();
        testData.setTotalManualEntry(true);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setTotalEmissions(new BigDecimal("9.4"));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();

        testData.setTotalManualEntry(false);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_TOTAL_EMISSIONS.value()) && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).size() == 1
                && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).get(0).getErrorCode() == ValidationResult.FEDERAL_ERROR_CODE);
    }

    /**
     * There should be one warning when Calculation Method Code has false total direct entry and Emission Total Emissions is not calculated correctly
     * and there should be no errors when totalManualEntry is true
     */
    @Test
    public void totalDirectEntryFalse_TotalEmissionsToleranceWarning_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);
        testData.setTotalEmissions(new BigDecimal("10.2"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_TOTAL_EMISSIONS.value()) && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).size() == 1
                && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).get(0).getErrorCode() == ValidationResult.FEDERAL_WARNING_CODE);

        cefContext = createContext();
        testData.setTotalManualEntry(true);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setTotalEmissions(new BigDecimal("9.8"));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setTotalManualEntry(false);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_TOTAL_EMISSIONS.value()) && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).size() == 1
                && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).get(0).getErrorCode() == ValidationResult.FEDERAL_WARNING_CODE);

        cefContext = createContext();
        testData.setTotalEmissions(new BigDecimal("9.5"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_TOTAL_EMISSIONS.value()) && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).size() == 1
                && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).get(0).getErrorCode() == ValidationResult.FEDERAL_WARNING_CODE);

        cefContext = createContext();
        testData.setTotalEmissions(new BigDecimal("10.5"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_TOTAL_EMISSIONS.value()) && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).size() == 1
                && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).get(0).getErrorCode() == ValidationResult.FEDERAL_WARNING_CODE);
    }

    @Test
    public void totalDirectEntryFalse_TotalEmissionsToleranceWarning_BoundaryTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);
        testData.setTotalEmissions(new BigDecimal("10.1"));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setTotalEmissions(new BigDecimal("9.9"));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

    }
    
    /**
     * There should be two errors when emission formula variable value for % ash is less than 0.01 and greater than 30,
     * and there should be no errors when value is within range.
     */
    @Test
    public void percentAsh_Range_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);
        EmissionFormulaVariableCode ashCode = new EmissionFormulaVariableCode();
        ashCode.setCode("A");
        EmissionFormulaVariable var1 = new EmissionFormulaVariable();
        var1.setValue(BigDecimal.ZERO);
        var1.setVariableCode(ashCode);
        testData.getVariables().add(var1);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_FORMULA_VARIABLE.value()) && errorMap.get(ValidationField.EMISSION_FORMULA_VARIABLE.value()).size() == 1);

        cefContext = createContext();
        var1.setValue(new BigDecimal(31));
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        cefContext = createContext();
        var1.setValue(new BigDecimal(0.01));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    /**
     * There should be two errors when emission formula variable value for % sulfur is less than 0.01 and greater than 10,
     * and there should be no errors when value is within range.
     */
    @Test
    public void percentSulfur_Range_FailTest() {

        CefValidatorContext cefContext = createContext();
        Emission testData = createBaseEmission(false);
        EmissionFormulaVariableCode sulfurCode = new EmissionFormulaVariableCode();
        sulfurCode.setCode("SU");
        EmissionFormulaVariable var1 = new EmissionFormulaVariable();
        var1.setValue(BigDecimal.ZERO);
        var1.setVariableCode(sulfurCode);
        testData.getVariables().add(var1);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSION_FORMULA_VARIABLE.value()) && errorMap.get(ValidationField.EMISSION_FORMULA_VARIABLE.value()).size() == 1);

        cefContext = createContext();
        var1.setValue(new BigDecimal(11));
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        cefContext = createContext();
        var1.setValue(new BigDecimal(10));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }


    private Emission createBaseEmission(boolean totalDirectEntry) {

        Emission result = new Emission();

        CalculationMethodCode calcMethod = new CalculationMethodCode();
        calcMethod.setCode("1");
        calcMethod.setControlIndicator(false);
        calcMethod.setEpaEmissionFactor(false);
        calcMethod.setTotalDirectEntry(totalDirectEntry);
        result.setEmissionsCalcMethodCode(calcMethod);

        ReportingPeriod period = new ReportingPeriod();
        period.setCalculationParameterValue(new BigDecimal("10"));
        period.setCalculationParameterUom(tonUom);
        period.setEmissionsProcess(new EmissionsProcess());
        period.getEmissionsProcess().setEmissionsUnit(new EmissionsUnit());
        period.getEmissionsProcess().getEmissionsUnit().setFacilitySite(new FacilitySite());
        period.getEmissionsProcess().getEmissionsUnit().getFacilitySite().setEmissionsReport(new EmissionsReport());
        period.getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEmissionsReport().setYear(new Short("2019"));
        result.setReportingPeriod(period);

        result.setEmissionsUomCode(tonUom);

        result.setTotalEmissions(new BigDecimal("10"));

        if (!totalDirectEntry) {
            result.setEmissionsDenominatorUom(tonUom);
            result.setEmissionsNumeratorUom(tonUom);
            result.setEmissionsFactor(new BigDecimal("1"));
            result.setTotalManualEntry(false);
        } else {
            result.setComments("Comment");
            result.setTotalManualEntry(true);
        }

        return result;
    }
}
