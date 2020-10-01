package gov.epa.cef.web.service.impl;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorChain;

import gov.epa.cef.web.domain.CalculationMethodCode;
import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ReportHistory;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.ReportHistoryRepository;
import gov.epa.cef.web.service.validation.ValidationRegistry;
import gov.epa.cef.web.service.validation.ValidationResult;
import gov.epa.cef.web.service.validation.validator.IEmissionsReportValidator;
import gov.epa.cef.web.service.validation.validator.federal.ControlPathValidator;
import gov.epa.cef.web.service.validation.validator.federal.ControlValidator;
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
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
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

    @Spy
    private EmissionRepository emissionRepo;
    
    @Spy
    private ReportHistoryRepository historyRepo;
    
    @Spy
    @InjectMocks
    private EmissionsReportValidator erValidator;
    
    @InjectMocks
    private EmissionsReportValidationServiceImpl validationService;

    @Before
    public void _onJunitBeginTest() {
    	
    	List<Emission> eList = new ArrayList<Emission>();
    	Emission e = new Emission();
        e.setId(1L);
        e.setTotalManualEntry(true);
        CalculationMethodCode cmc = new CalculationMethodCode();
        cmc.setTotalDirectEntry(false);
        e.setEmissionsCalcMethodCode(cmc);
        eList.add(e);
        
        List<ReportHistory> raList = new ArrayList<ReportHistory>();
        ReportHistory ra = new ReportHistory();
        ra.setId(1L);
        ra.setUserRole("Preparer");
        ra.setReportAttachmentId(1L);
        ra.setFileDeleted(false);
        raList.add(ra);
        
        when(emissionRepo.findAllByReportId(1L)).thenReturn(eList);
        when(historyRepo.findByEmissionsReportIdOrderByActionDate(1L)).thenReturn(raList);

        when(validationRegistry.findOneByType(FacilitySiteValidator.class))
            .thenReturn(new FacilitySiteValidator());

        when(validationRegistry.findOneByType(ReleasePointValidator.class))
            .thenReturn(new ReleasePointValidator());

        when(validationRegistry.findOneByType(EmissionsUnitValidator.class))
            .thenReturn(new EmissionsUnitValidator());
        
        when(validationRegistry.findOneByType(ControlValidator.class))
        .thenReturn(new ControlValidator());

        when(validationRegistry.findOneByType(EmissionsProcessValidator.class))
            .thenReturn(new EmissionsProcessValidator());

        when(validationRegistry.findOneByType(ReportingPeriodValidator.class))
            .thenReturn(new ReportingPeriodValidator());

        when(validationRegistry.findOneByType(OperatingDetailValidator.class))
            .thenReturn(new OperatingDetailValidator());

        when(validationRegistry.findOneByType(EmissionValidator.class))
            .thenReturn(new EmissionValidator());
        
        when(validationRegistry.findOneByType(ControlPathValidator.class))
        .thenReturn(new ControlPathValidator());

        ValidatorChain reportChain = new ValidatorChain();
        reportChain.setValidators(Arrays.asList(erValidator, new GeorgiaValidator()));

        when(validationRegistry.createValidatorChain(IEmissionsReportValidator.class))
            .thenReturn(reportChain);
    }

    @Test
    public void simpleValidateFailureTest() {

        EmissionsReport report = new EmissionsReport();
        report.setId(1L);
        report.setYear((short) 2020);

        FacilitySite facilitySite = new FacilitySite();
        facilitySite.setStatusYear((short) 2019);
        EmissionsUnit emissionsUnit = new EmissionsUnit();
        EmissionsProcess emissionsProcess = new EmissionsProcess();
        ReportingPeriod reportingPeriod = new ReportingPeriod();
        OperatingDetail detail = new OperatingDetail();
        Emission emission = new Emission();
        emission.setTotalEmissions(new BigDecimal(10));
        ControlPath controlPath = new ControlPath();
        Control control = new Control(); 
        control.setIdentifier("control_Identifier");
        control.setPercentCapture(50.0);
        control.setPercentControl(50.0);
        control.setFacilitySite(facilitySite);
        controlPath.setFacilitySite(facilitySite);
        facilitySite.getControls().add(control);
        facilitySite.getControlPaths().add(controlPath);
        
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
        reportingPeriod.setEmissionsProcess(emissionsProcess);
        reportingPeriod.getOperatingDetails().add(detail);
        detail.setReportingPeriod(reportingPeriod);
        emission.setReportingPeriod(reportingPeriod);
        emissionsProcess.getReportingPeriods().add(reportingPeriod);
        emissionsProcess.setEmissionsUnit(emissionsUnit);
        emissionsProcess.setOperatingStatusCode(opStatCode);
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
