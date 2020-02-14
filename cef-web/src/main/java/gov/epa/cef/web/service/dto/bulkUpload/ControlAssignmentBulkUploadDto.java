package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ControlAssignmentBulkUploadDto  extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Control Assignment ID is required.")
    private Long id;

    private Long controlId;

    @NotNull(message = "Control Path ID is required.")
    private Long controlPathId;

    private Long controlPathChildId;

    @NotNull(message = "Sequence number is required.")
    private Integer sequenceNumber;

    @NotNull(message = "Percent Apportionment is required.")
    private Double percentApportionment;

    public ControlAssignmentBulkUploadDto() {

        super(WorksheetName.ControlAssignment);
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

    public Long getControlPathId() {
        return controlPathId;
    }

    public void setControlPathId(Long controlPathId) {
        this.controlPathId = controlPathId;
    }

    public Long getControlPathChildId() {
        return controlPathChildId;
    }

    public void setControlPathChildId(Long controlPathChildId) {
        this.controlPathChildId = controlPathChildId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Double getPercentApportionment() {
        return percentApportionment;
    }

    public void setPercentApportionment(Double percentApportionment) {
        this.percentApportionment = percentApportionment;
    }

}
