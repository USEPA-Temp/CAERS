package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class CodeLookupDto implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String code;
    protected String description;

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

}