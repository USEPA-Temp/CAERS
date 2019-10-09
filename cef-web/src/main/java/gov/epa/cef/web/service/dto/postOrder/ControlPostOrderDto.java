package gov.epa.cef.web.service.dto.postOrder;

import java.io.Serializable;
import java.util.List;
import gov.epa.cef.web.service.dto.ControlDto;

public class ControlPostOrderDto extends ControlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ControlAssignmentPostOrderDto> assignments;


    public List<ControlAssignmentPostOrderDto> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<ControlAssignmentPostOrderDto> assignments) {
        this.assignments = assignments;
    }

}
