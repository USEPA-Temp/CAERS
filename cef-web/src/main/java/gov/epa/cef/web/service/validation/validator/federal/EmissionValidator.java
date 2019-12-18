package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

import java.math.BigDecimal;
import java.text.MessageFormat;

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
                createValidationDetails(emission));

        } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == true) {

            if(Strings.emptyToNull(emission.getComments()) == null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_COMMENTS.value(),
                        "emission.comments.required.method", 
                        createValidationDetails(emission));
            }

            if(emission.getEmissionsFactor() != null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_EF.value(),
                        "emission.emissionsFactor.banned.method",
                        createValidationDetails(emission));
            }

            if (emission.getReportingPeriod() != null 
                    && emission.getReportingPeriod().getCalculationParameterValue().compareTo(BigDecimal.ZERO) == 0
                    && emission.getTotalEmissions().compareTo(BigDecimal.ZERO) != 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_TOTAL_EMISSIONS.value(),
                        "emission.totalEmissions.nonzero.method", 
                        createValidationDetails(emission));
            }

        } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == false) {

            if(emission.getEmissionsFactor() == null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_EF.value(),
                        "emission.emissionsFactor.required.method",
                        createValidationDetails(emission));
            }
        }

        if (emission.getEmissionsFactor() != null) {

            if (emission.getEmissionsNumeratorUom() == null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_NUM_UOM.value(),
                        "emission.emissionsNumeratorUom.required.emissionsFactor", 
                        createValidationDetails(emission));
            }

            if (emission.getEmissionsDenominatorUom() == null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_DENOM_UOM.value(),
                        "emission.emissionsDenominatorUom.required.emissionsFactor", 
                        createValidationDetails(emission));
            }

        } else if (emission.getEmissionsFactor() == null) {

            if (emission.getEmissionsNumeratorUom() != null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_NUM_UOM.value(),
                        "emission.emissionsNumeratorUom.banned.emissionsFactor", 
                        createValidationDetails(emission));
            }

            if (emission.getEmissionsDenominatorUom() != null) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_DENOM_UOM.value(),
                        "emission.emissionsDenominatorUom.banned.emissionsFactor", 
                        createValidationDetails(emission));
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
