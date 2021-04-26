package gov.epa.cef.web.service.validation.validator.federal;

import java.text.MessageFormat;
import org.springframework.stereotype.Component;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ControlPollutantValidator extends BaseValidator<ControlPollutant> {

    @Override
    public boolean validate(ValidatorContext validatorContext, ControlPollutant controlPollutant) {

        boolean result = true;
        CefValidatorContext context = getCefValidatorContext(validatorContext);

        String regex = "^\\d{0,3}(\\.\\d{1})?$";
        Pattern pattern = Pattern.compile(regex);
        if(controlPollutant.getPercentReduction() != null) {
            Matcher matcher = pattern.matcher(controlPollutant.getPercentReduction().toString());
            if(!matcher.matches()){
                result = false;
                context.addFederalError(
                    ValidationField.CONTROL_POLLUTANT_PERCENT_REDUCTION.value(),
                    "control.pollutant.percentReductionEfficiency.invalidFormat",
                    createValidationDetails(controlPollutant),
                    controlPollutant.getControl().getIdentifier());
            }
        }

        return result;
    }

    private ValidationDetailDto createValidationDetails(ControlPollutant source) {

        String description = MessageFormat.format("Control: {0}, Control Pollutant: {1}", source.getControl().getIdentifier(), source.getPollutant().getPollutantName());

        ValidationDetailDto dto = new ValidationDetailDto(source.getControl().getId(), source.getControl().getIdentifier(), EntityType.CONTROL, description);
        return dto;
    }

}
