package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * FipsCountryCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fips_country_code")
public class FipsCountryCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

}