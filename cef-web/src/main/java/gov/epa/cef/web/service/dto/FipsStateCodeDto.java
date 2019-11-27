package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class FipsStateCodeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String uspsCode;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUspsCode() {
        return uspsCode;
    }

    public void setUspsCode(String uspsCode) {
        this.uspsCode = uspsCode;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
