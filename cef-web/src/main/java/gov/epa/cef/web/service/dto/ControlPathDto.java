package gov.epa.cef.web.service.dto;

import java.io.Serializable;

import gov.epa.cef.web.domain.ControlType;

public class ControlPathDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private String controlOrder;
    private ControlType controlType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getControlOrder() {
        return controlOrder;
    }

    public void setControlOrder(String controlOrder) {
        this.controlOrder = controlOrder;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

}
