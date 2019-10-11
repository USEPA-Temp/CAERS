package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;

@Component
public class ReportingPeriodValidator extends BaseValidator<ReportingPeriod> {

    @Override
    public void compose(FluentValidator validator,
                        ValidatorContext validatorContext,
                        ReportingPeriod reportingPeriod) {

        ValidationRegistry registry = getCefValidatorContext(validatorContext).getValidationRegistry();

        // add more validators as needed
        validator.onEach(reportingPeriod.getEmissions(),
            registry.findOneByType(EmissionValidator.class));
    }

    @Override
    public boolean validate(ValidatorContext validatorContext, ReportingPeriod period) {

        boolean valid = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        if (period.getCalculationParameterValue() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(
                "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationParameterValue",
                "reportingPeriod.calculationParameterValue.required");

        } else if (period.getCalculationParameterValue().intValue() < 0) {

            valid = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationParameterValue",
                    "reportingPeriod.calculationParameterValue.min");
        }

        if (period.getCalculationMaterialCode() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationMaterialCode",
                    "reportingPeriod.calculationMaterialCode.required");
        }

        if (period.getCalculationParameterTypeCode() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationParameterTypeCode",
                    "reportingPeriod.calculationParameterTypeCode.required");
        }

        if (period.getCalculationParameterUom() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationParameterUom",
                    "reportingPeriod.calculationParameterUom.required");
        }

        return valid;
    }
}
