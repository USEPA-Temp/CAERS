package gov.epa.cef.web.exception;

import java.util.ArrayList;
import java.util.List;

public class CalculationException extends ApplicationException {

    private List<String> missingVariables;

    public CalculationException(List<String> variables) {

        super(ApplicationErrorCode.E_CALC_MISSING_VARIABLE, 
                String.format("Emission Factor could not be calculated. Variable(s) [%s] are missing.", String.join(", ", variables)));

        this.missingVariables = new ArrayList<>();
        if (variables != null) {
            this.missingVariables.addAll(variables);
        }
    }

    public List<String> getMissingVariables() {
        return missingVariables;
    }
}
