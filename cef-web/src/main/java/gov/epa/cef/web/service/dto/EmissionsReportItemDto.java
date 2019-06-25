package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class EmissionsReportItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String identifier;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
