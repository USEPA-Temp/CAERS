package gov.epa.cef.web.util;

import java.math.BigDecimal;

public class CalculationUtils {


    public static BigDecimal convertMassUnits(BigDecimal sourceValue, MassUomConversion sourceUnit, MassUomConversion targetUnit) {
        BigDecimal result = sourceValue.multiply(sourceUnit.conversionFactor()).divide(targetUnit.conversionFactor());
        return result;
    }
}
