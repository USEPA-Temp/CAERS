package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * RegionCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "region_code")
public class RegionCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    // Constructors

    /** default constructor */
    public RegionCode() {
    }

}