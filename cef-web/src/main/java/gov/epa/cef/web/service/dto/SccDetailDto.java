package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.Map;

public class SccDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uid;
    private String code;
    private String lastUpdated;
    private Map<String, SccAttributeDto> attributes;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, SccAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, SccAttributeDto> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SccDetailDto [uid=").append(uid).append(", code=").append(code).append(", lastUpdated=")
                .append(lastUpdated).append(", attributes=").append(attributes).append("]");
        return builder.toString();
    }

}
