package gov.epa.cef.web.service.dto.postOrder;

import java.io.Serializable;


/***
 * EmissionsProcessPostOrderDto is used to traverse the object hierarchy from the bottom up.  The EmissionsProcessPostOrderDto will contain a reference to this EmissionsUnitPostOrderDto
 * but the EmissionsUnitPostOrderDto will not contain a list of these EmissionsProcessPostOrderDto objects.  This helps avoid circular references within the MapStruct mappers when 
 * traversing the hierarchy post order.
 * @author kbrundag
 *
 */
public class EmissionsProcessPostOrderDto implements Serializable {

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String emissionsProcessIdentifier;
    private String description;
    private EmissionsUnitPostOrderDto emissionsUnit;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmissionsProcessIdentifier() {
        return emissionsProcessIdentifier;
    }
    public void setEmissionsProcessIdentifier(String emissionsProcessIdentifier) {
        this.emissionsProcessIdentifier = emissionsProcessIdentifier;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public EmissionsUnitPostOrderDto getEmissionsUnit() {
        return emissionsUnit;
    }
    public void setEmissionsUnit(EmissionsUnitPostOrderDto emissionsUnit) {
        this.emissionsUnit = emissionsUnit;
    }
}

