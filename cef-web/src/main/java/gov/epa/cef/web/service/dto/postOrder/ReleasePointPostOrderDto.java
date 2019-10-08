package gov.epa.cef.web.service.dto.postOrder;

import java.io.Serializable;


/***
 * ReleasePointPostOrderDto is used to traverse the object hierarchy from the bottom up.  The ReleasePointApptPostOrderDto will contain a reference to the ReleasePointPostOrderDto
 * but this ReleasePointPostOrderDto will not contain a list of ReleasePointApptPostOrderDto objects.  This helps avoid circular references within the MapStruct mappers when 
 * traversing the hierarchy post order.
 * @author kbrundag
 *
 */
public class ReleasePointPostOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String releasePointIdentifier;
    private String description;
    private String comments;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getReleasePointIdentifier() {
        return releasePointIdentifier;
    }
    public void setReleasePointIdentifier(String releasePointIdentifier) {
        this.releasePointIdentifier = releasePointIdentifier;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

}
