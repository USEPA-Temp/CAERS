package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.List;


public class ControlPathDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private String pathId;
    private List<ControlAssignmentDto> assignments;
    private Long facilitySiteId;

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

    public List<ControlAssignmentDto> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<ControlAssignmentDto> assignments) {
        this.assignments = assignments;
    }

	public String getPathId() {
		return pathId;
	}

	public void setPathId(String pathId) {
		this.pathId = pathId;
	}
	
    public ControlPathDto withId(Long id) {
        setId(id);
        return this;
    }

	public Long getFacilitySiteId() {
		return facilitySiteId;
	}

	public void setFacilitySiteId(Long facilitySiteId) {
		this.facilitySiteId = facilitySiteId;
	}

}
