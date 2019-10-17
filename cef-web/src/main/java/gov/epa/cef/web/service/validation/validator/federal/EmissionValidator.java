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
                "emission.emissionsCalcMethodCode.required", 
                getEmissionsUnitIdentifier(emission),
                getEmissionsProcessIdentifier(emission),
                getPollutantName(emission));

        } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == true) {

            if(Strings.emptyToNull(emission.getComments()) == null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.comments",
                        "emission.comments.required.method", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

            if(emission.getEmissionsFactor() != null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsFactor",
                        "emission.emissionsFactor.banned.method", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }
        } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == false) {

            if(emission.getEmissionsFactor() == null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsFactor",
                        "emission.emissionsFactor.required.method",
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }
        }

        if (emission.getEmissionsFactor() != null) {

            if (emission.getEmissionsNumeratorUom() == null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsNumeratorUom",
                        "emission.emissionsNumeratorUom.required.emissionsFactor", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

            if (emission.getEmissionsDenominatorUom() == null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsDenominatorUom",
                        "emission.emissionsDenominatorUom.required.emissionsFactor", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

        } else if (emission.getEmissionsFactor() == null) {

            if (emission.getEmissionsNumeratorUom() != null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsNumeratorUom",
                        "emission.emissionsNumeratorUom.banned.emissionsFactor", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

            if (emission.getEmissionsDenominatorUom() != null) {

                valid = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsDenominatorUom",
                        "emission.emissionsDenominatorUom.banned.emissionsFactor", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

        }

        return valid;
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
}
