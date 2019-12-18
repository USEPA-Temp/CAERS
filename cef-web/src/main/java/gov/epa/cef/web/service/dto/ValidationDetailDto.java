package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String identifier;
    private EntityType type;
    private String description;
    private List<ValidationDetailDto> parents = new ArrayList<>();

    public ValidationDetailDto() {}

    public ValidationDetailDto(Long id, String identifier, EntityType type) {
        this.id = id;
        this.identifier = identifier;
        this.type = type;
    }
    
    public ValidationDetailDto(Long id, String identifier, EntityType type, String description) {
        this.id = id;
        this.identifier = identifier;
        this.type = type;
        this.description = description;
    }

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

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ValidationDetailDto> getParents() {
        return parents;
    }

    public void setParents(List<ValidationDetailDto> parents) {

        this.parents.clear();
        if (parents != null) {
            this.parents.addAll(parents);
        }
    }

    public ValidationDetailDto withParents(List<ValidationDetailDto> parents) {

        this.parents.clear();
        if (parents != null) {
            this.parents.addAll(parents);
        }
        return this;
    }

}
