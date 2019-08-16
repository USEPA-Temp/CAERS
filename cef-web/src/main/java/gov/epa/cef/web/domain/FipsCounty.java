package gov.epa.cef.web.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "code", column = @Column(name = "code", nullable = false, length = 3)),
            @AttributeOverride(name = "stateCode", column = @Column(name = "state_code", nullable = false, length = 2)) })
    private FipsCountyId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_code", nullable = false, insertable = false, updatable = false)
    private FipsStateCode fipsStateCode;

    @Column(name = "name", length = 43)
    private String name;

    // Property accessors
    public FipsCountyId getId() {
        return this.id;
    }

    public void setId(FipsCountyId id) {
        this.id = id;
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
