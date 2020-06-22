package gov.epa.cef.web.service.validation.validator.federal;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Strings;
import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class FacilitySiteValidator extends BaseValidator<FacilitySite> {

    private static final String STATUS_OPERATING = "OP";
    private static final String STATUS_TEMPORARILY_SHUTDOWN = "TS";
    private static final String STATUS_PERMANENTLY_SHUTDOWN = "PS";
    private static final String LANDFILL_SOURCE_CODE = "104";

    @Override
    public void compose(FluentValidator validator,
                        ValidatorContext validatorContext,
                        FacilitySite facilitySite) {

        ValidationRegistry registry = getCefValidatorContext(validatorContext).getValidationRegistry();

        // add more validators as needed
        validator.onEach(facilitySite.getEmissionsUnits(),
            registry.findOneByType(EmissionsUnitValidator.class));

        validator.onEach(facilitySite.getReleasePoints(),
                registry.findOneByType(ReleasePointValidator.class));

        validator.onEach(facilitySite.getControls(),
        		registry.findOneByType(ControlValidator.class));

        validator.onEach(facilitySite.getControlPaths(),
        		registry.findOneByType(ControlPathValidator.class));
    }

    @Override
    public boolean validate(ValidatorContext validatorContext, FacilitySite facilitySite) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        if (Strings.emptyToNull(facilitySite.getEisProgramId()) == null) {
            // prevented by db constraints
            result = false;
            context.addFederalError(
                    ValidationField.FACILITY_EIS_ID.value(), "facilitysite.eisProgramId.required");
        }

        // If facility operation status is not operating, status year is required
        if (!STATUS_OPERATING.contentEquals(facilitySite.getOperatingStatusCode().getCode()) && facilitySite.getStatusYear() == null) {

        	result = false;
        	context.addFederalError(
        			ValidationField.FACILITY_STATUS.value(), "facilitysite.status.required",
        			createValidationDetails(facilitySite));
        }

        // Status year must be between 1900 and 2050
        if (facilitySite.getStatusYear() != null && (facilitySite.getStatusYear() < 1900 || facilitySite.getStatusYear() > 2050)) {

        	result = false;
        	context.addFederalError(
        			ValidationField.FACILITY_STATUS.value(), "facilitysite.status.range",
        			createValidationDetails(facilitySite));
        }

        if (facilitySite.getCountyCode() == null) {

            result = false;
            context.addFederalError(
                    ValidationField.FACILITY_COUNTY.value(), "facilitySite.county.required",
                    createValidationDetails(facilitySite));

        } else if (!facilitySite.getCountyCode().getFipsStateCode().getUspsCode().equals(facilitySite.getStateCode().getUspsCode())) {

            result = false;
            context.addFederalError(
                    ValidationField.FACILITY_COUNTY.value(), "facilitySite.county.invalidState",
                    createValidationDetails(facilitySite),
                    facilitySite.getCountyCode().getName(),
                    facilitySite.getStateCode().getUspsCode());
        }
        
        if (facilitySite.getCountyCode() != null && facilitySite.getCountyCode().getLastInventoryYear() != null
            && facilitySite.getCountyCode().getLastInventoryYear() < facilitySite.getEmissionsReport().getYear()) {

            result = false;
            context.addFederalError(
                    ValidationField.FACILITY_COUNTY.value(), "facilitysite.county.legacy", 
                    createValidationDetails(facilitySite),
                    facilitySite.getCountyCode().getName());

        }
        
        // Phone number must be entered as 10 digits
        String regex = "^[0-9]{10}";
        Pattern pattern = Pattern.compile(regex);
    	for(FacilitySiteContact fc: facilitySite.getContacts()){
        	if(!StringUtils.isEmpty(fc.getPhone())){
            	Matcher matcher = pattern.matcher(fc.getPhone());
            	if(!matcher.matches()){
                	result = false;
                	context.addFederalError(
                			ValidationField.FACILITY_CONTACT_PHONE.value(),
                			"facilitySite.contacts.phoneNumber.requiredFormat",
                			createContactValidationDetails(facilitySite));
            	}
        	} else {
            	result = false;
            	context.addFederalError(
            			ValidationField.FACILITY_CONTACT_PHONE.value(),
            			"facilitySite.contacts.phoneNumber.requiredFormat",
            			createContactValidationDetails(facilitySite));
        	}
    	}
    	
        // Postal codes must be entered as 5 digits (XXXXX) or 9 digits (XXXXX-XXXX).
    	regex = "^[0-9]{5}(?:-[0-9]{4})?$";
    	pattern = Pattern.compile(regex);
    	for(FacilitySiteContact fc: facilitySite.getContacts()){
        	if(!StringUtils.isEmpty(fc.getPostalCode())){
            	Matcher matcher = pattern.matcher(fc.getPostalCode());
            	if(!matcher.matches()){
                	result = false;
                	context.addFederalError(
                			ValidationField.FACILITY_CONTACT_POSTAL.value(),
                			"facilitySite.contacts.postalCode.requiredFormat",
                			createContactValidationDetails(facilitySite));
            	}
        	}
        	if(!StringUtils.isEmpty(fc.getMailingPostalCode())){
            	Matcher matcher = pattern.matcher(fc.getMailingPostalCode());
            	if(!matcher.matches()){
                	result = false;
                	context.addFederalError(
                			ValidationField.FACILITY_CONTACT_POSTAL.value(),
                			"facilitySite.contacts.postalCode.requiredFormat",
                			createContactValidationDetails(facilitySite));
            	}
        	}
    	}

    	if(!StringUtils.isEmpty(facilitySite.getPostalCode())) {
        	Matcher matcher = pattern.matcher(facilitySite.getPostalCode());
        	if(!matcher.matches()){
            	result = false;
            	context.addFederalError(
            			ValidationField.FACILITY_CONTACT_POSTAL.value(),
            			"facilitysite.postalCode.requiredFormat",
            			createValidationDetails(facilitySite));
        	}
    	}
    	if(!StringUtils.isEmpty(facilitySite.getMailingPostalCode())){
        	Matcher matcher = pattern.matcher(facilitySite.getMailingPostalCode());
        	if(!matcher.matches()){
            	result = false;
            	context.addFederalError(
            			ValidationField.FACILITY_CONTACT_POSTAL.value(),
            			"facilitysite.postalCode.requiredFormat",
            			createValidationDetails(facilitySite));
        	}
    	}

    	// Email address must be in a valid format.
    	pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[\\.][A-Za-z]{2,}$");
    	for(FacilitySiteContact fc: facilitySite.getContacts()){
        	if(!StringUtils.isEmpty(fc.getEmail())){
            	Matcher matcher = pattern.matcher(fc.getEmail());
            	if(!pattern.matcher(fc.getEmail()).matches()){
                	result = false;
                	context.addFederalError(
                			ValidationField.FACILITY_EMAIL_ADDRESS.value(),
                			"facilitySite.contacts.emailAddress.requiredFormat",
                			createContactValidationDetails(facilitySite));
            	}
        	}
    	}

        // Facility must have a facility NAICS code reported
        List<FacilityNAICSXref> fsNAICSList = facilitySite.getFacilityNAICS();

        if (CollectionUtils.isEmpty(fsNAICSList)) {

        	result = false;
        	context.addFederalError(ValidationField.FACILITY_NAICS.value(), "facilitysite.naics.required",
        			createValidationDetails(facilitySite));
        }

        for (FacilityNAICSXref naics : fsNAICSList) {

            if (naics.getNaicsCode() != null && naics.getNaicsCode().getLastInventoryYear() != null
                    && naics.getNaicsCode().getLastInventoryYear() < facilitySite.getEmissionsReport().getYear()) {

                result = false;

                if (Strings.emptyToNull(naics.getNaicsCode().getMapTo()) != null) {

                    context.addFederalError(
                            ValidationField.FACILITY_NAICS.value(),
                            "facilitysite.naics.legacy.map",
                            createValidationDetails(facilitySite),
                            naics.getNaicsCode().getCode().toString(),
                            naics.getNaicsCode().getMapTo());
                } else {

                    context.addFederalError(
                            ValidationField.FACILITY_NAICS.value(),
                            "facilitysite.naics.legacy",
                            createValidationDetails(facilitySite),
                            naics.getNaicsCode().getCode().toString());
                }

            }
        }

        // Facility NAICS must have one and only one primary assigned
        fsNAICSList = facilitySite.getFacilityNAICS().stream()
            .filter(fn -> fn.isPrimaryFlag() == true)
            .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(fsNAICSList) || fsNAICSList.size() != 1) {
        	result = false;
        	context.addFederalError(ValidationField.FACILITY_NAICS.value(), "facilitysite.naics.primary.required",
        			createValidationDetails(facilitySite));
        }


        // Facility must have an Emissions Inventory Contact
        List<FacilitySiteContact> contactList = facilitySite.getContacts().stream()
        .filter(fc -> fc.getType().getCode().equals("EI"))
        .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(contactList)) {

        	result = false;
        	context.addFederalError(ValidationField.FACILITY_CONTACT.value(), "facilitysite.contacts.required",
        			createContactValidationDetails(facilitySite));
        }


        for (FacilitySiteContact contact: facilitySite.getContacts()) {
            // Facility contact must have an email
        	if (Strings.emptyToNull(contact.getEmail()) == null) {

        		result = false;
        		context.addFederalError(ValidationField.FACILITY_CONTACT.value(), "facilitysite.contacts.email.required",
        				createContactValidationDetails(facilitySite));
        	}

        	// Facility contact county must be for selected state
        	if (contact.getCountyCode() != null && !contact.getCountyCode().getFipsStateCode().getUspsCode().equals(contact.getStateCode().getUspsCode())) {

                result = false;
                context.addFederalError(
                        ValidationField.FACILITY_CONTACT_COUNTY.value(), "facilitySite.contacts.county.invalidState",
                        createContactValidationDetails(facilitySite),
                        contact.getCountyCode().getName(),
                        contact.getStateCode().getUspsCode());
            }
        	
        	if (contact.getCountyCode() != null && contact.getCountyCode().getLastInventoryYear() != null
                && contact.getCountyCode().getLastInventoryYear() < contact.getFacilitySite().getEmissionsReport().getYear()) {

                result = false;
                context.addFederalError(
                        ValidationField.FACILITY_CONTACT_COUNTY.value(), "facilitysite.contacts.county.legacy", 
                        createContactValidationDetails(facilitySite),
                        contact.getCountyCode().getName());

            }
        }
        
        if (STATUS_TEMPORARILY_SHUTDOWN.contentEquals(facilitySite.getOperatingStatusCode().getCode())) {
        	List<EmissionsUnit> euList = facilitySite.getEmissionsUnits().stream()
        			.filter(emissionUnit -> !STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionUnit.getOperatingStatusCode().getCode())
        					&& !STATUS_TEMPORARILY_SHUTDOWN.contentEquals(emissionUnit.getOperatingStatusCode().getCode()))
        			.collect(Collectors.toList());

        	for (EmissionsUnit eu: euList) {
        		result = false;
        		context.addFederalError(
        				ValidationField.EMISSIONS_UNIT_STATUS_CODE.value(),
        				"emissionsUnit.statusTypeCode.temporarilyShutdown",
        				createEmissionsUnitValidationDetails(eu));
        	}

        	List<ReleasePoint> rpList = facilitySite.getReleasePoints().stream()
        			.filter(releasePoint -> !STATUS_PERMANENTLY_SHUTDOWN.contentEquals(releasePoint.getOperatingStatusCode().getCode())
        					&& !STATUS_TEMPORARILY_SHUTDOWN.contentEquals(releasePoint.getOperatingStatusCode().getCode()))
        			.collect(Collectors.toList());

        	for (ReleasePoint rp: rpList) {
        		result = false;
        		context.addFederalError(
        				ValidationField.RP_STATUS_CODE.value(),
        				"releasePoint.statusTypeCode.temporarilyShutdown",
        				createReleasePointValidationDetails(rp));
        	}

        	List<Control> cList = facilitySite.getControls().stream()
        			.filter(control -> !STATUS_PERMANENTLY_SHUTDOWN.contentEquals(control.getOperatingStatusCode().getCode())
        					&& !STATUS_TEMPORARILY_SHUTDOWN.contentEquals(control.getOperatingStatusCode().getCode()))
        			.collect(Collectors.toList());

        	for (Control c: cList) {
        		result = false;
        		context.addFederalError(
        				ValidationField.CONTROL_STATUS_CODE.value(),
        				"control.statusTypeCode.temporarilyShutdown",
        				createControlValidationDetails(c));
        	}
        } else if (STATUS_PERMANENTLY_SHUTDOWN.contentEquals(facilitySite.getOperatingStatusCode().getCode())) {
        	List<EmissionsUnit> euList = facilitySite.getEmissionsUnits().stream()
        			.filter(emissionUnit -> !STATUS_PERMANENTLY_SHUTDOWN.contentEquals(emissionUnit.getOperatingStatusCode().getCode()))
        			.collect(Collectors.toList());

        	for (EmissionsUnit eu: euList) {
        		result = false;
        		context.addFederalError(
        				ValidationField.EMISSIONS_UNIT_STATUS_CODE.value(),
        				"emissionsUnit.statusTypeCode.permanentShutdown",
        				createEmissionsUnitValidationDetails(eu));
        	}

        	List<ReleasePoint> rpList = facilitySite.getReleasePoints().stream()
        			.filter(releasePoint -> !STATUS_PERMANENTLY_SHUTDOWN.contentEquals(releasePoint.getOperatingStatusCode().getCode()))
        			.collect(Collectors.toList());

        	for (ReleasePoint rp: rpList) {
        		result = false;
        		context.addFederalError(
        				ValidationField.RP_STATUS_CODE.value(),
        				"releasePoint.statusTypeCode.permanentShutdown",
        				createReleasePointValidationDetails(rp));
        	}

        	List<Control> cList = facilitySite.getControls().stream()
        			.filter(control -> !STATUS_PERMANENTLY_SHUTDOWN.contentEquals(control.getOperatingStatusCode().getCode()))
        			.collect(Collectors.toList());

        	for (Control c: cList) {
        		result = false;
        		context.addFederalError(
        				ValidationField.CONTROL_STATUS_CODE.value(),
        				"control.statusTypeCode.permanentShutdown",
        				createControlValidationDetails(c));
        	}
        }

        if (facilitySite.getStatusYear() != null && facilitySite.getFacilitySourceTypeCode() != null) {

	        // warning total emissions will not be accepted if facility site operation status is not OP,
	        // except when source type is landfill or status year is greater than inventory cycle year.
	        if ((!LANDFILL_SOURCE_CODE.contentEquals(facilitySite.getFacilitySourceTypeCode().getCode()))
	        	&& facilitySite.getStatusYear() <= facilitySite.getEmissionsReport().getYear()
	        	&& (!STATUS_OPERATING.contentEquals(facilitySite.getOperatingStatusCode().getCode()))) {

		        	result = false;
		        	context.addFederalWarning(
		        			ValidationField.FACILITY_EMISSION_REPORTED.value(),
		        			"facilitysite.reportedEmissions.invalidWarning",
		        			createValidationDetails(facilitySite));
	      	}
        }

        return result;
    }

    private ValidationDetailDto createValidationDetails(FacilitySite source) {

    	String description = MessageFormat.format("Facility Site ", source.getEisProgramId());

    	ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getEisProgramId(), EntityType.FACILITY_SITE, description);
    	return dto;
    }

    private ValidationDetailDto createContactValidationDetails(FacilitySite source) {

    	String description = MessageFormat.format("Facility Contact ", source.getEisProgramId());

    	ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getEisProgramId(), EntityType.FACILITY_SITE, description);
    	return dto;
    }

    private ValidationDetailDto createEmissionsUnitValidationDetails(EmissionsUnit source) {

      String description = MessageFormat.format("Emissions Unit: {0}", source.getUnitIdentifier());

      ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getUnitIdentifier(), EntityType.EMISSIONS_UNIT, description);
      return dto;
    }

    private ValidationDetailDto createReleasePointValidationDetails(ReleasePoint source) {

      String description = MessageFormat.format("Release Point: {0}", source.getReleasePointIdentifier());

      ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getReleasePointIdentifier(), EntityType.RELEASE_POINT, description);
      return dto;
    }

    private ValidationDetailDto createControlValidationDetails(Control source) {

      String description = MessageFormat.format("Control: {0}", source.getIdentifier());

      ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getIdentifier(), EntityType.CONTROL, description);
      return dto;
    }

}
