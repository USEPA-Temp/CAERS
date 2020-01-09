package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmissionFormulaVariableDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long emissionId;
    private EmissionFormulaVariableCodeDto variableCode;
    private BigDecimal value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmissionId() {
        return emissionId;
    }

    public void setEmissionId(Long emissionId) {
        this.emissionId = emissionId;
    }

    public EmissionFormulaVariableCodeDto getVariableCode() {
        return variableCode;
    }

    public void setVariableCode(EmissionFormulaVariableCodeDto variable) {
        this.variableCode = variable;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
