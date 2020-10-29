package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseLookupEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;


/**
 * OperatingStatusCode entity
 */
@Entity
@Table(name = "operating_status_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class OperatingStatusCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "sub_facility_status", nullable = false)
    private Boolean subFacilityStatus;

    @Column(name = "facility_status", nullable = false)
    private Boolean facilityStatus;


    public Boolean getSubFacilityStatus() {
        return subFacilityStatus;
    }
    public void setSubFacilityStatus(Boolean subFacilityStatus) {
        this.subFacilityStatus = subFacilityStatus;
    }

    public Boolean getFacilityStatus() {
        return facilityStatus;
    }
    public void setFacilityStatus(Boolean facilityStatus) {
        this.facilityStatus = facilityStatus;
    }
}
