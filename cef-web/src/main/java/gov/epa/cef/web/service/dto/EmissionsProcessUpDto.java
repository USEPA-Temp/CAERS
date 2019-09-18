package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class EmissionsProcessUpDto implements Serializable {

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String emissionsProcessIdentifier;
    private String description;
    private EmissionsUnitUpDto emissionsUnit;

    
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
    
    public EmissionsUnitUpDto getEmissionsUnit() {
        return emissionsUnit;
    }
    public void setEmissionsUnit(EmissionsUnitUpDto emissionsUnit) {
        this.emissionsUnit = emissionsUnit;
    }
}

