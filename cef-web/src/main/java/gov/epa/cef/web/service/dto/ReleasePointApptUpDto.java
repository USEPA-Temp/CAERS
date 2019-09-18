package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ReleasePointApptUpDto implements Serializable{

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private ReleasePointUpDto releasePoint;
    private EmissionsProcessUpDto emissionsProcess;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public ReleasePointUpDto getReleasePoint() {
    	return releasePoint;
    }
    public void setReleasePoint(ReleasePointUpDto releasePoint) {
    	this.releasePoint = releasePoint;
    }
    
    public EmissionsProcessUpDto getEmissionsProcess() {
    	return emissionsProcess;
    }
    public void setEmissionsProcess(EmissionsProcessUpDto emissionsProcess) {
    	this.emissionsProcess = emissionsProcess;
    }
}
