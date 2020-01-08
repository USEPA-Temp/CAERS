package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmissionFormulaVariableDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long emissionId;
    private EfVariableCodeDto emissionFactorVariableCode;
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

    public EfVariableCodeDto getEmissionFactorVariableCode() {
        return emissionFactorVariableCode;
    }

    public void setEmissionFactorVariableCode(EfVariableCodeDto variable) {
        this.emissionFactorVariableCode = variable;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
