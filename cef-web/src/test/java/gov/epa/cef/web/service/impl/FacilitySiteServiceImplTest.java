package gov.epa.cef.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.Resources;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilitySiteMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
        when(facSiteRepo.findByEmissionsReportId(1L)).thenReturn(testSiteList);
        when(facSiteRepo.findByEmissionsReportId(2L)).thenReturn(new ArrayList<>());
        when(facSiteRepo.findByStateCode("GA")).thenReturn(testSiteList);
        when(facSiteRepo.findByStateCode("AK")).thenReturn(nullSiteList);
        when(facilitySiteMapper.toDtoList(nullSiteList)).thenReturn(null);

    }

    @Test
    public void retrieveByEisProgramIdAndReportId_Should_Return_FacilitySiteObject_When_FacilitySiteExists() {

        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findByReportId(1L);
        assertNotEquals(null, facilitySite);
    }

    @Test
    public void retrieveByEisProgramIdAndReportId_Should_Return_Null_When_FacilitySiteDoesNotExist() {

        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findByReportId(2L);
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
