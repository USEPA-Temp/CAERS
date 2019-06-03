package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ReleasePointApptDto implements Serializable{

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;

    private Long releasePointId;
    
    private String releasePointDescription;
    
    private Double percent;

    
    public Long getReleasePointId() {
        return releasePointId;
    }

    public void setReleasePointId(Long releasePointId) {
        this.releasePointId = releasePointId;
    }

    public String getReleasePointDescription() {
        return releasePointDescription;
    }

    public void setReleasePointDescription(String releasePointDescription) {
        this.releasePointDescription = releasePointDescription;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
    
    
}
