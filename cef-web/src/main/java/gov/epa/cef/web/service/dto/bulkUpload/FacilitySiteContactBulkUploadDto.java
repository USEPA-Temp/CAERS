package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class FacilitySiteContactBulkUploadDto extends BaseWorksheetDto implements Serializable {

	private static final long serialVersionUID = 1L;

    @NotNull(message = "Facility Site Contact ID is required.")
	private Long id;

    @NotNull(message = "Facility Site ID is required.")
	private Long facilitySiteId;

    @NotBlank(message = "Facility Site Contact Type is required.")
    @Size(max = 150, message = "Facility Site Contact Type can not exceed {max} chars; found '${validatedValue}'.")
	private String type;

    @Size(max = 15, message = "Prefix can not exceed {max} chars; found '${validatedValue}'.")
	private String prefix;

    @Size(max = 20, message = "First Name can not exceed {max} chars; found '${validatedValue}'.")
	private String firstName;

    @Size(max = 20, message = "Last Name can not exceed {max} chars; found '${validatedValue}'.")
	private String lastName;

    @NotBlank(message = "Email is required.")
    @Size(max = 255, message = "Email can not exceed {max} chars; found '${validatedValue}'.")
	private String email;

    @Size(max = 15, message = "Phone can not exceed {max} chars; found '${validatedValue}'.")
	private String phone;

    @Size(max = 5, message = "Phone Ext can not exceed {max} chars; found '${validatedValue}'.")
	private String phoneExt;

    @NotBlank(message = "Street Address is required.")
    @Size(max = 100, message = "Street Address can not exceed {max} chars; found '${validatedValue}'.")
	private String streetAddress;

    @NotBlank(message = "City is required.")
    @Size(max = 60, message = "City can not exceed {max} chars; found '${validatedValue}'.")
	private String city;

    @Size(max = 43, message = "County can not exceed {max} chars; found '${validatedValue}'.")
	private String county;

    @NotBlank(message = "State Code is required.")
    @Size(max = 5, message = "State Code can not exceed {max} chars; found '${validatedValue}'.")
	private String stateCode;

    @Size(max = 10, message = "Country Code can not exceed {max} chars; found '${validatedValue}'.")
	private String countryCode;

    @Size(max = 10, message = "Postal Code can not exceed {max} chars; found '${validatedValue}'.")
	private String postalCode;

    @Size(max = 100, message = "Mailing Street Address can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingStreetAddress;

    @Size(max = 60, message = "Mailing City can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingCity;

    @Size(max = 5, message = "Mailing State Code can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingStateCode;

    @Size(max = 10, message = "Mailing Postal Code can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingPostalCode;

    @Size(max = 10, message = "Mailing Country Code can not exceed {max} chars; found '${validatedValue}'.")
    private String mailingCountryCode;

    public FacilitySiteContactBulkUploadDto() {

        super(WorksheetName.FacilitySiteContact);
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFacilitySiteId() {
		return facilitySiteId;
	}

	public void setFacilitySiteId(Long facilitySiteId) {
		this.facilitySiteId = facilitySiteId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneExt() {
		return phoneExt;
	}

	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
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
