package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.List;

public class ControlPathDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private List<ReleasePointApptUpDto> releasePointAppts;

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

    public List<ReleasePointApptUpDto> getReleasePointAppts() {
        return releasePointAppts;
    }

    public void setReleasePointAppts(List<ReleasePointApptUpDto> releasePoints) {
        this.releasePointAppts = releasePoints;
    }

}
