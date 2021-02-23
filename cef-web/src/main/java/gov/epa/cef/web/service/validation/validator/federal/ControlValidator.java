package gov.epa.cef.web.service.validation.validator.federal;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Strings;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

@Component
public class ControlValidator extends BaseValidator<Control> {

    @Override
    public boolean validate(ValidatorContext validatorContext, Control control) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);


		Map<Object, List<Control>> cMap = control.getFacilitySite().getControls().stream()
        .filter(controls -> (controls.getIdentifier() != null))
        .collect(Collectors.groupingBy(c -> c.getIdentifier().trim().toLowerCase()));

		for (List<Control> cList: cMap.values()) {
			if (cList.size() > 1 && cList.get(0).getIdentifier().trim().toLowerCase().contentEquals(control.getIdentifier().trim().toLowerCase())) {

				result = false;
				context.addFederalError(
	  			ValidationField.CONTROL_IDENTIFIER.value(),
	  			"control.controlIdentifier.duplicate",
	  			createValidationDetails(control));
			}
		}

        if (control.getControlMeasureCode() != null && control.getControlMeasureCode().getLastInventoryYear() != null
            && control.getControlMeasureCode().getLastInventoryYear() < control.getFacilitySite().getEmissionsReport().getYear()) {

            result = false;
            if (Strings.emptyToNull(control.getControlMeasureCode().getMapTo()) != null) {

                context.addFederalError(
                    ValidationField.CONTROL_MEASURE_CODE.value(),
                    "control.controlMeasureCode.legacy.map",
                    createValidationDetails(control),
                    control.getControlMeasureCode().getDescription(),
                    control.getControlMeasureCode().getMapTo());
            } else {

                context.addFederalError(
                    ValidationField.CONTROL_MEASURE_CODE.value(),
                    "control.controlMeasureCode.legacy",
                    createValidationDetails(control),
                    control.getControlMeasureCode().getDescription());
            }

        }

        if (control.getPercentControl() != null && (control.getPercentControl() < 1 || control.getPercentControl() > 100)) {

            result = false;
            context.addFederalError(
                ValidationField.CONTROL_PERCENT_CONTROL.value(),
                "control.percentControl.range",
                createValidationDetails(control));
        }

        for (ControlPollutant cp : control.getPollutants()) {

            if (cp.getPollutant().getLastInventoryYear() != null && cp.getPollutant().getLastInventoryYear() < control.getFacilitySite().getEmissionsReport().getYear()) {

                result = false;
                context.addFederalError(
                    ValidationField.CONTROL_POLLUTANT.value(),
                    "control.controlPollutant.legacy",
                    createValidationDetails(control),
                    cp.getPollutant().getPollutantName());
            }

            if (cp.getPercentReduction() < 5 || cp.getPercentReduction() > 99.9) {

                result = false;
                context.addFederalError(
                    ValidationField.CONTROL_POLLUTANT.value(),
                    "control.controlPollutant.range",
                    createValidationDetails(control),
                    cp.getPollutant().getPollutantName());
            }
        }

        Map<Object, List<ControlPollutant>> cpMap = control.getPollutants().stream()
            .filter(cp -> cp.getPollutant() != null)
            .collect(Collectors.groupingBy(p -> p.getPollutant().getPollutantName()));

        for (List<ControlPollutant> pList : cpMap.values()) {
            if (pList.size() > 1) {

                result = false;
                context.addFederalError(
                    ValidationField.CONTROL_POLLUTANT.value(),
                    "control.controlPollutant.duplicate",
                    createValidationDetails(control),
                    pList.get(0).getPollutant().getPollutantName());

            }
        }

        if (control.getAssignments().isEmpty()) {

            result = false;
            context.addFederalWarning(
                ValidationField.CONTROL_PATH_WARNING.value(),
                "control.pathWarning.notAssigned",
                createValidationDetails(control));
        }

        if (control.getPollutants().isEmpty()) {

            result = false;
            context.addFederalError(
                ValidationField.CONTROL_POLLUTANT.value(),
                "control.controlPollutant.required",
                createValidationDetails(control));
        }

        return result;
    }

    private ValidationDetailDto createValidationDetails(Control source) {

        String description = MessageFormat.format("Control: {0}", source.getIdentifier());

        ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getIdentifier(), EntityType.CONTROL, description);
        return dto;
    }

}
