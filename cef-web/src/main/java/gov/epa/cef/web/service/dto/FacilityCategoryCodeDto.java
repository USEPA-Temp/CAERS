package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class FacilityCategoryCodeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String description;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
