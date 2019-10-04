package gov.epa.cef.web.service.validation.validator;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Strings;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.validation.ValidationFeature;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

public class EmissionsReportValidator extends BaseValidator<EmissionsReport> {

    public EmissionsReportValidator() {

        super("validation/emissionsreport.properties");
    }

    @Override
    public boolean accept(ValidatorContext context, EmissionsReport report) {

        return report != null
            && getCefValidatorContext(context).isEnabled(ValidationFeature.Federal);
    }

    @Override
    public boolean validate(ValidatorContext context, EmissionsReport report) {

        boolean valid = true;

        if (report.getYear() == null) {

            valid = false;
            context.addError(error("report.year", "e0001"));

        } else if (report.getYear().intValue() < 2019) {

            context.addError(warning("report.year", "w0001"));
        }

        if (Strings.emptyToNull(report.getAgencyCode()) == null) {

            valid = false;
            context.addError(error("report.agencyCode", "e0002"));
        }

        if (Strings.emptyToNull(report.getFrsFacilityId()) == null) {

            valid = false;
            context.addError(error("report.frsFacilityId", "e0003"));
        }

        if (Strings.emptyToNull(report.getEisProgramId()) == null) {

            valid = false;
            context.addError(error("report.eisProgramId", "e0004"));
        }

        valid = FluentValidator.checkAll().failOver()
            .withContext(context)
            .onEach(report.getFacilitySites(), new FacilitySiteValidator())
            .doValidate()
            .result(toSimple())
            .isSuccess() && valid;

        return valid;
    }
}
