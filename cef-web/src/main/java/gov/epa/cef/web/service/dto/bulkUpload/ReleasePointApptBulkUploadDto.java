package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class ReleasePointApptBulkUploadDto implements IWorksheetAware, Serializable{

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long releasePointId;
    private Long emissionProcessId;
    private Double percent;
    private Long controlPathId;

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
