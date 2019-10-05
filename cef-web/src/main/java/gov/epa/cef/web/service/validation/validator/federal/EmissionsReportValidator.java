package gov.epa.cef.web.service.validation.validator.federal;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Strings;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import org.springframework.stereotype.Component;

@Component
public class EmissionsReportValidator extends BaseValidator<EmissionsReport> {

    @Override
    public boolean validate(ValidatorContext validatorContext, EmissionsReport report) {

        boolean valid = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        if (report.getYear() == null) {

            valid = false;
            context.addFederalError("report.year", "report.year.required");

        } else if (report.getYear().intValue() < 2019) {

            context.addFederalWarning("report.year", "report.year.min");
        }

        if (Strings.emptyToNull(report.getAgencyCode()) == null) {

            valid = false;
            context.addFederalError("report.agencyCode", "report.agencyCode.required");
        }

        if (Strings.emptyToNull(report.getFrsFacilityId()) == null) {

            valid = false;
            context.addFederalError("report.frsFacilityId", "report.frsFacilityId.required");
        }

        if (Strings.emptyToNull(report.getEisProgramId()) == null) {

            valid = false;
            context.addFederalError("report.eisProgramId", "report.eisProgramId.required");
        }

        return valid;
    }
}
