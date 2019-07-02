package gov.epa.cef.web.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import gov.epa.cef.web.domain.common.BaseEntity;

/**
 * Entity of the  View v_emissions_by_facility_and_cas 
 */
@Entity
@Immutable
@Table(name = "v_emissions_by_facility_and_cas")
public class EmissionsByFacilityAndCAS extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "frs_facility_id")
    private String frsFacilityId;
    
    @Column(name = "facility_name")
    private String facilityName;
    
    @Column(name = "year")
    private Short year;
    
    @Column(name = "pollutant_name")
    private String pollutantName;
    
    @Column(name = "pollutant_cas_id")
    private String pollutantCasId;
     
    @Column(name = "apportioned_emissions")
    private BigDecimal apportionedEmissions;
    
    @Column(name = "release_point_identifier")
    private String releasePointIdentifier;
    
    @Column(name = "release_point_type")
    private String releasePointType;
    
    @Column(name = "emissions_uom_code")
    private String emissionsUomCode;
    

    public String getFrsFacilityId() {
        return frsFacilityId;
    }

    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getPollutantName() {
        return pollutantName;
    }

    public void setPollutantName(String pollutantName) {
        this.pollutantName = pollutantName;
    }

    public String getPollutantCasId() {
        return pollutantCasId;
    }

    public void setPollutantCasId(String pollutantCasId) {
        this.pollutantCasId = pollutantCasId;
    }

    public String getEmissionsUomCode() {
        return emissionsUomCode;
    }

    public void setEmissionsUomCode(String emissionsUomCode) {
        this.emissionsUomCode = emissionsUomCode;
    }

    public BigDecimal getApportionedEmissions() {
        return apportionedEmissions;
    }

    public void setApportionedEmissions(BigDecimal apportionedEmissions) {
        this.apportionedEmissions = apportionedEmissions;
    }

    public String getReleasePointIdentifier() {
        return releasePointIdentifier;
    }

    public void setReleasePointIdentifier(String releasePointIdentifier) {
        this.releasePointIdentifier = releasePointIdentifier;
    }

    public String getReleasePointType() {
        return releasePointType;
    }

    public void setReleasePointType(String releasePointType) {
        this.releasePointType = releasePointType;
    }
    
}
