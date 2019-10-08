package gov.epa.cef.web.service.dto.postOrder;

import java.io.Serializable;


/***
 * ReleasePointApptPostOrderDto is used to traverse the object hierarchy from the bottom up.  This ReleasePointApptPostOrderDto will contain a reference to the ReleasePointPostOrderDto
 * but the ReleasePointPostOrderDto will not contain a list of these ReleasePointApptPostOrderDto objects.  This helps avoid circular references within the MapStruct mappers when 
 * traversing the hierarchy post order.
 * @author kbrundag
 *
 */
public class ReleasePointApptPostOrderDto implements Serializable{

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private ReleasePointPostOrderDto releasePoint;
    private EmissionsProcessPostOrderDto emissionsProcess;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public ReleasePointPostOrderDto getReleasePoint() {
    	return releasePoint;
    }
    public void setReleasePoint(ReleasePointPostOrderDto releasePoint) {
    	this.releasePoint = releasePoint;
    }
    
    public EmissionsProcessPostOrderDto getEmissionsProcess() {
    	return emissionsProcess;
    }
    public void setEmissionsProcess(EmissionsProcessPostOrderDto emissionsProcess) {
    	this.emissionsProcess = emissionsProcess;
    }
}
