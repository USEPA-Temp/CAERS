package gov.epa.cef.web.service.dto.postOrder;

import java.io.Serializable;
import java.util.List;


/***
 * ControlPathPostOrderDto is used to traverse the object hierarchy from the bottom up.  The ControlAssignmentPostOrderDto will contain a reference to this ControlPathPostOrderDto
 * but this ControlPathPostOrderDto will not contain a list of ControlAssignmentPostOrderDto objects.  This helps avoid circular references when traversing the hierarchy post order.
 */
public class ControlPathPostOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private List<ReleasePointApptPostOrderDto> releasePointAppts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReleasePointApptPostOrderDto> getReleasePointAppts() {
        return releasePointAppts;
    }

    public void setReleasePointAppts(List<ReleasePointApptPostOrderDto> releasePoints) {
        this.releasePointAppts = releasePoints;
    }

}
