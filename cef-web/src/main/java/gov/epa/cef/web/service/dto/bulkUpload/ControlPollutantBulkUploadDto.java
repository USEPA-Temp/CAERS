package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ControlPollutantBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Control Pollutant ID is required.")
    private Long id;

    @NotNull(message = "Control ID is required.")
    private Long controlId;

    @NotBlank(message = "Pollutant Code is required.")
    @Size(max = 12, message = "Pollutant Code can not exceed {max} chars; found '${validatedValue}'.")
    private String pollutantCode;

    @NotBlank(message = "Percent Reduction is required.")
    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1})?$",
        message = "Percent Reduction Efficiency is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentReduction;

    public ControlPollutantBulkUploadDto() {

        super(WorksheetName.ControlPollutant);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getControlId() {
        return controlId;
    }

    public void setControlId(Long controlId) {
        this.controlId = controlId;
    }

    public String getPollutantCode() {
        return pollutantCode;
    }

    public void setPollutantCode(String pollutant) {
        this.pollutantCode = pollutant;
    }

    public String getPercentReduction() {
        return percentReduction;
    }

    public void setPercentReduction(String percentReduction) {
        this.percentReduction = percentReduction;
    }

}
