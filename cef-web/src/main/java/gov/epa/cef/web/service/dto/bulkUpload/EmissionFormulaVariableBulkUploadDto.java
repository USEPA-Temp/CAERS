package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

public class EmissionFormulaVariableBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Emission Factor Formula ID is required.")
    private Long id;

    @NotNull(message = "Emission is required.")
    private Long emissionId;

    @NotBlank(message = "Emission Formula Variable Code is required.")
    @Size(max = 20, message = "Emission Formula Variable Code can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionFormulaVariableCode;

    @NotNull(message = "Value is required.")
    private BigDecimal value;

    public EmissionFormulaVariableBulkUploadDto() {

        super(WorksheetName.EmissionFormulaVariable);
    }

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

    public String getEmissionFormulaVariableCode() {
        return emissionFormulaVariableCode;
    }
    public void setEmissionFormulaVariableCode(String emissionFormulaVariableCode) {
        this.emissionFormulaVariableCode = emissionFormulaVariableCode;
    }

    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
