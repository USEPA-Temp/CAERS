package gov.epa.cef.web.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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

public class FipsCounty implements java.io.Serializable {

    // Fields

    private FipsCountyId id;
    private FipsStateCode fipsStateCode;
    private String name;

    // Constructors

    /** default constructor */
    public FipsCounty() {
    }

    /** minimal constructor */
    public FipsCounty(FipsCountyId id, FipsStateCode fipsStateCode) {
        this.id = id;
        this.fipsStateCode = fipsStateCode;
    }

    /** full constructor */
    public FipsCounty(FipsCountyId id, FipsStateCode fipsStateCode, String name) {
        this.id = id;
        this.fipsStateCode = fipsStateCode;
        this.name = name;
    }

    // Property accessors
    @EmbeddedId

    @AttributeOverrides({
            @AttributeOverride(name = "code", column = @Column(name = "code", nullable = false, length = 3)),
            @AttributeOverride(name = "stateCode", column = @Column(name = "state_code", nullable = false, length = 2)) })

    public FipsCountyId getId() {
        return this.id;
    }

    public void setId(FipsCountyId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_code", nullable = false, insertable = false, updatable = false)

    public FipsStateCode getFipsStateCode() {
        return this.fipsStateCode;
    }

    public void setFipsStateCode(FipsStateCode fipsStateCode) {
        this.fipsStateCode = fipsStateCode;
    }

    @Column(name = "name", length = 43)

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}