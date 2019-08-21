package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.CalculationMaterialCode;
import gov.epa.cef.web.domain.CalculationMethodCode;
import gov.epa.cef.web.domain.CalculationParameterTypeCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.repository.CalculationMaterialCodeRepository;
import gov.epa.cef.web.repository.CalculationMethodCodeRepository;
import gov.epa.cef.web.repository.CalculationParameterTypeCodeRepository;
import gov.epa.cef.web.repository.OperatingStatusCodeRepository;
import gov.epa.cef.web.repository.PollutantRepository;
import gov.epa.cef.web.repository.ReportingPeriodCodeRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.PollutantDto;
import gov.epa.cef.web.service.mapper.LookupEntityMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LookupServiceImplTest {
    
    @Mock
    private CalculationMaterialCodeRepository materialCodeRepo;

    @Mock
    private CalculationMethodCodeRepository methodCodeRepo;

    @Mock
    private CalculationParameterTypeCodeRepository paramTypeCodeRepo;

    @Mock
    private OperatingStatusCodeRepository operatingStatusRepo;

    @Mock
    private PollutantRepository pollutantRepo;

    @Mock
    private ReportingPeriodCodeRepository periodCodeRepo;

    @Mock
    private UnitMeasureCodeRepository uomRepo;

    @Mock
    private LookupEntityMapper lookupMapper;
    
    @InjectMocks
    private LookupServiceImpl lookupServiceImpl;
    
    private CodeLookupDto codeLookupDto;
    private List<CodeLookupDto> codeLookupDtoList;
    private PollutantDto pollutantDto;
    
    @Before
    public void init(){
        CalculationMaterialCode materialCode = new CalculationMaterialCode();
        List<CalculationMaterialCode> materialCodeList = new ArrayList<CalculationMaterialCode>();
        materialCodeList.add(materialCode);
        when(materialCodeRepo.findById("1")).thenReturn(Optional.of(materialCode));
        when(materialCodeRepo.findById("2")).thenReturn(Optional.empty());
        when(materialCodeRepo.findAll()).thenReturn(materialCodeList);
        
        CalculationMethodCode methodCode = new CalculationMethodCode();
        List<CalculationMethodCode> methodCodeList = new ArrayList<CalculationMethodCode>();
        methodCodeList.add(methodCode);
        when(methodCodeRepo.findById("1")).thenReturn(Optional.of(methodCode));
        when(methodCodeRepo.findById("2")).thenReturn(Optional.empty());
        when(methodCodeRepo.findAll()).thenReturn(methodCodeList);
        
        CalculationParameterTypeCode paramTypeCode = new CalculationParameterTypeCode();
        List<CalculationParameterTypeCode> paramTypeCodeList = new ArrayList<CalculationParameterTypeCode>();
        paramTypeCodeList.add(paramTypeCode);
        when(paramTypeCodeRepo.findById("1")).thenReturn(Optional.of(paramTypeCode));
        when(paramTypeCodeRepo.findById("2")).thenReturn(Optional.empty());
        when(paramTypeCodeRepo.findAll()).thenReturn(paramTypeCodeList);
        
        OperatingStatusCode operatingStatus = new OperatingStatusCode();
        List<OperatingStatusCode> operatingStatusList = new ArrayList<OperatingStatusCode>();
        operatingStatusList.add(operatingStatus);
        when(operatingStatusRepo.findById("1")).thenReturn(Optional.of(operatingStatus));
        when(operatingStatusRepo.findById("2")).thenReturn(Optional.empty());
        when(operatingStatusRepo.findAll()).thenReturn(operatingStatusList);
        
        Pollutant pollutant = new Pollutant();
        List<Pollutant> pollutantList = new ArrayList<Pollutant>();
        pollutantList.add(pollutant);
        when(pollutantRepo.findById("1")).thenReturn(Optional.of(pollutant));
        when(pollutantRepo.findById("2")).thenReturn(Optional.empty());
        when(pollutantRepo.findAll()).thenReturn(pollutantList);
        
        ReportingPeriodCode periodCode = new ReportingPeriodCode();
        List<ReportingPeriodCode> periodCodeList = new ArrayList<ReportingPeriodCode>();
        periodCodeList.add(periodCode);
        when(periodCodeRepo.findById("1")).thenReturn(Optional.of(periodCode));
        when(periodCodeRepo.findById("2")).thenReturn(Optional.empty());
        when(periodCodeRepo.findAll()).thenReturn(periodCodeList);
        
        UnitMeasureCode uom = new UnitMeasureCode();
        List<UnitMeasureCode> uomList = new ArrayList<UnitMeasureCode>();
        uomList.add(uom);
        when(uomRepo.findById("1")).thenReturn(Optional.of(uom));
        when(uomRepo.findById("2")).thenReturn(Optional.empty());
        when(uomRepo.findAll()).thenReturn(uomList);
        
        codeLookupDto = new CodeLookupDto();
        codeLookupDtoList = new ArrayList<>();
        pollutantDto = new PollutantDto();
        when(lookupMapper.toDto(materialCode)).thenReturn(codeLookupDto);
        when(lookupMapper.toDto(methodCode)).thenReturn(codeLookupDto);
        when(lookupMapper.toDto(paramTypeCode)).thenReturn(codeLookupDto);
        when(lookupMapper.toDto(operatingStatus)).thenReturn(codeLookupDto);
        when(lookupMapper.pollutantToDto(pollutant)).thenReturn(pollutantDto);
        when(lookupMapper.reportingPeriodCodeToDto(periodCode)).thenReturn(codeLookupDto);
        when(lookupMapper.toDto(uom)).thenReturn(codeLookupDto);
    }
    
    @Test
    public void retrieveCalcMaterialCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveCalcMaterialCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveCalcMaterialCodeEntityByCode_Should_Return_CodeObject_When_CodeExists(){
        CalculationMaterialCode result = lookupServiceImpl.retrieveCalcMaterialCodeEntityByCode("1");
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveCalcMaterialCodeEntityByCode_Should_Return_Null_When_CodeDoesNotExist(){
        CalculationMaterialCode result = lookupServiceImpl.retrieveCalcMaterialCodeEntityByCode("2");
        assertEquals(null, result);
    }
    
    @Test
    public void retrieveCalcMethodCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveCalcMethodCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveCalcParamTypeCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveCalcParamTypeCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveCalcParamTypeCodeEntityByCode_Should_Return_CodeObject_When_CodeExists(){
        CalculationParameterTypeCode result = lookupServiceImpl.retrieveCalcParamTypeCodeEntityByCode("1");
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveCalcParamTypeCodeEntityByCode_Should_Return_Null_When_CodeDoesNotExist(){
        CalculationParameterTypeCode result = lookupServiceImpl.retrieveCalcParamTypeCodeEntityByCode("2");
        assertEquals(null, result);
    }
    
    @Test
    public void retrieveOperatingStatusCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveOperatingStatusCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveOperatingStatusCodeEntityByCode_Should_Return_CodeObject_When_CodeExists(){
        OperatingStatusCode result = lookupServiceImpl.retrieveOperatingStatusCodeEntityByCode("1");
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveOperatingStatusCodeEntityByCode_Should_Return_Null_When_CodeDoesNotExist(){
        OperatingStatusCode result = lookupServiceImpl.retrieveOperatingStatusCodeEntityByCode("2");
        assertEquals(null, result);
    }
    
    @Test
    public void retrievePollutants_Should_Return_PollutantDtoList(){
        List<PollutantDto> result = lookupServiceImpl.retrievePollutants();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveReportingPeriodCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveReportingPeriodCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveReportingPeriodCodeEntityByCode_Should_Return_CodeObject_When_CodeExists(){
        ReportingPeriodCode result = lookupServiceImpl.retrieveReportingPeriodCodeEntityByCode("1");
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveReportingPeriodCodeEntityByCode_Should_Return_Null_When_CodeDoesNotExist(){
        ReportingPeriodCode result = lookupServiceImpl.retrieveReportingPeriodCodeEntityByCode("2");
        assertEquals(null, result);
    }
    
    @Test
    public void retrieveUnitMeasureCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveUnitMeasureCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveUnitMeasureCodeEntityByCode_Should_Return_CodeObject_When_CodeExists(){
        UnitMeasureCode result = lookupServiceImpl.retrieveUnitMeasureCodeEntityByCode("1");
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveUnitMeasureCodeEntityByCode_Should_Return_Null_When_CodeDoesNotExist(){
        UnitMeasureCode result = lookupServiceImpl.retrieveUnitMeasureCodeEntityByCode("2");
        assertEquals(null, result);
    }
}
