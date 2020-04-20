package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class EmissionFormulaVariableBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Emission Formula Variable Code is required.")
    @Size(max = 20, message = "Emission Formula Variable Code can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionFormulaVariableCode;

    @NotNull(message = "Emission is required.")
    private Long emissionId;

    @NotNull(message = "Emission Factor Formula ID is required.")
    private Long id;

    @NotBlank(message = "Value is required.")
    @Pattern(regexp = PositiveDecimalPattern,
        message = "Value is not in expected numeric format; found '${validatedValue}'.")
    private String value;

    public EmissionFormulaVariableBulkUploadDto() {

        super(WorksheetName.EmissionFormulaVariable);
    }

    public String getEmissionFormulaVariableCode() {

        return emissionFormulaVariableCode;
    }

    public void setEmissionFormulaVariableCode(String emissionFormulaVariableCode) {

        this.emissionFormulaVariableCode = emissionFormulaVariableCode;
    }

    public Long getEmissionId() {

        return emissionId;
    }

    public void setEmissionId(Long emissionId) {

        this.emissionId = emissionId;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

}
