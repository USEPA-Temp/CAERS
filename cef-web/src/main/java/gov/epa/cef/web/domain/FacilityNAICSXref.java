package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "facility_naics_xref")
public class FacilityNAICSXref extends BaseAuditEntity {
    
    private static final long serialVersionUID = 1L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_site_id", nullable = false)
    private FacilitySite facilitySite;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "naics_code", nullable = false)
    private NaicsCode naicsCode;
    
    @Column(name = "primary_flag", nullable = false)
    private boolean primaryFlag;


    /**
     * Default constructor
     */
    public FacilityNAICSXref() {}


    /**
     * Copy constructor
     * @param facilitySite
     * @param naicsXref
     */
    public FacilityNAICSXref(FacilitySite facilitySite, FacilityNAICSXref originalNaicsXref) {
    	this.id = originalNaicsXref.getId();
    	this.facilitySite = facilitySite;
    	this.naicsCode = originalNaicsXref.getNaicsCode();
    	this.primaryFlag = originalNaicsXref.isPrimaryFlag();
    }
    
    public FacilitySite getFacilitySite() {
        return facilitySite;
    }

    public NaicsCode getNaicsCode() {
        return naicsCode;
    }

    public boolean isPrimaryFlag() {
        return primaryFlag;
    }

    public void setFacilitySite(FacilitySite facilitySite) {
        this.facilitySite = facilitySite;
    }

    public void setNaicsCode(NaicsCode naicsCode) {
        this.naicsCode = naicsCode;
    }

    public void setPrimaryFlag(boolean primaryFlag) {
        this.primaryFlag = primaryFlag;
    }
    
    
    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}
