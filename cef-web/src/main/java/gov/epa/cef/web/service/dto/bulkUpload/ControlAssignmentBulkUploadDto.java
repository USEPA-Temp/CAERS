package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class ControlAssignmentBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String controlId;

    private String controlPathChildId;

    @NotNull(message = "Control Path ID is required.")
    private String controlPathId;

    @NotNull(message = "Control Assignment ID is required.")
    private Long id;

    @NotBlank(message = "Percent Apportionment is required.")
    @Pattern(regexp = PercentPattern,
        message = "Percent Apportionment is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentApportionment;

    @NotBlank(message = "Sequence number is required.")
    @Pattern(regexp = PositiveIntPattern,
        message = "Sequence number is not in expected numeric format: '{10}' digits; found '${validatedValue}'.")
    private String sequenceNumber;

    public ControlAssignmentBulkUploadDto() {

        super(WorksheetName.ControlAssignment);
    }

    public String getControlId() {

        return controlId;
    }

    public void setControlId(String controlId) {

        this.controlId = controlId;
    }

    public String getControlPathChildId() {

        return controlPathChildId;
    }

    public void setControlPathChildId(String controlPathChildId) {

        this.controlPathChildId = controlPathChildId;
    }

    public String getControlPathId() {

        return controlPathId;
    }

    public void setControlPathId(String controlPathId) {

        this.controlPathId = controlPathId;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getPercentApportionment() {

        return percentApportionment;
    }

    public void setPercentApportionment(String percentApportionment) {

        this.percentApportionment = percentApportionment;
    }

    public String getSequenceNumber() {

        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {

        this.sequenceNumber = sequenceNumber;
    }

}
