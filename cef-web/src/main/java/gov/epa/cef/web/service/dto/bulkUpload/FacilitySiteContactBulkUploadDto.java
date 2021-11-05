/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import gov.epa.cef.web.annotation.CsvColumn;
import gov.epa.cef.web.annotation.CsvFileName;

import java.io.Serializable;

@CsvFileName(name = "facility_site_contacts.csv")
public class FacilitySiteContactBulkUploadDto extends BaseWorksheetDto implements Serializable {

	private static final long serialVersionUID = 1L;

    @NotNull(message = "Facility Site Contact ID is required.")
	private Long id;

    @NotNull(message = "Facility Site ID is required.")
	private Long facilitySiteId;

    @NotBlank(message = "Facility Site Contact Type is required.")
    @Size(max = 150, message = "Facility Site Contact Type can not exceed {max} chars; found '${validatedValue}'.")
	private String type;
    
    private String contactTypeDescription;

    @Size(max = 15, message = "Prefix can not exceed {max} chars; found '${validatedValue}'.")
	private String prefix;

    @NotBlank(message = "First Name is required.")
    @Size(max = 20, message = "First Name can not exceed {max} chars; found '${validatedValue}'.")
	private String firstName;

    @NotBlank(message = "Last Name is required.")
    @Size(max = 20, message = "Last Name can not exceed {max} chars; found '${validatedValue}'.")
	private String lastName;

    @NotBlank(message = "Email is required.")
    @Size(max = 255, message = "Email can not exceed {max} chars; found '${validatedValue}'.")
	private String email;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = PhonePattern,
    message = "Phone number is not in expected numeric format: '[0-9]{10}' digits; found '${validatedValue}.")
    @Size(max = 10, message = "Phone can not exceed {max} chars; found '${validatedValue}'.")
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

    @CsvColumn(name = "ID", order = 1)
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @CsvColumn(name = "Facility Site ID", order = 2)
	public Long getFacilitySiteId() {
		return facilitySiteId;
	}

	public void setFacilitySiteId(Long facilitySiteId) {
		this.facilitySiteId = facilitySiteId;
	}

    @CsvColumn(name = "Type", order = 3)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    @CsvColumn(name = "Contact Type Description", order = 3)
	public String getContactTypeDescription() {
		return contactTypeDescription;
	}
	public void setContactTypeDescription(String contactTypeDescription) {
		this.contactTypeDescription = contactTypeDescription;
	}    
	
    @CsvColumn(name = "Prefix", order = 4)
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

    @CsvColumn(name = "First Name", order = 5)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    @CsvColumn(name = "Last Name", order = 6)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    @CsvColumn(name = "Email", order = 7)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @CsvColumn(name = "Phone", order = 8)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    @CsvColumn(name = "Phone Extension", order = 9)
	public String getPhoneExt() {
		return phoneExt;
	}

	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}

    @CsvColumn(name = "Street Address", order = 10)
	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

    @CsvColumn(name = "City", order = 11)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

    @CsvColumn(name = "County", order = 15)
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

    @CsvColumn(name = "County Code", order = 14)
	public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    @CsvColumn(name = "State Code", order = 13)
    public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

    @CsvColumn(name = "State FIPS Code", order = 12)
	public String getStateFipsCode() {
        return stateFipsCode;
    }

    public void setStateFipsCode(String stateFipsCode) {
        this.stateFipsCode = stateFipsCode;
    }

    @CsvColumn(name = "Country Code", order = 16)
    public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

    @CsvColumn(name = "Postal Code", order = 17)
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

    @CsvColumn(name = "Mailing Street Address", order = 18)
	public String getMailingStreetAddress() {
		return mailingStreetAddress;
	}

	public void setMailingStreetAddress(String mailingStreetAddress) {
		this.mailingStreetAddress = mailingStreetAddress;
	}

    @CsvColumn(name = "Mailing City", order = 19)
	public String getMailingCity() {
		return mailingCity;
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

    @CsvColumn(name = "Mailing State Code", order = 20)
	public String getMailingStateCode() {
		return mailingStateCode;
	}

	public void setMailingStateCode(String mailingStateCode) {
		this.mailingStateCode = mailingStateCode;
	}

    @CsvColumn(name = "Mailing Postal Code", order = 22)
	public String getMailingPostalCode() {
		return mailingPostalCode;
	}

	public void setMailingPostalCode(String mailingPostalCode) {
		this.mailingPostalCode = mailingPostalCode;
	}

    @CsvColumn(name = "Mailing Country Code", order = 21)
	public String getMailingCountryCode() {
		return mailingCountryCode;
	}

	public void setMailingCountryCode(String mailingCountryCode) {
		this.mailingCountryCode = mailingCountryCode;
	}

}
