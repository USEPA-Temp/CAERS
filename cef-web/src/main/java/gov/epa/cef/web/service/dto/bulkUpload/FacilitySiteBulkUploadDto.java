package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class FacilitySiteBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Facility Site ID is required.")
    private Long id;

    @Size(max = 22, message = "FRS Facility ID can not exceed {max} chars; found '${validatedValue}'.")
    private String frsFacilityId;

    @Size(max = 30, message = "Alternate Site Identifier can not exceed {max} chars; found '${validatedValue}'.")
    private String altSiteIdentifier;

    @Size(max = 20, message = "Category Code can not exceed {max} chars; found '${validatedValue}'.")
    private String facilityCategoryCode;

    @Size(max = 20, message = "Source Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String facilitySourceTypeCode;

    @NotBlank(message = "Facility Name is required.")
    @Size(max = 80, message = "Facility Name can not exceed {max} chars; found '${validatedValue}'.")
    private String name;

    @Size(max = 100, message = "Description can not exceed {max} chars; found '${validatedValue}'.")
    private String description;

    @NotBlank(message = "Operating Status Code is required.")
    @Size(max = 20, message = "Operating Status Code can not exceed {max} chars; found '${validatedValue}'.")
    private String operatingStatusCode;

    @NotBlank(message = "Status Year is required.")
    @Pattern(regexp = YearPattern,
        message = "Status Year is not in expected format: {4} digits; found '${validatedValue}'.")
    private String statusYear;

    @NotBlank(message = "Program System Code is required.")
    @Size(max = 20, message = "Program System Code can not exceed {max} chars; found '${validatedValue}'.")
    private String programSystemCode;

    @NotBlank(message = "Street Address is required.")
    @Size(max = 100, message = "Street Address can not exceed {max} chars; found '${validatedValue}'.")
    private String streetAddress;

    @NotBlank(message = "City is required.")
    @Size(max = 60, message = "City can not exceed {max} chars; found '${validatedValue}'.")
    private String city;

    @NotBlank(message = "County is required.")
    @Size(max = 43, message = "County can not exceed {max} chars; found '${validatedValue}'.")
    private String county;

    @Size(max = 3, message = "County Code can not exceed {max} chars; found '${validatedValue}'.")
    private String countyCode;

    @NotBlank(message = "State Code is required.")
    @Size(max = 5, message = "State Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stateCode;

    @Size(max = 2, message = "State FIPS Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stateFipsCode;

    @Size(max = 10, message = "Country Code can not exceed {max} chars; found '${validatedValue}'.")
    private String countryCode;

    @NotBlank(message = "Postal Code is required.")
    @Size(max = 10, message = "Postal Code can not exceed {max} chars; found '${validatedValue}'.")
    private String postalCode;

    @NotBlank(message = "Latitude is required.")
    @Pattern(regexp = LatitudePattern,
        message = "Latitude is not in expected numeric format: '+/-{2}.{6}' digits; found '${validatedValue}'.")
    private String latitude;

    @NotBlank(message = "Longitude is required.")
    @Pattern(regexp = LongitudePattern,
        message = "Longitude is not in expected numeric format: '+/-{3}.{6}' digits; found '${validatedValue}.")
    private String longitude;

    @NotBlank(message = "Mailing Street Address is required.")
    @Size(max = 100, message = "Mailing Street Address can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingStreetAddress;

    @NotBlank(message = "Mailing City is required.")
    @Size(max = 60, message = "Mailing City can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingCity;

    @NotBlank(message = "Mailing State Code is required.")
    @Size(max = 5, message = "Mailing State Code can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingStateCode;

    @NotBlank(message = "Mailing Postal Code is required.")
    @Size(max = 10, message = "Mailing Postal Code can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingPostalCode;

    @Size(max = 10, message = "Mailing Country Code can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingCountryCode;

    @Size(max = 22, message = "EIS Program ID can not exceed {max} chars; found '${validatedValue}'.")
    private String eisProgramId;

    @Size(max = 20, message = "Tribal Code can not exceed {max} chars; found '${validatedValue}'.")
    private String tribalCode;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    public FacilitySiteBulkUploadDto() {

        super(WorksheetName.FacilitySite);
    }

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

    public String getStatusYear() {
        return statusYear;
    }
    public void setStatusYear(String statusYear) {
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

    public String getCountyCode() {
        return countyCode;
    }
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getStateCode() {
        return stateCode;
    }
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateFipsCode() {
        return stateFipsCode;
    }
    public void setStateFipsCode(String stateFipsCode) {
        this.stateFipsCode = stateFipsCode;
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

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
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

    public String getComments() {
    	return comments;
    }

    public void setComments(String comments) {
    	this.comments = comments;
    }
}
