package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Strings;

@Component
public class EmissionValidator extends BaseValidator<Emission> {

    @Override
    public boolean validate(ValidatorContext validatorContext, Emission emission) {

        boolean valid = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        if (emission.getEmissionsCalcMethodCode() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(
                "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsCalcMethodCode",
                "emission.emissionsCalcMethodCode.required");

        } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == true) {

            if(Strings.emptyToNull(emission.getComments()) == null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.comments",
                        "emission.comments.required.method");
            }

            if(emission.getEmissionsFactor() != null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsFactor",
                        "emission.emissionsFactor.banned.method");
            }
        } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == false) {

            if(emission.getEmissionsFactor() == null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsFactor",
                        "emission.emissionsFactor.required.method");
            }
        }

        if (emission.getEmissionsFactor() != null) {

            if (emission.getEmissionsNumeratorUom() == null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsNumeratorUom",
                        "emission.emissionsNumeratorUom.required.emissionsFactor");
            }

            if (emission.getEmissionsDenominatorUom() == null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsDenominatorUom",
                        "emission.emissionsDenominatorUom.required.emissionsFactor");
            }

        } else if (emission.getEmissionsFactor() == null) {

            if (emission.getEmissionsNumeratorUom() != null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsNumeratorUom",
                        "emission.emissionsNumeratorUom.banned.emissionsFactor");
            }

            if (emission.getEmissionsDenominatorUom() != null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsDenominatorUom",
                        "emission.emissionsDenominatorUom.banned.emissionsFactor");
            }

        }

        return valid;
    }
}
