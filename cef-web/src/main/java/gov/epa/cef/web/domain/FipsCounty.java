package gov.epa.cef.web.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * FipsCounty entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fips_county")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class FipsCounty implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // Fields

    @Id
    @Column(name = "code", nullable = false, length = 5)
    private String code;

    @Column(name = "county_code", nullable = false, length = 3)
    private String countyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_code", nullable = false)
    private FipsStateCode fipsStateCode;

    @Column(name = "name", length = 43)
    private String name;

    // Property accessors
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public FipsStateCode getFipsStateCode() {
        return this.fipsStateCode;
    }

    public void setFipsStateCode(FipsStateCode fipsStateCode) {
        this.fipsStateCode = fipsStateCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
