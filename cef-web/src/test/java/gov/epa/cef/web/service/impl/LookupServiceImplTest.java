package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.CalculationMaterialCode;
import gov.epa.cef.web.domain.CalculationParameterTypeCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.repository.CalculationMaterialCodeRepository;
import gov.epa.cef.web.repository.CalculationParameterTypeCodeRepository;
import gov.epa.cef.web.repository.OperatingStatusCodeRepository;
import gov.epa.cef.web.repository.ReportingPeriodCodeRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.mapper.LookupEntityMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LookupServiceImplTest {
    
    @Mock
    private CalculationMaterialCodeRepository materialCodeRepo;

    @Mock
    private CalculationParameterTypeCodeRepository paramTypeCodeRepo;

    @Mock
    private OperatingStatusCodeRepository operatingStatusRepo;

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
    
    @Before
    public void init(){
        CalculationMaterialCode materialCode = new CalculationMaterialCode();
        List<CalculationMaterialCode> materialCodeList = new ArrayList<CalculationMaterialCode>();
        materialCodeList.add(materialCode);
        when(materialCodeRepo.findAll()).thenReturn(materialCodeList);
        
        CalculationParameterTypeCode paramTypeCode = new CalculationParameterTypeCode();
        List<CalculationParameterTypeCode> paramTypeCodeList = new ArrayList<CalculationParameterTypeCode>();
        paramTypeCodeList.add(paramTypeCode);
        when(paramTypeCodeRepo.findAll()).thenReturn(paramTypeCodeList);
        
        OperatingStatusCode operatingStatus = new OperatingStatusCode();
        List<OperatingStatusCode> operatingStatusList = new ArrayList<OperatingStatusCode>();
        operatingStatusList.add(operatingStatus);
        when(operatingStatusRepo.findAll()).thenReturn(operatingStatusList);
        
        ReportingPeriodCode periodCode = new ReportingPeriodCode();
        List<ReportingPeriodCode> periodCodeList = new ArrayList<ReportingPeriodCode>();
        periodCodeList.add(periodCode);
        when(periodCodeRepo.findAll()).thenReturn(periodCodeList);
        
        UnitMeasureCode uom = new UnitMeasureCode();
        List<UnitMeasureCode> uomList = new ArrayList<UnitMeasureCode>();
        uomList.add(uom);
        when(uomRepo.findAll()).thenReturn(uomList);
        
        codeLookupDto = new CodeLookupDto();
        codeLookupDtoList=new ArrayList<>();
        when(lookupMapper.toDto(materialCode)).thenReturn(codeLookupDto);
        when(lookupMapper.toDto(paramTypeCode)).thenReturn(codeLookupDto);
        when(lookupMapper.toDto(operatingStatus)).thenReturn(codeLookupDto);
        when(lookupMapper.reportingPeriodCodeToDto(periodCode)).thenReturn(codeLookupDto);
        when(lookupMapper.toDto(uom)).thenReturn(codeLookupDto);
    }

    @Test
    public void retrieveCalcMaterialCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveCalcMaterialCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveCalcParamTypeCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveCalcParamTypeCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveOperatingStatusCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveOperatingStatusCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveReportingPeriodCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveReportingPeriodCodes();
        assertNotEquals(null, result);
    }
    
    @Test
    public void retrieveUnitMeasureCodes_Should_Return_CodeLookupDtoList(){
        List<CodeLookupDto> result = lookupServiceImpl.retrieveUnitMeasureCodes();
        assertNotEquals(null, result);
    }
}
