package gov.epa.cef.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.Resources;
import gov.epa.cef.web.client.api.FrsApiClient;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilitySiteMapper;
import gov.epa.client.frs.iptquery.model.Contact;
import gov.epa.client.frs.iptquery.model.Naics;
import gov.epa.client.frs.iptquery.model.ProgramFacility;
import gov.epa.client.frs.iptquery.model.ProgramGIS;
import gov.epa.client.frs.iptquery.model.Unit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FacilitySiteServiceImplTest extends BaseServiceTest {

    private final ObjectMapper jsonMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Mock
    private FacilitySiteRepository facSiteRepo;

    @Mock
    private FacilitySiteMapper facilitySiteMapper;

    @InjectMocks
    private FacilitySiteServiceImpl facilitySiteServiceImpl;

    @Mock
    private FrsApiClient frsClient;

    @Before
    public void _init() throws Exception {

        FacilitySite testSite = new FacilitySite();
        when(facSiteRepo.findById(1L)).thenReturn(Optional.of(testSite));
        when(facSiteRepo.findById(2L)).thenReturn(Optional.empty());

        when(facSiteRepo.save(any())).then(AdditionalAnswers.returnsFirstArg());

        FacilitySiteDto facilitySiteDto = new FacilitySiteDto();
        when(facilitySiteMapper.toDto(testSite)).thenReturn(facilitySiteDto);

        List<FacilitySite> testSiteList = new ArrayList<FacilitySite>();
        testSiteList.add(testSite);
        List<FacilitySite> nullSiteList = null;
        when(facSiteRepo.findByEisProgramIdAndEmissionsReportId("XXXX", 1L)).thenReturn(testSiteList);
        when(facSiteRepo.findByEisProgramIdAndEmissionsReportId("XXXX", 2L)).thenReturn(new ArrayList<>());
        when(facSiteRepo.findByStateCode("GA")).thenReturn(testSiteList);
        when(facSiteRepo.findByStateCode("AK")).thenReturn(nullSiteList);
        when(facilitySiteMapper.toDtoList(nullSiteList)).thenReturn(null);

        when(frsClient.queryProgramFacility("554711")).thenReturn(
            hydrateFrsJsonObject("frs-queryProgramFacility.json", ProgramFacility.class));

        when(frsClient.queryProgramGis("554711")).thenReturn(
            hydrateFrsJsonObject("frs-queryProgramGis.json", ProgramGIS.class));

        when(frsClient.queryContacts("554711")).thenReturn(
            hydrateFrsJsonList("frs-queryContact.json", Contact.class));

        when(frsClient.queryNaics("554711")).thenReturn(
            hydrateFrsJsonList("frs-queryNaics.json", Naics.class));

        when(frsClient.queryEmissionsUnit("554711", null, null)).thenReturn(
            hydrateFrsJsonList("frs-queryEmissionsUnit.json", Unit.class));
    }

    @Test
    public void copyFacilityFromFrsTest() {

        EmissionsReport report = new EmissionsReport();
        report.setId(1234L);
        report.setEisProgramId("554711");

        FacilitySite facilitySite = this.facilitySiteServiceImpl.copyFromFrs(report);

        assertEquals(report.getEisProgramId(), facilitySite.getEisProgramId());
        assertEquals("ROME", facilitySite.getCity());
        assertEquals("FLOYD", facilitySite.getCounty());
        assertEquals("GA", facilitySite.getStateCode());

        assertNotNull(facilitySite.getOperatingStatusCode());
        assertEquals("OP", facilitySite.getOperatingStatusCode().getCode());

        assertEquals(new BigDecimal("34.252502"), facilitySite.getLatitude());
        assertEquals(new BigDecimal("-85.321754"), facilitySite.getLongitude());

        assertTrue(facilitySite.getContacts().isEmpty());

        assertEquals(1, facilitySite.getFacilityNAICS().size());
        FacilityNAICSXref naics = facilitySite.getFacilityNAICS().iterator().next();
        assertTrue(naics.isPrimaryFlag());
        assertEquals(321113L, naics.getNaicsCode().getCode().longValue());

        assertFalse(facilitySite.getEmissionsUnits().isEmpty());
        assertEquals(10, facilitySite.getEmissionsUnits().size());

        EmissionsUnit unit = facilitySite.getEmissionsUnits().get(9);
        assertEquals("108744513", unit.getUnitIdentifier());
        assertNotNull(unit.getUnitTypeCode());
        assertEquals("160", unit.getUnitTypeCode().getCode());
        assertNotNull(unit.getUnitOfMeasureCode());
        assertEquals("HP", unit.getUnitOfMeasureCode().getCode());
        assertEquals(new BigDecimal("64"), unit.getDesignCapacity());
        assertNotNull(unit.getOperatingStatusCode());
        assertEquals("OP", unit.getOperatingStatusCode().getCode());
    }

    @Test
    public void retrieveByEisProgramIdAndReportId_Should_Return_FacilitySiteObject_When_FacilitySiteExists() {

        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findByEisProgramIdAndReportId("XXXX", 1L);
        assertNotEquals(null, facilitySite);
    }

    @Test
    public void retrieveByEisProgramIdAndReportId_Should_Return_Null_When_FacilitySiteDoesNotExist() {

        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findByEisProgramIdAndReportId("XXXX", 2L);
        assertEquals(null, facilitySite);
    }

    @Test
    public void retrieveById_Should_Return_FacilitySiteObject_When_FacilitySiteExists() {

        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findById(1L);
        assertNotEquals(null, facilitySite);
    }

    @Test
    public void retrieveById_Should_Return_Null_When_FacilitySiteDoesNotExist() {

        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findById(2L);
        assertEquals(null, facilitySite);
    }

    @Test
    public void retrieveById_Should_Return_Null_When_FacilitySiteIDisNull() {

        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findById(null);
        assertEquals(null, facilitySite);
    }

    private <T> Collection<T> hydrateFrsJsonList(String resourceName, Class<T> clazz) throws Exception {

        String fullname = String.format("json/facilitySiteServiceImplTest/%s", resourceName);

        return this.jsonMapper.readValue(Resources.getResource(fullname),
            this.jsonMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    private <T> Optional<T> hydrateFrsJsonObject(String resourceName, Class<T> clazz) throws Exception {

        String fullname = String.format("json/facilitySiteServiceImplTest/%s", resourceName);

        return Optional.of(this.jsonMapper.readValue(Resources.getResource(fullname), clazz));
    }
}
