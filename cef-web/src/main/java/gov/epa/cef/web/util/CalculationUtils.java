package gov.epa.cef.web.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.epa.cef.web.domain.EmissionFormulaVariable;

public class CalculationUtils {

//    private static final Logger logger = LoggerFactory.getLogger(CalculationUtils.class);

    private static final Argument FT3 = new Argument("ft3 = [ft] * [ft] * [ft]");
    private static final Argument STON = new Argument("sTon = 2000 * [lb]");
    private static final Argument BTU = new Argument("btu = 1055.05585 * [J]");
    private static final Argument W = new Argument("w = [J] / [s]");
    private static final Argument HP = new Argument("hp = 745.69987158 * [J] / [s]");
    private static final Argument YEAR = new Argument("year = 365 * [day]");
    private static final Argument LEAP_YEAR = new Argument("year = 365 * [day]");

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

    public static BigDecimal convertUnits(String sourceFormula, String targetFormula) {
        return convertUnits(sourceFormula, targetFormula, false);
    }

    public static BigDecimal convertUnits(String sourceFormula, String targetFormula, boolean leapYear) {

        String formula = "(1) * (" + sourceFormula + ") / (" + targetFormula + ")"; 
        Expression e = new Expression(formula);
        e.addArguments(FT3, STON, BTU, W, HP);

        if (leapYear) {
            e.addArguments(LEAP_YEAR);
        } else {
            e.addArguments(YEAR);
        }

//        logger.info(formula);

        return BigDecimal.valueOf(e.calculate());
    }

}
