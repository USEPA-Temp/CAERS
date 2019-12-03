package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

import java.math.BigDecimal;

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
                ValidationField.EMISSION_CALC_METHOD.value(),
                "emission.emissionsCalcMethodCode.required", 
                getEmissionsUnitIdentifier(emission),
                getEmissionsProcessIdentifier(emission),
                getPollutantName(emission));

        } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == true) {

            if(Strings.emptyToNull(emission.getComments()) == null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_COMMENTS.value(),
                        "emission.comments.required.method", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

            if(emission.getEmissionsFactor() != null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_EF.value(),
                        "emission.emissionsFactor.banned.method", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

            if (emission.getReportingPeriod() != null 
                    && emission.getReportingPeriod().getCalculationParameterValue().compareTo(BigDecimal.ZERO) == 0
                    && emission.getTotalEmissions().compareTo(BigDecimal.ZERO) != 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_TOTAL_EMISSIONS.value(),
                        "emission.totalEmissions.nonzero.method", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

        } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == false) {

            if(emission.getEmissionsFactor() == null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_EF.value(),
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
                        ValidationField.EMISSION_NUM_UOM.value(),
                        "emission.emissionsNumeratorUom.required.emissionsFactor", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

            if (emission.getEmissionsDenominatorUom() == null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_DENOM_UOM.value(),
                        "emission.emissionsDenominatorUom.required.emissionsFactor", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

        } else if (emission.getEmissionsFactor() == null) {

            if (emission.getEmissionsNumeratorUom() != null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_NUM_UOM.value(),
                        "emission.emissionsNumeratorUom.banned.emissionsFactor", 
                        getEmissionsUnitIdentifier(emission),
                        getEmissionsProcessIdentifier(emission),
                        getPollutantName(emission));
            }

            if (emission.getEmissionsDenominatorUom() != null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_DENOM_UOM.value(),
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
