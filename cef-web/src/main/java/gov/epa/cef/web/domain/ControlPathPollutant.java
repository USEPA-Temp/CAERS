package gov.epa.cef.web.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "control_path_pollutant")
public class ControlPathPollutant extends BaseAuditEntity {
	
	private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "control_path_id", nullable = false)
    private ControlPath controlPath;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pollutant_code")
    private Pollutant pollutant;
    
    @Column(name = "percent_reduction", precision = 6, scale = 3, nullable = false)
    private BigDecimal percentReduction;

    
    /**
     * Default constructor
     */
    public ControlPathPollutant() {}
    
    
    /**
     * Copy constructor
     * @param controlPath
     * @param originalControlPathPollutant
     */
    public ControlPathPollutant(ControlPath controlPath, ControlPathPollutant originalControlPathPollutant) {
    	this.id = originalControlPathPollutant.getId();
    	this.controlPath = controlPath;
    	this.pollutant = originalControlPathPollutant.getPollutant();
    	this.percentReduction = originalControlPathPollutant.percentReduction;
    }
    
    public ControlPath getControlPath() {
        return controlPath;
    }

    public void setControlPath(ControlPath controlPath) {
        this.controlPath = controlPath;
    }

    public Pollutant getPollutant() {
        return pollutant;
    }

    public void setPollutant(Pollutant pollutant) {
        this.pollutant = pollutant;
    }

    public BigDecimal getPercentReduction() {
        return percentReduction;
    }

    public void setPercentReduction(BigDecimal percentReduction) {
        this.percentReduction = percentReduction;
    }
    
    
    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}
