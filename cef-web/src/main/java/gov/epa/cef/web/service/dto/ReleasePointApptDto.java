package gov.epa.cef.web.service.dto;

import java.io.Serializable;


public class ReleasePointApptDto implements Serializable{

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;
    
    private Long id;

    private Long releasePointId;
    
    private Long facilitySiteId;
    
    private Long emissionsProcessId;
    
    private String releasePointIdentifier;
    
    private String releasePointDescription;

	private CodeLookupDto releasePointTypeCode;
    
    private Double percent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReleasePointId() {
        return releasePointId;
    }

    public void setReleasePointId(Long releasePointId) {
        this.releasePointId = releasePointId;
    }

    public String getReleasePointIdentifier() {
        return releasePointIdentifier;
    }

    public void setReleasePointIdentifier(String releasePointIdentifier) {
        this.releasePointIdentifier = releasePointIdentifier;
    }

    public String getReleasePointDescription() {
        return releasePointDescription;
    }

    public void setReleasePointDescription(String releasePointDescription) {
        this.releasePointDescription = releasePointDescription;
    }

    public CodeLookupDto getReleasePointTypeCode() {
        return releasePointTypeCode;
    }

    public void setReleasePointTypeCode(CodeLookupDto releasePointTypeCode) {
        this.releasePointTypeCode = releasePointTypeCode;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

	public Long getEmissionsProcessId() {
		return emissionsProcessId;
	}

	public void setEmissionsProcessId(Long emissionsProcessId) {
		this.emissionsProcessId = emissionsProcessId;
	}

	public Long getFacilitySiteId() {
		return facilitySiteId;
	}

	public void setFacilitySiteId(Long facilitySiteId) {
		this.facilitySiteId = facilitySiteId;
	}
    
    
}
