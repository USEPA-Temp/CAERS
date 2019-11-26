package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.CalculationMethodCode;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.validator.federal.EmissionValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private EmissionValidator validator;

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
        result.setReportingPeriod(period);

        UnitMeasureCode uom = new UnitMeasureCode();
        uom.setCode("TON");
        result.setEmissionsUomCode(uom);

        result.setTotalEmissions(new BigDecimal("10"));

        if (!totalDirectEntry) {
            result.setEmissionsDenominatorUom(uom);
            result.setEmissionsNumeratorUom(uom);
            result.setEmissionsFactor(new BigDecimal("1"));
        } else {
            result.setComments("Comment");
        }

        return result;
    }
}
