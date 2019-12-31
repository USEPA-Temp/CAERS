package gov.epa.cef.web.service.impl;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.EmissionsReportValidationService;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationFeature;
import gov.epa.cef.web.service.validation.ValidationResult;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.validator.IEmissionsReportValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmissionsReportValidationServiceImpl implements EmissionsReportValidationService {

    private final EmissionsReportRepository emissionsReportRepository;

    private final ValidationRegistry validationRegistry;

    @Autowired
    EmissionsReportValidationServiceImpl(EmissionsReportRepository emissionsReportRepository,
                                         ValidationRegistry validationRegistry) {

        this.emissionsReportRepository = emissionsReportRepository;
        this.validationRegistry = validationRegistry;
    }

    public ValidationResult validate(long reportId, ValidationFeature... requestedFeatures) {

        return this.emissionsReportRepository.findById(reportId)
            .map(report -> validate(report, requestedFeatures))
            .orElseThrow(() -> new NotExistException("Emissions Report", reportId));
    }

    public ValidationResult validate(@NotNull EmissionsReport report,
                                     ValidationFeature... requestedFeatures) {

        ValidationResult result = new ValidationResult();

        CefValidatorContext cefContext =
            new CefValidatorContext(this.validationRegistry, "validation/emissionsreport")
                .enable(requestedFeatures);

        // Run thru all validators
        FluentValidator.checkAll().failOver()
            .withContext(cefContext)
            .on(report, this.validationRegistry.createValidatorChain(IEmissionsReportValidator.class))
            .doValidate()
            .result(result.resultCollector());

        return result;
    }

    @Override
    public ValidationResult validateAndUpdateStatus(long reportId, ValidationFeature... requestedFeatures) {

        ValidationResult result = validate(reportId, requestedFeatures);

        this.emissionsReportRepository.findById(reportId).ifPresent(report -> {

            if (result.hasAnyWarnings() && result.isValid()) {

                report.setValidationStatus(ValidationStatus.PASSED_WARNINGS);

            } else if (result.isValid()) {

                report.setValidationStatus(ValidationStatus.PASSED);

            } else {

                report.setValidationStatus(ValidationStatus.UNVALIDATED);
            }

            this.emissionsReportRepository.save(report);
        });

        return result;
    }
}
