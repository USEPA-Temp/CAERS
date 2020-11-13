package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class ReleasePointApptBulkUploadDto extends BaseWorksheetDto implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Release Point Apportionment ID is required.")
    private Long id;

    @NotNull(message = "Release Point ID is required.")
    private Long releasePointId;

    @NotNull(message = "Emission Process ID is required.")
    private Long emissionProcessId;

    @NotBlank(message = "Percent Apportionment is required.")
    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1,2})?$",
        message = "Percent is not in expected numeric format: '{3}.{2}' digits; found '${validatedValue}'.")
    private String percent;

    private Long controlPathId;

    public ReleasePointApptBulkUploadDto() {

        super(WorksheetName.ReleasePointAppt);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getReleasePointId() {
        return releasePointId;
    }
    public void setReleasePointId(Long releasePointId) {
        this.releasePointId = releasePointId;
    }

    public Long getEmissionProcessId() {
        return emissionProcessId;
    }
    public void setEmissionProcessId(Long emissionProcessId) {
        this.emissionProcessId = emissionProcessId;
    }

    public String getPercent() {
        return percent;
    }
    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Long getControlPathId() {
        return controlPathId;
    }
    public void setControlPathId(Long controlPathId) {
        this.controlPathId = controlPathId;
    }
}
