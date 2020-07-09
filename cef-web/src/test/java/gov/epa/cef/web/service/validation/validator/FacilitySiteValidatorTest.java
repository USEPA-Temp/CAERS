package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.FacilitySourceTypeCode;
import gov.epa.cef.web.domain.FipsCounty;
import gov.epa.cef.web.domain.FipsStateCode;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.FacilitySiteValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FacilitySiteValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private FacilitySiteValidator validator;
    
    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void nullEisProgramIdTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setEisProgramId(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_EIS_ID.value()) && errorMap.get(ValidationField.FACILITY_EIS_ID.value()).size() == 1);
    }
    
    @Test
    public void facilityContactPhonePassTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void facilityContactPhoneFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.getContacts().get(0).setPhone("1234");

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_CONTACT_PHONE.value()) && errorMap.get(ValidationField.FACILITY_CONTACT_PHONE.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateOperationStatusTypeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        
        OperatingStatusCode opStatusCode = new OperatingStatusCode();
        opStatusCode.setCode("TS");
        
        EmissionsUnit eu = new EmissionsUnit();
        eu.setOperatingStatusCode(opStatusCode);
        eu.setFacilitySite(testData);
        testData.getEmissionsUnits().add(eu);
        testData.setOperatingStatusCode(opStatusCode);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_STATUS.value()) && errorMap.get(ValidationField.FACILITY_STATUS.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateStatusYearRangeTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        
        testData.setStatusYear((short) 1900);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.setStatusYear((short) 2050);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void simpleValidateStatusYearRangeFailTest() {
	      
        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        
        testData.setStatusYear((short) 1800);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_STATUS.value()) && errorMap.get(ValidationField.FACILITY_STATUS.value()).size() == 1);
        
        cefContext = createContext();
        testData.setStatusYear((short) 2051);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_STATUS.value()) && errorMap.get(ValidationField.FACILITY_STATUS.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateFacilityNAICSPrimaryFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        for (FacilityNAICSXref naicsFlag: testData.getFacilityNAICS()) {
        	naicsFlag.setPrimaryFlag(false);
        }
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_NAICS.value()) && errorMap.get(ValidationField.FACILITY_NAICS.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateFacilityNAICSCodeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setFacilityNAICS(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_NAICS.value()) && errorMap.get(ValidationField.FACILITY_NAICS.value()).size() == 2);
    }
    
    @Test
    public void simpleValidateContactTypeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();

        for (FacilitySiteContact contact: testData.getContacts()) {
        	ContactTypeCode contactTypeCode = new ContactTypeCode();
        	contactTypeCode.setCode("FAC");
        	contact.setType(contactTypeCode);
        }
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void simpleValidateContactEmailAddressFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.getContacts().get(0).setEmail("notAValidEmail");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void simpleValidateContactEmailAddressPassTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.getContacts().get(0).setEmail("validemail@gmail.com");
        
        assertTrue(this.validator.validate(cefContext, testData));
    }
    
    @Test
    public void simpleValidateContactPostalCodeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.getContacts().get(0).setPostalCode("1234567890");
        testData.getContacts().get(0).setMailingPostalCode("1234567890");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
    }
    
    @Test
    public void simpleValidateFacilityPostalCodeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setPostalCode("1234567890");
        testData.setMailingPostalCode("1234567890");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
    }
    
    @Test
    public void nullFacilityCountyTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setCountyCode(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_COUNTY.value()) && errorMap.get(ValidationField.FACILITY_COUNTY.value()).size() == 1);
    }
    
    @Test
    public void facilityCountyInvalidStateTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.getStateCode().setUspsCode("NC");;

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_COUNTY.value()) && errorMap.get(ValidationField.FACILITY_COUNTY.value()).size() == 1);
    }
    
    @Test
    public void facilityCountyLegacyFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.getStateCode().setCode("99");
        testData.getStateCode().setUspsCode("AK");

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_COUNTY.value()) && errorMap.get(ValidationField.FACILITY_COUNTY.value()).size() == 1);
    }
    
    @Test
    public void facilityContactCountyInvalidStateTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.getContacts().get(0).getStateCode().setCode("37");
        testData.getContacts().get(0).getStateCode().setUspsCode("NC");

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_CONTACT_COUNTY.value()) && errorMap.get(ValidationField.FACILITY_CONTACT_COUNTY.value()).size() == 1);
    }
    
    @Test
    public void facilityContactCountyLegacyFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.getContacts().get(0).getStateCode().setCode("99");
        testData.getContacts().get(0).getStateCode().setUspsCode("AK");

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_CONTACT_COUNTY.value()) && errorMap.get(ValidationField.FACILITY_CONTACT_COUNTY.value()).size() == 1);
    }
    
    @Test
    public void nullFacilityContactEmissionsInventoryTypeTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setContacts(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_CONTACT.value()) && errorMap.get(ValidationField.FACILITY_CONTACT.value()).size() == 1);
    }
    
    @Test
    public void nullFacilityContactEmailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        
        for (FacilitySiteContact contact: testData.getContacts()) {
        	contact.setEmail(null);
        };

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_CONTACT.value()) && errorMap.get(ValidationField.FACILITY_CONTACT.value()).size() == 1);
    }
    
    /**
     * There should be no errors when facility site operating status is not PS.
     * There should be one error when facility site operating status is PS and emission unit operating status is not PS.
     * There should be no errors when facility site operating status is PS and emission unit operating status is PS.
     */
    @Test
    public void facilityOperatingStatusPSEmissionUnitStatusTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setStatusYear((short) 2019);
        EmissionsUnit eu = new EmissionsUnit();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        eu.setOperatingStatusCode(opStatCode);
        eu.setFacilitySite(testData);
        testData.getEmissionsUnits().add(eu);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("PS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        eu.getOperatingStatusCode().setCode("PS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    /**
     * There should be no errors when facility site operating status is not PS.
     * There should be one error when facility site operating status is PS and control operating status is not PS.
     * There should be no errors when facility site operating status is PS and control operating status is PS.
     */
    @Test
    public void facilityOperatingStatusPSControlStatusTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setStatusYear((short) 2019);
        Control c = new Control();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        c.setOperatingStatusCode(opStatCode);
        c.setFacilitySite(testData);
        testData.getControls().add(c);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("PS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.CONTROL_STATUS_CODE.value()) && errorMap.get(ValidationField.CONTROL_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        c.getOperatingStatusCode().setCode("PS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    /**
     * There should be no errors when facility site operating status is not PS.
     * There should be one error when facility site operating status is PS and release point operating status is not PS.
     * There should be no errors when facility site operating status is PS and release point operating status is PS.
     */
    @Test
    public void facilityOperatingStatusPSReleasePointStatusTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setStatusYear((short) 2019);
        ReleasePoint rp = new ReleasePoint();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        rp.setOperatingStatusCode(opStatCode);
        rp.setFacilitySite(testData);
        testData.getReleasePoints().add(rp);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("PS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STATUS_CODE.value()) && errorMap.get(ValidationField.RP_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        rp.getOperatingStatusCode().setCode("PS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    /**
     * There should be no errors when facility site operating status is not TS or PS.
     * There should be one error when facility site operating status is TS and emission unit operating status is not PS and not TS.
     * There should be no errors when facility site operating status is TS and emission unit operating status is PS or TS.
     */
    @Test
    public void facilityOperatingStatusTSEmissionUnitStatusTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setStatusYear((short) 2019);
        EmissionsUnit eu = new EmissionsUnit();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        eu.setOperatingStatusCode(opStatCode);
        eu.setFacilitySite(testData);
        testData.getEmissionsUnits().add(eu);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("TS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        eu.getOperatingStatusCode().setCode("PS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    /**
     * There should be no errors when facility site operating status is not TS or PS.
     * There should be one error when facility site operating status is PS and control operating status is not PS and not TS.
     * There should be no errors when facility site operating status is PS and control operating status is PS or TS.
     */
    @Test
    public void facilityOperatingStatusTSControlStatusTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setStatusYear((short) 2019);
        Control c = new Control();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        c.setOperatingStatusCode(opStatCode);
        c.setFacilitySite(testData);
        testData.getControls().add(c);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("TS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.CONTROL_STATUS_CODE.value()) && errorMap.get(ValidationField.CONTROL_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        c.getOperatingStatusCode().setCode("TS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    /**
     * There should be no errors when facility with status of not OP is a landfill or if the status year is > current cycle year
     */
    @Test
    public void facilityNotOperatingReportEmissionsPassTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setStatusYear((short) 2020);
        testData.getOperatingStatusCode().setCode("TS");
        
        FacilitySourceTypeCode sourceType = new FacilitySourceTypeCode();
        sourceType.setCode("104");
        testData.setFacilitySourceTypeCode(sourceType);
        testData.setStatusYear((short) 2000);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    /**
     * There should be no errors when facility site operating status is not TS or PS.
     * There should be one error when facility site operating status is PS and release point operating status is not PS and not TS.
     * There should be no errors when facility site operating status is PS and release point operating status is PS or TS.
     */
    @Test
    public void facilityOperatingStatusTSReleasePointStatusTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setStatusYear((short) 2019);
        ReleasePoint rp = new ReleasePoint();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        rp.setOperatingStatusCode(opStatCode);
        rp.setFacilitySite(testData);
        testData.getReleasePoints().add(rp);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("TS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_STATUS_CODE.value()) && errorMap.get(ValidationField.RP_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        rp.getOperatingStatusCode().setCode("PS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    /**
     * There should be errors when facility with status of not OP is not a landfill and the status year is <= current cycle year
     */
    @Test
    public void facilityNotOperatingReportEmissionsFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        FacilitySourceTypeCode sourceType = new FacilitySourceTypeCode();
        sourceType.setCode("100");
        testData.setFacilitySourceTypeCode(sourceType);
        testData.getOperatingStatusCode().setCode("TS");
        testData.setStatusYear((short) 2000);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_EMISSION_REPORTED.value()) && errorMap.get(ValidationField.FACILITY_EMISSION_REPORTED.value()).size() == 1);
    }
    
    /**
     * There should be errors when facility source type code has last inventory year < current report year
     */
    @Test
    public void facilitySourceTypeCodeLegacyFailTest() {
	      
        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        FacilitySourceTypeCode sourceType = new FacilitySourceTypeCode();
        sourceType.setCode("128");
        sourceType.setLastInventoryYear(1990);
        testData.setFacilitySourceTypeCode(sourceType);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_SOURCE_TYPE_CODE.value()) && errorMap.get(ValidationField.FACILITY_SOURCE_TYPE_CODE.value()).size() == 1);
    }
    
    private FacilitySite createBaseFacilitySite() {

        FipsStateCode countyStateCode = new FipsStateCode();
        countyStateCode.setCode("13");
        countyStateCode.setUspsCode("GA");
        
        FipsCounty countyCode = new FipsCounty();
        countyCode.setCode("13313");
        countyCode.setCountyCode("13");
        countyCode.setName("Whitfield");
        countyCode.setFipsStateCode(countyStateCode);
        
        FipsStateCode stateCode = new FipsStateCode();
        stateCode.setCode("13");
        stateCode.setUspsCode("GA");
        
        FipsStateCode mailingStateCode = new FipsStateCode();
        mailingStateCode.setCode("13");
        mailingStateCode.setUspsCode("GA");
        
        FacilitySite result = new FacilitySite();
        result.setEisProgramId("1");
        result.setStatusYear(null);
        result.setPostalCode("31750");
        result.setMailingPostalCode("31750");
        result.setCountyCode(countyCode);
        result.setStateCode(stateCode);
        
        EmissionsReport er = new EmissionsReport();
        er.setId(2L);
        er.setYear(new Short("2019"));
        er.setEisProgramId("1");
        result.setEmissionsReport(er);
        
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        result.setOperatingStatusCode(opStatCode);
        
        ContactTypeCode contactTypeCode = new ContactTypeCode();
        contactTypeCode.setCode("EI");
        
        FipsStateCode contactStateCode = new FipsStateCode();
        contactStateCode.setCode("13");
        contactStateCode.setUspsCode("GA");
        
        FipsStateCode contactMailingStateCode = new FipsStateCode();
        contactMailingStateCode.setCode("13");
        contactMailingStateCode.setUspsCode("GA");
        
        List<FacilitySiteContact> contactList = new ArrayList<FacilitySiteContact>();
        FacilitySiteContact contact = new FacilitySiteContact();
        
        contact.setType(contactTypeCode);
        contact.setFirstName("Jane");
        contact.setLastName("Doe");
        contact.setEmail("jane.doe@test.com");
        contact.setPhone("1234567890");
        contact.setPhoneExt("");
        contact.setStreetAddress("123 Test Street");
        contact.setCity("Fitzgerald");
        contact.setStateCode(contactStateCode);
        contact.setPostalCode("31750");
        contact.setMailingPostalCode("31750");
        contact.setCountyCode(countyCode);
        contactList.add(contact);
        
        result.setContacts(contactList);
        
        List<FacilityNAICSXref> naicsList = new ArrayList<FacilityNAICSXref>();
        FacilityNAICSXref facilityNaics = new FacilityNAICSXref();
      	
        NaicsCode naics = new NaicsCode();
        naics.setCode(332116);
        naics.setDescription("Metal Stamping");
        
        facilityNaics.setNaicsCode(naics);
        facilityNaics.setPrimaryFlag(true);
        naicsList.add(facilityNaics);
        
        result.setFacilityNAICS(naicsList);

        return result;
    }
    
}
