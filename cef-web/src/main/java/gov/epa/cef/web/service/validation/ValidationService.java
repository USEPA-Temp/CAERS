package gov.epa.cef.web.service.validation;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import gov.epa.cef.web.service.validation.validator.EmissionsReportValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class ValidationService {

    private final EmissionsReportRepository emissionsReportRepository;

    @Autowired
    ValidationService(EmissionsReportRepository emissionsReportRepository) {

        this.emissionsReportRepository = emissionsReportRepository;
    }

    public ValidationResult validateReport(long reportId, ValidationFeature... requestedFeatures) {

        return this.emissionsReportRepository.findById(reportId)
            .map(report -> validateReport(report, requestedFeatures))
            .orElseThrow(() ->
                new IllegalArgumentException(String.format("Emissions Report %d does not exist.", reportId)));
    }

    public ValidationResult validateReport(@NotNull EmissionsReport report,
                                           ValidationFeature... requestedFeatures) {

        ValidationResult result = new ValidationResult();

        CefValidatorContext cefContext = new CefValidatorContext()
            .enable(ValidationFeature.Federal)
            .enable(requestedFeatures);

        FluentValidator.checkAll().failOver()
            .putAttribute2Context(BaseValidator.CefContextKey, cefContext)
            .on(report, new EmissionsReportValidator())
            .doValidate()
            .result(result.federalCollector());

        if (cefContext.isEnabled(ValidationFeature.State)) {

            // if (CefContext.isEnabled(ValidationFeature.Georgia)) ... do something
        }

        return result;
    }
}
