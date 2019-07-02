package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import gov.epa.cef.web.domain.EmissionsByFacilityAndCAS;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.repository.EmissionsByFacilityAndCASRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.dto.EmissionsByFacilityAndCASDto;
import gov.epa.cef.web.service.mapper.EmissionsByFacilityAndCASMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionServiceImplTest {
    
    @Mock
    private EmissionsByFacilityAndCASRepository emissionsByFacilityAndCASRepo;
    
    @Mock
    private EmissionsReportRepository emissionsReportRepo;
    
    @Mock
    EmissionsByFacilityAndCASMapper emissionsByFacilityAndCASMapper;
    
    @InjectMocks
    EmissionServiceImpl emissionServiceImpl;
    
    @Before
    public void init(){
        List<EmissionsReport> emptyEmissionsReportList = new ArrayList<EmissionsReport>();
        EmissionsReport emissionsReport = new EmissionsReport();
        emissionsReport.setYear(new Short("2019"));
        List<EmissionsReport> emissionsReportList2019 = new ArrayList<EmissionsReport>();
        emissionsReportList2019.add(emissionsReport);
        List<EmissionsByFacilityAndCAS> emptyEmissions = new ArrayList<EmissionsByFacilityAndCAS>();
        List<EmissionsByFacilityAndCAS> emissionsList = new ArrayList<EmissionsByFacilityAndCAS>();
        
        EmissionsByFacilityAndCAS emission1 = new EmissionsByFacilityAndCAS();
        emission1.setId(1L);
        emission1.setFacilityName("Test Facility 1");
        emission1.setFrsFacilityId("110015680799");
        emission1.setPollutantCasId("71-43-2");
        emission1.setPollutantName("Benzene");
        emission1.setYear(new Short("2019"));
        emission1.setReleasePointIdentifier("RP-1234");
        emission1.setReleasePointType("point");
        emission1.setApportionedEmissions(new BigDecimal("51.75"));
        emission1.setEmissionsUomCode("TON");
        
        EmissionsByFacilityAndCAS emission2 = new EmissionsByFacilityAndCAS();
        emission2.setId(2L);
        emission2.setFacilityName("Test Facility 1");
        emission2.setFrsFacilityId("110015680799");
        emission2.setPollutantCasId("71-43-2");
        emission2.setPollutantName("Benzene");
        emission2.setYear(new Short("2019"));
        emission2.setReleasePointIdentifier("RP-3456");
        emission2.setReleasePointType("point");
        emission2.setApportionedEmissions(new BigDecimal("765.15"));
        emission2.setEmissionsUomCode("TON");
        
        EmissionsByFacilityAndCAS emission3 = new EmissionsByFacilityAndCAS();
        emission3.setId(3L);
        emission3.setFacilityName("Test Facility 1");
        emission3.setFrsFacilityId("110015680799");
        emission3.setPollutantCasId("71-43-2");
        emission3.setPollutantName("Benzene");
        emission3.setYear(new Short("2019"));
        emission3.setReleasePointIdentifier("RP-2345");
        emission3.setReleasePointType("non-point");
        emission3.setApportionedEmissions(new BigDecimal("276.25"));
        emission3.setEmissionsUomCode("TON");
        
        EmissionsByFacilityAndCAS emission4 = new EmissionsByFacilityAndCAS();
        emission4.setId(4L);
        emission4.setFacilityName("Test Facility 1");
        emission4.setFrsFacilityId("110015680799");
        emission4.setPollutantCasId("71-43-2");
        emission4.setPollutantName("Benzene");
        emission4.setYear(new Short("2019"));
        emission4.setReleasePointIdentifier("RP-2345");
        emission4.setReleasePointType("non-point");
        emission4.setApportionedEmissions(new BigDecimal("114.86"));
        emission4.setEmissionsUomCode("TON");
        
        emissionsList.add(emission1);
        emissionsList.add(emission2);
        emissionsList.add(emission3);
        emissionsList.add(emission4);
        
        EmissionsByFacilityAndCASDto emissionsByFacilityAndCASDto = new EmissionsByFacilityAndCASDto();
        emissionsByFacilityAndCASDto.setFrsFacilityId("110015680799");
        emissionsByFacilityAndCASDto.setCasNumber("71-43-2");  
        emissionsByFacilityAndCASDto.setChemical("Benzene");
        emissionsByFacilityAndCASDto.setFacilityName("Test Facility 1");
        emissionsByFacilityAndCASDto.setYear(new Short("2019"));
        emissionsByFacilityAndCASDto.setUom("TON");
        
        when(emissionsReportRepo.findByFrsFacilityId("12345", Sort.by(Sort.Direction.DESC, "year"))).thenReturn(emptyEmissionsReportList);
        when(emissionsReportRepo.findByFrsFacilityId("110015680799", Sort.by(Sort.Direction.DESC, "year"))).thenReturn(emissionsReportList2019);
        when(emissionsByFacilityAndCASRepo.findByFrsFacilityIdAndPollutantCasIdAndYear("110015680799", "71-43-1", new Short("2019")))
            .thenReturn(emptyEmissions);
        when(emissionsByFacilityAndCASRepo.findByFrsFacilityIdAndPollutantCasIdAndYear("110015680799", "71-43-2", new Short("2019")))
        .thenReturn(emissionsList);
        when(emissionsByFacilityAndCASMapper.toDto(emission1)).thenReturn(emissionsByFacilityAndCASDto);
    }
    
    @Test
    public void findEmissionsByFacilityAndCAS_should_return_no_emissions_report_when_none_exists() {
        EmissionsByFacilityAndCASDto emissions = emissionServiceImpl.findEmissionsByFacilityAndCAS("12345", "71-43-2");
        assertEquals("NO_EMISSIONS_REPORT", emissions.getCode());
        assertEquals("No emission reports found for FRS Facility ID = 12345", emissions.getMessage());
        assertNull(emissions.getFrsFacilityId());
        assertNull(emissions.getCasNumber());
        assertNull(emissions.getYear());
        assertNull(emissions.getChemical());
        assertNull(emissions.getPointEmissions());
        assertNull(emissions.getNonPointEmissions());
        assertNull(emissions.getUom());
    }
    
    @Test
    public void findEmissionsByFacilityAndCAS_should_return_no_emissions_when_pollutant_was_not_reported() {
        EmissionsByFacilityAndCASDto emissions = emissionServiceImpl.findEmissionsByFacilityAndCAS("110015680799", "71-43-1");
        assertEquals("NO_EMISSIONS_REPORTED_FOR_CAS", emissions.getCode());
        assertEquals("There were no emissions reported for the CAS number 71-43-1 on the most recent emissions "
                + "report for FRS Facility ID = 110015680799", emissions.getMessage());
        assertNull(emissions.getFrsFacilityId());
        assertNull(emissions.getCasNumber());
        assertNull(emissions.getYear());
        assertNull(emissions.getChemical());
        assertNull(emissions.getPointEmissions());
        assertNull(emissions.getNonPointEmissions());
        assertNull(emissions.getUom());
    }
    
    @Test
    public void findEmissionsByFacilityAndCAS_should_return_calculated_emissions_when_pollutant_was_reported() {
        EmissionsByFacilityAndCASDto emissions = emissionServiceImpl.findEmissionsByFacilityAndCAS("110015680799", "71-43-2");
        assertEquals("EMISSIONS_FOUND", emissions.getCode());
        assertEquals("Found 816.90 point emissions and 391.11 non-point emissions for CAS number = 71-43-2 on the most recent "
                + "emissions report for FRS Facility ID = 110015680799", emissions.getMessage());
        assertEquals("110015680799", emissions.getFrsFacilityId());
        assertEquals("71-43-2", emissions.getCasNumber());
        assertEquals(new Short("2019"), emissions.getYear());
        assertEquals("Benzene", emissions.getChemical());
        assertEquals(new BigDecimal("816.90"), emissions.getPointEmissions());
        assertEquals(new BigDecimal("391.11"), emissions.getNonPointEmissions());
        assertEquals("TON", emissions.getUom());
    }
}
