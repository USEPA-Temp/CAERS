package gov.epa.cef.web.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;

import gov.epa.cef.web.domain.EmissionFormulaVariable;

public class CalculationUtils {

    public static BigDecimal convertMassUnits(BigDecimal sourceValue, MassUomConversion sourceUnit, MassUomConversion targetUnit) {
        BigDecimal result = sourceValue.multiply(sourceUnit.conversionFactor()).divide(targetUnit.conversionFactor());
        return result;
    }

    public static BigDecimal calculateEmissionFormula(String formula, List<EmissionFormulaVariable> inputs) {

        List<Constant> variables = inputs.stream().map(input -> {
            return new Constant(input.getVariableCode().getCode(), input.getValue().doubleValue());
        }).collect(Collectors.toList());

        Expression e = new Expression(formula);
        e.addConstants(variables);

        return BigDecimal.valueOf(e.calculate());
    }
}
