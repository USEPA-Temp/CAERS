/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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
