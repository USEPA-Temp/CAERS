package gov.epa.cef.web.service.validation.validator.federal;

import java.text.MessageFormat;
import org.springframework.stereotype.Component;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import gov.epa.cef.web.domain.ControlPathPollutant;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ControlPathPollutantValidator extends BaseValidator<ControlPathPollutant> {

    @Override
    public boolean validate(ValidatorContext validatorContext, ControlPathPollutant controlPathPollutant) {

        boolean result = true;
        CefValidatorContext context = getCefValidatorContext(validatorContext);
        
        String regex = "^\\d{0,3}(\\.\\d{1})?$";
        Pattern pattern = Pattern.compile(regex);
        if(controlPathPollutant.getPercentReduction() != null) {
            Matcher matcher = pattern.matcher(controlPathPollutant.getPercentReduction().toString());
            if(!matcher.matches()){
                result = false;
                context.addFederalError(
                    ValidationField.CONTROL_PATH_POLLUTANT_PERCENT_REDUCTION.value(),
                    "controlPath.pollutant.percentReductionEfficiency.invalidFormat",
                    createValidationDetails(controlPathPollutant),
                    controlPathPollutant.getControlPath().getPathId());
            }
        }

        return result;
    }

    private ValidationDetailDto createValidationDetails(ControlPathPollutant source) {

        String description = MessageFormat.format("ControlPath: {0}, ControlPathPollutant: {1}", source.getControlPath().getPathId(), source.getPollutant().getPollutantCode());

        ValidationDetailDto dto = new ValidationDetailDto(source.getControlPath().getId(), source.getControlPath().getPathId(), EntityType.CONTROL_PATH, description);
        return dto;
    }

}
