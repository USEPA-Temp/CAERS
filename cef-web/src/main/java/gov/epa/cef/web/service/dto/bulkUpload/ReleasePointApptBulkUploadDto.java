package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ReleasePointApptBulkUploadDto extends BaseWorksheetDto implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Release Point Apportionment ID is required.")
    private Long id;

    @NotNull(message = "Release Point ID is required.")
    private Long releasePointId;

    @NotNull(message = "Emission Process ID is required.")
    private Long emissionProcessId;

    @NotNull(message = "Percent is required.")
    @Digits(integer = 3, fraction = 1,
        message = "Percent is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double percent;

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

    public Double getPercent() {
        return percent;
    }
    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Long getControlPathId() {
        return controlPathId;
    }
    public void setControlPathId(Long controlPathId) {
        this.controlPathId = controlPathId;
    }
}
