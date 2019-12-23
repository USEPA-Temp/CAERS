package gov.epa.cef.web.service.validation.validator.federal;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

@Component
public class ReleasePointValidator extends BaseValidator<ReleasePoint> {

    @Override
    public boolean validate(ValidatorContext validatorContext, ReleasePoint releasePoint) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        // Exit Gas Temperature must be between 30 and 3500 degrees Fahrenheit.
        if (releasePoint.getExitGasTemperature() != null && (releasePoint.getExitGasTemperature() < 30 || releasePoint.getExitGasTemperature() > 3500)) {

            result = false;
            context.addFederalError(
                    ValidationField.RP_GAS_TEMP.value(),
                    "releasePoint.exitGasTemperature.range",
                    createValidationDetails(releasePoint));
        }


        return result;
    }

    private ValidationDetailDto createValidationDetails(ReleasePoint source) {

        String description = MessageFormat.format("Release Point: {0}", source.getReleasePointIdentifier());

        ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getReleasePointIdentifier(), EntityType.RELEASE_POINT, description);
        return dto;
    }
}
