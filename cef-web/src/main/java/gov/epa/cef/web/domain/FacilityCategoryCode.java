package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * FacilityCategoryCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "facility_category_code")
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