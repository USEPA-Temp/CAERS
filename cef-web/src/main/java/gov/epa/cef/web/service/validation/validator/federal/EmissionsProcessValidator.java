package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;

@Component
public class EmissionsProcessValidator extends BaseValidator<EmissionsProcess> {

    @Override
    public void compose(FluentValidator validator,
                        ValidatorContext validatorContext,
                        EmissionsProcess emissionsProcess) {

        ValidationRegistry registry = getCefValidatorContext(validatorContext).getValidationRegistry();

        // add more validators as needed
        validator.onEach(emissionsProcess.getReportingPeriods(),
            registry.findOneByType(ReportingPeriodValidator.class));
    }

    @Override
    public boolean validate(ValidatorContext validatorContext, EmissionsProcess emissionsProcess) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        Double totalReleasePointPercent = emissionsProcess.getReleasePointAppts().stream().mapToDouble(ReleasePointAppt::getPercent).sum();
        // Might need to add a rounding tolerance.
        if (100 != totalReleasePointPercent) {

            result = false;
            context.addFederalError(
                    ValidationField.PROCESS_RP_PCT.value(),
                    "emissionsProcess.releasePointAppts.percent.total",
                    createValidationDetails(emissionsProcess));
        }


        return result;
    }

    private String getEmissionsUnitIdentifier(EmissionsProcess process) {
        if (process.getEmissionsUnit() != null) {
            return process.getEmissionsUnit().getUnitIdentifier();
        }
        return null;
    }

    private ValidationDetailDto createValidationDetails(EmissionsProcess source) {

        String description = MessageFormat.format("Emission Unit: {0}, Emission Process: {1}", 
                getEmissionsUnitIdentifier(source),
                source.getEmissionsProcessIdentifier());

        ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getEmissionsProcessIdentifier(), EntityType.EMISSIONS_PROCESS, description);
        return dto;
    }

}
