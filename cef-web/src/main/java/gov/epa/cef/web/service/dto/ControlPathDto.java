package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.List;

import gov.epa.cef.web.service.dto.postOrder.ReleasePointApptPostOrderDto;

public class ControlPathDto implements Serializable {

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
