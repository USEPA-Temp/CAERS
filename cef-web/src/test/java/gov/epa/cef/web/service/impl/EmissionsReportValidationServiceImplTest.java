package gov.epa.cef.web.service.impl;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorChain;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.ValidationResult;
import gov.epa.cef.web.service.validation.validator.IEmissionsReportValidator;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsReportValidator;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsUnitValidator;
import gov.epa.cef.web.service.validation.validator.federal.FacilitySiteValidator;
import gov.epa.cef.web.service.validation.validator.state.GeorgiaValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionsReportValidationServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Mock
    private ValidationRegistry validationRegistry;

    @InjectMocks
    private EmissionsReportValidationServiceImpl validationService;

    @Before
    public void _onJunitBeginTest() {

        when(validationRegistry.findOneByType(FacilitySiteValidator.class))
            .thenReturn(new FacilitySiteValidator());

        when(validationRegistry.findOneByType(EmissionsUnitValidator.class))
            .thenReturn(new EmissionsUnitValidator());

        ValidatorChain reportChain = new ValidatorChain();
        reportChain.setValidators(Arrays.asList(new EmissionsReportValidator(), new GeorgiaValidator()));

        when(validationRegistry.createValidatorChain(IEmissionsReportValidator.class))
            .thenReturn(reportChain);
    }

    @Test
    public void simpleValidateFailureTest() {

        EmissionsReport report = new EmissionsReport();

        FacilitySite facilitySite = new FacilitySite();
        report.getFacilitySites().add(facilitySite);

        ValidationResult result = this.validationService.validate(report);
        assertFalse(result.isValid());

        Map<String, ValidationError> federalErrors = result.getFederalErrors().stream()
            .collect(Collectors.toMap(ValidationError::getField, re -> re));

        logger.debug("Failures {}", String.join(", ", federalErrors.keySet()));
        assertTrue(federalErrors.containsKey("report.eisProgramId"));
        assertTrue(federalErrors.containsKey("report.facilitySite.eisProgramId"));
    }
}