package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class SccAttributeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uid;
    private String text;

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SccAttributeDto [uid=").append(uid).append(", text=").append(text).append("]");
        return builder.toString();
    }


}
