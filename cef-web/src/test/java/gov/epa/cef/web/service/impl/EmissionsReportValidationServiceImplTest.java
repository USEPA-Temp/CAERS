package gov.epa.cef.web.service.impl;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorChain;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.ValidationResult;
import gov.epa.cef.web.service.validation.validator.IEmissionsReportValidator;
import gov.epa.cef.web.service.validation.validator.federal.EmissionValidator;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsProcessValidator;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsReportValidator;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsUnitValidator;
import gov.epa.cef.web.service.validation.validator.federal.FacilitySiteValidator;
import gov.epa.cef.web.service.validation.validator.federal.OperatingDetailValidator;
import gov.epa.cef.web.service.validation.validator.federal.ReleasePointValidator;
import gov.epa.cef.web.service.validation.validator.federal.ReportingPeriodValidator;
import gov.epa.cef.web.service.validation.validator.state.GeorgiaValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        when(validationRegistry.findOneByType(ReleasePointValidator.class))
            .thenReturn(new ReleasePointValidator());

        when(validationRegistry.findOneByType(EmissionsUnitValidator.class))
            .thenReturn(new EmissionsUnitValidator());

        when(validationRegistry.findOneByType(EmissionsProcessValidator.class))
            .thenReturn(new EmissionsProcessValidator());

        when(validationRegistry.findOneByType(ReportingPeriodValidator.class))
            .thenReturn(new ReportingPeriodValidator());

        when(validationRegistry.findOneByType(OperatingDetailValidator.class))
            .thenReturn(new OperatingDetailValidator());

        when(validationRegistry.findOneByType(EmissionValidator.class))
            .thenReturn(new EmissionValidator());

        ValidatorChain reportChain = new ValidatorChain();
        reportChain.setValidators(Arrays.asList(new EmissionsReportValidator(), new GeorgiaValidator()));

        when(validationRegistry.createValidatorChain(IEmissionsReportValidator.class))
            .thenReturn(reportChain);
    }

    @Test
    public void simpleValidateFailureTest() {

        EmissionsReport report = new EmissionsReport();

        FacilitySite facilitySite = new FacilitySite();
        EmissionsUnit emissionsUnit = new EmissionsUnit();
        EmissionsProcess emissionsProcess = new EmissionsProcess();
        ReportingPeriod reportingPeriod = new ReportingPeriod();
        OperatingDetail detail = new OperatingDetail();
        Emission emission = new Emission();
        
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
        List<FacilityNAICSXref> naicsList = new ArrayList<FacilityNAICSXref>();
        FacilityNAICSXref facilityNaics = new FacilityNAICSXref();
      	
        NaicsCode naics = new NaicsCode();
        naics.setCode(332116);
        naics.setDescription("Metal Stamping");
        
        facilityNaics.setNaicsCode(naics);
        facilityNaics.setPrimaryFlag(true);
        naicsList.add(facilityNaics);
        
        facilitySite.setFacilityNAICS(naicsList);
        facilitySite.setOperatingStatusCode(opStatCode);

        reportingPeriod.getEmissions().add(emission);
        reportingPeriod.getOperatingDetails().add(detail);
        emissionsProcess.getReportingPeriods().add(reportingPeriod);
        emissionsUnit.getEmissionsProcesses().add(emissionsProcess);
        emissionsUnit.setOperatingStatusCode(opStatCode);
        emissionsUnit.setFacilitySite(facilitySite);
        facilitySite.getEmissionsUnits().add(emissionsUnit);
        report.getFacilitySites().add(facilitySite);

        ValidationResult result = this.validationService.validate(report);
        assertFalse(result.isValid());

        Map<String, ValidationError> federalErrors = result.getFederalErrors().stream()
            .collect(Collectors.toMap(ValidationError::getField, re -> re));

        logger.debug("Failures {}", String.join(", ", federalErrors.keySet()));
        assertTrue(federalErrors.containsKey("report.eisProgramId"));
        assertTrue(federalErrors.containsKey("report.facilitySite.eisProgramId"));
        assertTrue(federalErrors.containsKey("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationParameterValue"));
        assertTrue(federalErrors.containsKey("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsCalcMethodCode"));
    }
}
