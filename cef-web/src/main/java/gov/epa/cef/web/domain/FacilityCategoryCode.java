package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseLookupEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * FacilityCategoryCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "facility_category_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class FacilityCategoryCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    // Fields

    @Column(name = "name", length = 20)
    private String name;

    // Property accessors

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
