package gov.epa.cef.web.service.validation.validator.federal;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Strings;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FacilitySiteValidator extends BaseValidator<FacilitySite> {

    @Autowired
    private EmissionRepository emissionRepo;
    
    @Autowired
    private EmissionsReportRepository reportRepo;
    
    private static final String STATUS_OPERATING = "OP";
    
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
        
        // Postal codes must be entered as 5 digits (XXXXX) or 9 digits (XXXXX-XXXX).
        if(facilitySite.getContacts() != null){
        	for(FacilitySiteContact fc: facilitySite.getContacts()){
            	String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
            	Pattern pattern = Pattern.compile(regex);
            	if(fc.getPostalCode() != null){
                	Matcher matcher = pattern.matcher(fc.getPostalCode());
                	if(!matcher.matches()){
                    	result = false; 
                    	context.addFederalError(
                    			ValidationField.FACILITY_CONTACT_POSTAL.value(), 
                    			"facilitySite.contacts.postalCode.requiredFormat",
                    			createContactValidationDetails(facilitySite));
                	}	
            	}
            	if(fc.getMailingPostalCode() != null){
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
        }
        if(facilitySite != null){
        	String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        	Pattern pattern = Pattern.compile(regex);
        	if(facilitySite.getPostalCode() != null) {
	        	Matcher matcher = pattern.matcher(facilitySite.getPostalCode());
	        	if(!matcher.matches()){
	            	result = false; 
	            	context.addFederalError(
	            			ValidationField.FACILITY_CONTACT_POSTAL.value(), 
	            			"facilitysite.postalCode.requiredFormat",
	            			createValidationDetails(facilitySite));
	        	}
        	}
        	if(facilitySite.getMailingPostalCode() != null){
            	Matcher matcher = pattern.matcher(facilitySite.getMailingPostalCode());
            	if(!matcher.matches()){
                	result = false; 
                	context.addFederalError(
                			ValidationField.FACILITY_CONTACT_POSTAL.value(), 
                			"facilitysite.postalCode.requiredFormat",
                			createValidationDetails(facilitySite));
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
        
        // Facility contact must have an email
        for (FacilitySiteContact contact: facilitySite.getContacts()) {
        	if (Strings.emptyToNull(contact.getEmail()) == null) {
        		
        		result = false;
        		context.addFederalError(ValidationField.FACILITY_CONTACT.value(), "facilitysite.contacts.email.required",
        				createContactValidationDetails(facilitySite));
        	}
        }
        
        try {
	        List<Emission> currentEmissionsList = emissionRepo.findAllByReportId(facilitySite.getEmissionsReport().getId());
	        
	        if (currentEmissionsList != null) {
	          // check current year's total emissions against previous year's total emissions warning
	          EmissionsReport currentReport = facilitySite.getEmissionsReport();
	          // find previous report
	          if (currentReport != null) {
		          List<EmissionsReport> erList = reportRepo.findByEisProgramId(currentReport.getEisProgramId()).stream()
		              .filter(var -> (var.getYear() != null && var.getYear() < currentReport.getYear()))
		              .sorted(Comparator.comparing(EmissionsReport::getYear))
		              .collect(Collectors.toList());
	
		          if (!erList.isEmpty()) {
		          	Short previousReportYr = erList.get(erList.size()-1).getYear();
		          	Long previousReportId = erList.get(erList.size()-1).getId();
		          	
	          		List<Emission> previousEmissionsList = emissionRepo.findAllByReportId(previousReportId);
	          		
	          		for (Emission ce: currentEmissionsList) {
	          			for (Emission pe: previousEmissionsList) {
	          				if (pe.getReportingPeriod().getEmissionsProcess().getEmissionsProcessIdentifier()
	          						.contentEquals(ce.getReportingPeriod().getEmissionsProcess().getEmissionsProcessIdentifier())) {
	          					
	          					// check if total emissions are equal in value and scale 
	          					if (pe.getTotalEmissions().equals(ce.getTotalEmissions())) {
	          						result = false;
	          						context.addFederalWarning(
	          								ValidationField.EMISSION_TOTAL_EMISSIONS.value(),
	          								"emission.totalEmissions.copied",
	          								createValidationDetails(ce),
	          								previousReportYr.toString());
				          		}
	          				}
	          			}
	          		}
	          	}
	          }
	        }
        } catch (NullPointerException e) {
        	System.out.println("No Emissions found for Emissions Report");
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
    
    private String getEmissionsUnitIdentifier(Emission emission) {
      if (emission.getReportingPeriod() != null && emission.getReportingPeriod().getEmissionsProcess() != null 
              && emission.getReportingPeriod().getEmissionsProcess().getEmissionsUnit() != null) {
          return emission.getReportingPeriod().getEmissionsProcess().getEmissionsUnit().getUnitIdentifier();
      }
      return null;
	}
    
    private String getEmissionsProcessIdentifier(Emission emission) {
  	if (emission.getReportingPeriod() != null && emission.getReportingPeriod().getEmissionsProcess() != null) {
  		return emission.getReportingPeriod().getEmissionsProcess().getEmissionsProcessIdentifier();
  	}
  	return null;
    }
    
    private String getPollutantName(Emission emission) {
  	if (emission.getPollutant() != null) {
  		return emission.getPollutant().getPollutantName();
  	}
  	return null;
	}

    private ValidationDetailDto createValidationDetails(Emission source) {

      String description = MessageFormat.format("Emission Unit: {0}, Emission Process: {1}, Pollutant: {2}", 
              getEmissionsUnitIdentifier(source),
              getEmissionsProcessIdentifier(source),
              getPollutantName(source));

      ValidationDetailDto dto = new ValidationDetailDto(source.getId(), getPollutantName(source), EntityType.EMISSION, description);
      if (source.getReportingPeriod() != null) {
          dto.getParents().add(new ValidationDetailDto(source.getReportingPeriod().getId(), null, EntityType.REPORTING_PERIOD));
      }
      return dto;
  }
    
}
