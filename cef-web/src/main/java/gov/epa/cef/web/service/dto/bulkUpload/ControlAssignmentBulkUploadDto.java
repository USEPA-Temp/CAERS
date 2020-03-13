package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ControlAssignmentBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long controlId;

    private Long controlPathChildId;

    @NotNull(message = "Control Path ID is required.")
    private Long controlPathId;

    @NotNull(message = "Control Assignment ID is required.")
    private Long id;

    @NotNull(message = "Percent Apportionment is required.")
    @Digits(integer = 3, fraction = 1,
        message = "Percent Apportionment is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double percentApportionment;

    @NotNull(message = "Sequence number is required.")
    private Integer sequenceNumber;

    public ControlAssignmentBulkUploadDto() {

        super(WorksheetName.ControlAssignment);
    }

    public Long getControlId() {

        return controlId;
    }

    public void setControlId(Long controlId) {

        this.controlId = controlId;
    }

    public Long getControlPathChildId() {

        return controlPathChildId;
    }

    public void setControlPathChildId(Long controlPathChildId) {

        this.controlPathChildId = controlPathChildId;
    }

    public Long getControlPathId() {

        return controlPathId;
    }

    public void setControlPathId(Long controlPathId) {

        this.controlPathId = controlPathId;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Double getPercentApportionment() {

        return percentApportionment;
    }

    public void setPercentApportionment(Double percentApportionment) {

        this.percentApportionment = percentApportionment;
    }

    public Integer getSequenceNumber() {

        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {

        this.sequenceNumber = sequenceNumber;
    }

}
