package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class EmissionsUnitUpDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String unitIdentifier;
    private String description;
    private String typeCodeDescription;
    private String comments;


    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUnitIdentifier() {
        return this.unitIdentifier;
    }
    public void setUnitIdentifier(String unitIdentifier) {
        this.unitIdentifier = unitIdentifier;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeCodeDescription() {
        return this.typeCodeDescription;
    }
    public void setTypeCodeDescription(String typeCodeDescription) {
        this.typeCodeDescription = typeCodeDescription;
    }
    
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

}