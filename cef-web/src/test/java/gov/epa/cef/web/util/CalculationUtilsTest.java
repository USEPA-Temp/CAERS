package gov.epa.cef.web.util;

import gov.epa.cef.web.config.TestCategories;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@Category(TestCategories.FastTest.class)
@RunWith(MockitoJUnitRunner.class)
public class CalculationUtilsTest {


    @Test
    public void convertMassUnits_Should_Return_ConvertedUnits_When_ValidValuesPassed() {

        BigDecimal sourceValue = new BigDecimal("10000");
        assertEquals(new BigDecimal("5.0000"), CalculationUtils.convertMassUnits(sourceValue, MassUomConversion.LB, MassUomConversion.TON));

        sourceValue = new BigDecimal("1e4");
        assertEquals(new BigDecimal("5"), CalculationUtils.convertMassUnits(sourceValue, MassUomConversion.LB, MassUomConversion.TON));

    }

}
