package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.domain.CalculationMethodCode;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.ReportAttachmentRepository;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsReportValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionsReportValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private EmissionsReportValidator validator;
    
    @Mock
    private EmissionRepository emissionRepo;
    
    @Mock
	private ReportAttachmentRepository attachmentRepo;
    
    @Before
    public void init(){
    	
    	List<Emission> eList = new ArrayList<Emission>();
    	Emission e = new Emission();
        e.setId(1L);
        e.setTotalManualEntry(true);
        CalculationMethodCode cmc = new CalculationMethodCode();
        cmc.setTotalDirectEntry(false);
        e.setEmissionsCalcMethodCode(cmc);
        eList.add(e);
        
        List<ReportAttachment> raList = new ArrayList<ReportAttachment>();
        List<ReportAttachment> raList2 = new ArrayList<ReportAttachment>();
        ReportAttachment ra = new ReportAttachment();
        ra.setId(1L);
        raList.add(ra);
        
    	when(emissionRepo.findAllByReportId(1L)).thenReturn(eList);
    	when(attachmentRepo.findAllByReportId(1L)).thenReturn(raList);
    	
    	when(emissionRepo.findAllByReportId(2L)).thenReturn(eList);
    	when(attachmentRepo.findAllByReportId(2L)).thenReturn(raList2);
    }

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void nullYearTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setYear(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.REPORT_YEAR.value()) && errorMap.get(ValidationField.REPORT_YEAR.value()).size() == 1);
    }

    @Test
    public void oldYearTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setYear(Integer.valueOf(testData.getYear() - 1).shortValue());

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.REPORT_YEAR.value()) && errorMap.get(ValidationField.REPORT_YEAR.value()).size() == 1);
    }

    @Test
    public void nullAgencyCodeTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setAgencyCode(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.REPORT_AGENCY_CODE.value()) && errorMap.get(ValidationField.REPORT_AGENCY_CODE.value()).size() == 1);
    }

    @Test
    public void nullFrsFacilityIdTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setFrsFacilityId(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.REPORT_FRS_ID.value()) && errorMap.get(ValidationField.REPORT_FRS_ID.value()).size() == 1);
    }

    @Test
    public void nullEisProgramIdTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setEisProgramId(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.REPORT_EIS_ID.value()) && errorMap.get(ValidationField.REPORT_EIS_ID.value()).size() == 1);
    }
    
    @Test
    public void totalManualEntryOrTotalDirectEntryRequireAttachmentFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setId(2L);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.REPORT_ATTACHMENT.value()) && errorMap.get(ValidationField.REPORT_ATTACHMENT.value()).size() == 1);
    }


    private EmissionsReport createBaseReport() {

        EmissionsReport result = new EmissionsReport();
        result.setYear(Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1).shortValue());
        result.setAgencyCode("GA");
        result.setFrsFacilityId("1");
        result.setEisProgramId("1");
        result.setId(1L);
        
        return result;
    }
}
