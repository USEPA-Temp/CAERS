package gov.epa.cef.web.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

@Entity
@Table(name = "emissions_operating_type_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EmissionsOperatingTypeCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "short_name", nullable = false, length = 50)
    private String shortName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}
