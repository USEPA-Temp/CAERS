package gov.epa.cef.web.service.validation.validator.federal;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Strings;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.ReportAttachmentRepository;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import gov.epa.cef.web.service.validation.validator.IEmissionsReportValidator;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmissionsReportValidator
    extends BaseValidator<EmissionsReport>
    implements IEmissionsReportValidator {
	
	@Autowired
	private EmissionRepository emissionRepo;
	
	@Autowired
	private ReportAttachmentRepository attachmentRepo;

    @Override
    public void compose(FluentValidator validator,
                        ValidatorContext validatorContext,
                        EmissionsReport emissionsReport) {

        ValidationRegistry registry = getCefValidatorContext(validatorContext).getValidationRegistry();

        // add more validators as needed
        validator.onEach(emissionsReport.getFacilitySites(),
            registry.findOneByType(FacilitySiteValidator.class));
    }

    @Override
    public boolean validate(ValidatorContext validatorContext, EmissionsReport report) {

        boolean valid = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        if (report.getYear() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(ValidationField.REPORT_YEAR.value(), "report.year.required");

        } else if (report.getYear().intValue() < Calendar.getInstance().get(Calendar.YEAR) - 1) {

            valid = false;
            context.addFederalWarning(ValidationField.REPORT_YEAR.value(), "report.year.min", "" + (Calendar.getInstance().get(Calendar.YEAR) - 1));
        }

        if (Strings.emptyToNull(report.getAgencyCode()) == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(ValidationField.REPORT_AGENCY_CODE.value(), "report.agencyCode.required");
        }

        if (Strings.emptyToNull(report.getFrsFacilityId()) == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(ValidationField.REPORT_FRS_ID.value(), "report.frsFacilityId.required");
        }

        if (Strings.emptyToNull(report.getEisProgramId()) == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(ValidationField.REPORT_EIS_ID.value(), "report.eisProgramId.required");
        }
        
	        List<Emission> emissionsList = emissionRepo.findAllByReportId(report.getId()).stream()
	        		.filter(e -> (e.getEmissionsCalcMethodCode().getTotalDirectEntry() == true || e.getTotalManualEntry() == true))
					.collect(Collectors.toList());
	        
	        List<ReportAttachment> attachmentList = attachmentRepo.findAllByReportId(report.getId());
	        
	        if (emissionsList.size() > 0 && attachmentList.isEmpty()) {
	
	            valid = false;
	            context.addFederalError(
	            		ValidationField.REPORT_ATTACHMENT.value(),
	            		"report.reportAttachment.required",
	            		createValidationDetails(report));
	        }
        
        return valid;
    }
    
    private ValidationDetailDto createValidationDetails(EmissionsReport source) {
    	
    	String description = MessageFormat.format("Attachments ", source.getId());
    	
    	ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getId().toString(), EntityType.REPORT_ATTACHMENT, description);
    	return dto;
    }
}
