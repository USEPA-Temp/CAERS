package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;
import java.math.BigDecimal;


public class FacilitySiteBulkUploadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String frsFacilityId;
    private String altSiteIdentifier;
    private String facilityCategoryCode;
    private String facilitySourceTypeCode;
    private String name;
    private String description;
    private String operatingStatusCode;
    private Short statusYear;
    private String programSystemCode;
    private String streetAddress;
    private String city;
    private String county;
    private String stateCode;
    private String countryCode;
    private String postalCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String mailingStreetAddress;
    private String mailingCity;
    private String mailingStateCode;
    private String mailingPostalCode;
    private String mailingCountryCode;
    private String eisProgramId;
    private String tribalCode;
    private Integer naicsCode;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFacilityCategoryCode() {
        return facilityCategoryCode;
    }
    public void setFacilityCategoryCode(String facilityCategoryCode) {
        this.facilityCategoryCode = facilityCategoryCode;
    }

    public String getFacilitySourceTypeCode() {
        return facilitySourceTypeCode;
    }
    public void setFacilitySourceTypeCode(String facilitySourceTypeCode) {
        this.facilitySourceTypeCode = facilitySourceTypeCode;
    }

    public String getProgramSystemCode() {
        return programSystemCode;
    }
    public void setProgramSystemCode(String programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    public String getOperatingStatusCode() {
        return operatingStatusCode;
    }
    public void setOperatingStatusCode(String operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public String getTribalCode() {
        return tribalCode;
    }
    public void setTribalCode(String tribalCode) {
        this.tribalCode = tribalCode;
    }

    public Integer getNaicsCode() {
        return naicsCode;
    }
    public void setNaicsCode(Integer naicsCode) {
        this.naicsCode = naicsCode;
    }

    public String getFrsFacilityId() {
        return frsFacilityId;
    }
    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
    }

    public String getEisProgramId() {
        return eisProgramId;
    }
    public void setEisProgramId(String eisProgramId) {
        this.eisProgramId = eisProgramId;
    }

    public String getAltSiteIdentifier() {
        return altSiteIdentifier;
    }
    public void setAltSiteIdentifier(String altSiteIdentifier) {
        this.altSiteIdentifier = altSiteIdentifier;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Short getStatusYear() {
        return statusYear;
    }
    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }

    public String getStateCode() {
        return stateCode;
    }
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getMailingStreetAddress() {
        return mailingStreetAddress;
    }
    public void setMailingStreetAddress(String mailingStreetAddress) {
        this.mailingStreetAddress = mailingStreetAddress;
    }

    public String getMailingCity() {
        return mailingCity;
    }
    public void setMailingCity(String mailingCity) {
        this.mailingCity = mailingCity;
    }

    public String getMailingStateCode() {
        return mailingStateCode;
    }
    public void setMailingStateCode(String mailingStateCode) {
        this.mailingStateCode = mailingStateCode;
    }

    public String getMailingPostalCode() {
        return mailingPostalCode;
    }
    public void setMailingPostalCode(String mailingPostalCode) {
        this.mailingPostalCode = mailingPostalCode;
    }

    public String getMailingCountryCode() {
        return mailingCountryCode;
    }
    public void setMailingCountryCode(String mailingCountryCode) {
        this.mailingCountryCode = mailingCountryCode;
    }
}
