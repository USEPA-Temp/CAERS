package gov.epa.cef.web.service.dto.postOrder;

import java.io.Serializable;

import gov.epa.cef.web.service.dto.EmissionsReportItemDto;

/***
 * ControlAssignmentPostOrderDto is used to traverse the object hierarchy from the bottom up.  This ControlAssignmentPostOrderDto will contain a reference to the ControlPathPostOrderDto
 * but the ControlPathPostOrderDto will not contain a list of these ControlAssignmentPostOrderDto objects.  This helps avoid circular references when traversing the hierarchy post order.
 */
public class ControlAssignmentPostOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private EmissionsReportItemDto control;
    private ControlPathPostOrderDto controlPath;
    private String description;
    private Integer sequenceNumber;
    private ControlPathPostOrderDto controlPathChild;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmissionsReportItemDto getControl() {
        return control;
    }

    public void setControl(EmissionsReportItemDto control) {
        this.control = control;
    }

    public ControlPathPostOrderDto getControlPath() {
        return controlPath;
    }

    public void setControlPath(ControlPathPostOrderDto controlPath) {
        this.controlPath = controlPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public ControlPathPostOrderDto getControlPathChild() {
        return controlPathChild;
    }

    public void setControlPathChild(ControlPathPostOrderDto controlPathChild) {
        this.controlPathChild = controlPathChild;
    }

}
