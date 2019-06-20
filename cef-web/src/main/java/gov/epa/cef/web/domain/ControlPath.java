package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "control_path")
public class ControlPath extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "control_order", nullable = false, length = 3)
    private String controlOrder;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "control_type", nullable = false, length = 10)
    private ControlType controlType;

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
