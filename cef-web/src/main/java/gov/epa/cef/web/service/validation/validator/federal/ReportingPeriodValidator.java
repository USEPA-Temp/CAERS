package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        validator.onEach(reportingPeriod.getOperatingDetails(),
                registry.findOneByType(OperatingDetailValidator.class));
    }

    @Override
    public boolean validate(ValidatorContext validatorContext, ReportingPeriod period) {

        boolean valid = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        if (period.getCalculationParameterValue() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(
                ValidationField.PERIOD_CALC_VALUE.value(),
                "reportingPeriod.calculationParameterValue.required", 
                createValidationDetails(period));

        } else if (period.getCalculationParameterValue().compareTo(BigDecimal.ZERO) < 0) {

            valid = false;
            context.addFederalError(
                    ValidationField.PERIOD_CALC_VALUE.value(),
                    "reportingPeriod.calculationParameterValue.min", 
                    createValidationDetails(period));
        }

        if (period.getCalculationMaterialCode() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(
                    ValidationField.PERIOD_CALC_MAT_CODE.value(),
                    "reportingPeriod.calculationMaterialCode.required", 
                    createValidationDetails(period));
        }

        if (period.getCalculationParameterTypeCode() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(
                    ValidationField.PERIOD_CALC_TYPE_CODE.value(),
                    "reportingPeriod.calculationParameterTypeCode.required", 
                    createValidationDetails(period));
        }

        if (period.getCalculationParameterUom() == null) {

            // prevented by db constraints
            valid = false;
            context.addFederalError(
                    ValidationField.PERIOD_CALC_UOM.value(),
                    "reportingPeriod.calculationParameterUom.required", 
                    createValidationDetails(period));
        }

        Map<String, List<Emission>> emissionMap = period.getEmissions().stream()
                .filter(e -> e.getPollutant() != null)
                .collect(Collectors.groupingBy(e -> e.getPollutant().getPollutantCode()));
        
        for (List<Emission> emissions: emissionMap.values()) {

            if (emissions.size() > 1) {

                valid = false;
                context.addFederalError(
                        ValidationField.PERIOD_EMISSION.value(),
                        "reportingPeriod.emission.duplicate", 
                        createValidationDetails(period),
                        emissions.get(0).getPollutant().getPollutantName());
            }
        }

        // start batch of PM validations
        if (emissionMap.containsKey("PM10-FIL") || emissionMap.containsKey("PM10-PRI") 
                || emissionMap.containsKey("PM25-FIL") || emissionMap.containsKey("PM25-PRI") 
                || emissionMap.containsKey("PM-CON")) {

            // get the values of all the pm emissions
            BigDecimal pm10Fil = emissionMap.containsKey("PM10-FIL") ? emissionMap.get("PM10-FIL").get(0).getCalculatedEmissionsTons() : null;
            BigDecimal pm10Pri = emissionMap.containsKey("PM10-PRI") ? emissionMap.get("PM10-PRI").get(0).getCalculatedEmissionsTons() : null;
            BigDecimal pm25Fil = emissionMap.containsKey("PM25-FIL") ? emissionMap.get("PM25-FIL").get(0).getCalculatedEmissionsTons() : null;
            BigDecimal pm25Pri = emissionMap.containsKey("PM25-PRI") ? emissionMap.get("PM25-PRI").get(0).getCalculatedEmissionsTons() : null;
            BigDecimal pmCon = emissionMap.containsKey("PM-CON") ? emissionMap.get("PM-CON").get(0).getCalculatedEmissionsTons() : null;

            // PM10 Filterable should not exceed PM10 Primary
            if (pm10Fil != null && pm10Pri != null && pm10Fil.compareTo(pm10Pri) > 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.PERIOD_EMISSION.value(),
                        "reportingPeriod.emission.pm10.fil.greater.pri", 
                        createValidationDetails(period));
            }

            // PM2.5 Filterable should not exceed PM2.5 Primary
            if (pm25Fil != null && pm25Pri != null && pm25Fil.compareTo(pm25Pri) > 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.PERIOD_EMISSION.value(),
                        "reportingPeriod.emission.pm25.fil.greater.pri", 
                        createValidationDetails(period));
            }

            // PM Condensable should not exceed PM10 Primary
            if (pmCon != null && pm10Pri != null && pmCon.compareTo(pm10Pri) > 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.PERIOD_EMISSION.value(),
                        "reportingPeriod.emission.pm10.con.greater.pri", 
                        createValidationDetails(period));
            }

            // PM Condensable should not exceed PM2.5 Primary
            if (pmCon != null && pm25Pri != null && pmCon.compareTo(pm25Pri) > 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.PERIOD_EMISSION.value(),
                        "reportingPeriod.emission.pm25.con.greater.pri", 
                        createValidationDetails(period));
            }

            // PM10-FIL + PM-CON must equal PM10-PRI
            if (pmCon != null && pm10Fil != null && pm10Pri != null && pmCon.add(pm10Fil).compareTo(pm10Pri) != 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.PERIOD_EMISSION.value(),
                        "reportingPeriod.emission.pm10.invalid", 
                        createValidationDetails(period));
            }

            // PM25-FIL + PM-CON must equal PM25-PRI
            if (pmCon != null && pm25Fil != null && pm25Pri != null && pmCon.add(pm25Fil).compareTo(pm25Pri) != 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.PERIOD_EMISSION.value(),
                        "reportingPeriod.emission.pm25.invalid", 
                        createValidationDetails(period));
            }

            // PM2.5 Primary should not exceed PM10 Primary
            if (pm10Pri != null && pm25Pri != null && pm25Pri.compareTo(pm10Pri) > 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.PERIOD_EMISSION.value(),
                        "reportingPeriod.emission.pm25.pri.greater.pm10", 
                        createValidationDetails(period));
            }

            // PM2.5 Filterable should not exceed PM10 Filterable
            if (pm10Fil != null && pm25Fil != null && pm25Fil.compareTo(pm10Fil) > 0) {

                valid = false;
                context.addFederalError(
                        ValidationField.PERIOD_EMISSION.value(),
                        "reportingPeriod.emission.pm25.fil.greater.pm10", 
                        createValidationDetails(period));
            }
        }

        // Fluorides/16984488 value must be greater than or equal to HF/7664393; Flourides is not currently in the db so validation cannot be triggered
        if (emissionMap.containsKey("16984488") && emissionMap.containsKey("7664393") 
                && emissionMap.get("7664393").get(0).getCalculatedEmissionsTons().compareTo(emissionMap.get("16984488").get(0).getCalculatedEmissionsTons()) > 0) {

            valid = false;
            context.addFederalError(
                    ValidationField.PERIOD_EMISSION.value(),
                    "reportingPeriod.emission.hf.greater.fluorides", 
                    createValidationDetails(period));
        }

        return valid;
    }

    private String getEmissionsUnitIdentifier(ReportingPeriod period) {
        if (period.getEmissionsProcess() != null && period.getEmissionsProcess().getEmissionsUnit() != null) {
            return period.getEmissionsProcess().getEmissionsUnit().getUnitIdentifier();
        }
        return null;
    }

    private String getEmissionsProcessIdentifier(ReportingPeriod period) {
        if (period.getEmissionsProcess() != null) {
            return period.getEmissionsProcess().getEmissionsProcessIdentifier();
        }
        return null;
    }

    private ValidationDetailDto createValidationDetails(ReportingPeriod source) {

        String description = MessageFormat.format("Emission Unit: {0}, Emission Process: {1}", 
                getEmissionsUnitIdentifier(source),
                getEmissionsProcessIdentifier(source));

        ValidationDetailDto dto = new ValidationDetailDto(source.getId(), getEmissionsProcessIdentifier(source), EntityType.REPORTING_PERIOD, description);
        if (source.getEmissionsProcess() != null) {
            dto.getParents().add(new ValidationDetailDto(
                    source.getEmissionsProcess().getId(),
                    source.getEmissionsProcess().getEmissionsProcessIdentifier(),
                    EntityType.EMISSIONS_PROCESS));
        }
        return dto;
    }
}
