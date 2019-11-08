package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class ControlAssignmentBulkUploadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long controlId;
    private Long controlPathId;
    private Long controlPathChildId;
    private Integer sequenceNumber;
    private Double percentApportionment;

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
