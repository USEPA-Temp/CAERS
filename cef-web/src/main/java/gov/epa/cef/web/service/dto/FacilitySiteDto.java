package gov.epa.cef.web.service.dto;

import java.io.Serializable;

import gov.epa.cef.web.domain.FacilityCategoryCode;
import gov.epa.cef.web.domain.FacilitySourceTypeCode;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ProgramSystemCode;

public class FacilitySiteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private FacilityCategoryCode facilityCategoryCode;
    private FacilitySourceTypeCode facilitySourceTypeCode;
    private NaicsCode naicsCode;
    private ProgramSystemCode programSystemCode;
    private OperatingStatusCode operatingStatusCode;
    private EmissionsReportDto emissionsReport;
    private String frsFacilityId;
    private String altSiteIdentifier;
    private String name;
    private String description;
    private Short statusYear;
    private String streetAddress;
    private String city;
    private String county;
    private String stateCode;
    private String countryCode;
    private String postalCode;
    private Double latitude;
    private Double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FacilityCategoryCode getFacilityCategoryCode() {
        return facilityCategoryCode;
    }

    public void setFacilityCategoryCode(FacilityCategoryCode facilityCategoryCode) {
        this.facilityCategoryCode = facilityCategoryCode;
    }

    public FacilitySourceTypeCode getFacilitySourceTypeCode() {
        return facilitySourceTypeCode;
    }

    public void setFacilitySourceTypeCode(FacilitySourceTypeCode facilitySourceTypeCode) {
        this.facilitySourceTypeCode = facilitySourceTypeCode;
    }

    public NaicsCode getNaicsCode() {
        return naicsCode;
    }

    public void setNaicsCode(NaicsCode naicsCode) {
        this.naicsCode = naicsCode;
    }

    public ProgramSystemCode getProgramSystemCode() {
        return programSystemCode;
    }

    public void setProgramSystemCode(ProgramSystemCode programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    public OperatingStatusCode getOperatingStatusCode() {
        return operatingStatusCode;
    }

    public void setOperatingStatusCode(OperatingStatusCode operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public EmissionsReportDto getEmissionsReport() {
        return emissionsReport;
    }

    public void setEmissionsReport(EmissionsReportDto emissionsReport) {
        this.emissionsReport = emissionsReport;
    }

    public String getFrsFacilityId() {
        return frsFacilityId;
    }

    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
