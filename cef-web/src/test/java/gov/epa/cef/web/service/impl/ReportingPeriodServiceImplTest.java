package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.ReportingPeriodBulkEntryDto;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;
import gov.epa.cef.web.service.dto.ReportingPeriodUpdateResponseDto;
import gov.epa.cef.web.service.mapper.ReportingPeriodMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReportingPeriodServiceImplTest extends BaseServiceTest {

    @Mock
    private ReportingPeriodRepository reportingPeriodRepo;

    @Mock
    private EmissionsReportRepository reportRepo;

    @Mock
    private ReportingPeriodMapper reportingPeriodMapper;

    @Mock
    private EmissionsReportStatusServiceImpl emissionsReportStatusService;

    @InjectMocks
    private ReportingPeriodServiceImpl reportingPeriodServiceImpl;

    private ReportingPeriod reportingPeriod;
    private ReportingPeriodDto reportingPeriodDto;
    private List<ReportingPeriodDto> reportingPeriodDtoList;
    private List<ReportingPeriodBulkEntryDto> reportingPeriodBulkDtoList;

    private EmissionsReport report2019;
    private EmissionsReport report2018;

    @Before
    public void init(){

        report2019 = new EmissionsReport();
        report2019.setYear(new Short("2019"));
        report2019.setEisProgramId("1");

        report2018 = new EmissionsReport();
        report2018.setYear(new Short("2018"));
        report2018.setEisProgramId("1");

        CodeLookupDto code = new CodeLookupDto();
        code.setCode("A");

        UnitMeasureCode lbUom = new UnitMeasureCode();
        lbUom.setCode("LB");
        lbUom.setDescription("POUNDS");
        lbUom.setUnitType("MASS");
        lbUom.setCalculationVariable("[lb]");

        reportingPeriod = new ReportingPeriod();
        reportingPeriod.setId(1L);
        reportingPeriod.setCalculationParameterValue(new BigDecimal(10));
        reportingPeriod.setCalculationParameterUom(lbUom);
        reportingPeriod.setEmissionsProcess(new EmissionsProcess());
        reportingPeriod.getEmissionsProcess().setEmissionsUnit(new EmissionsUnit());
        reportingPeriod.getEmissionsProcess().getEmissionsUnit().setFacilitySite(new FacilitySite());
        reportingPeriod.getEmissionsProcess().getEmissionsUnit().getFacilitySite().setEisProgramId("1");
        reportingPeriod.getEmissionsProcess().getEmissionsUnit().getFacilitySite().setEmissionsReport(report2019);

        List<ReportingPeriod> reportingPeriodList = new ArrayList<ReportingPeriod>();
        List<ReportingPeriod> emptyReportingPeriodList = new ArrayList<ReportingPeriod>();
        reportingPeriodList.add(reportingPeriod);
        when(reportingPeriodRepo.findById(1L)).thenReturn(Optional.of(reportingPeriod));
        when(reportingPeriodRepo.findById(2L)).thenReturn(Optional.empty());
        when(reportingPeriodRepo.findByEmissionsProcessId(1L)).thenReturn(reportingPeriodList);
        when(reportingPeriodRepo.findByFacilitySiteId(1L)).thenReturn(reportingPeriodList);
        when(reportingPeriodRepo.findByEmissionsProcessId(2L)).thenReturn(emptyReportingPeriodList);
        when(reportingPeriodRepo.retrieveByTypeIdentifierParentFacilityYear("A", "1", "1", "1", new Short("2018"))).thenReturn(reportingPeriodList);
        when(reportingPeriodRepo.save(reportingPeriod)).thenReturn(reportingPeriod);

        when(reportRepo.findFirstByEisProgramIdAndYearLessThanOrderByYearDesc("1", new Short("2019"))).thenReturn(Optional.of(report2018));

        reportingPeriodDto = new ReportingPeriodDto();
        reportingPeriodDto.setId(1L);
        reportingPeriodDtoList = new ArrayList<>();
        reportingPeriodDtoList.add(reportingPeriodDto);
        ReportingPeriodBulkEntryDto bulkDto = new ReportingPeriodBulkEntryDto();
        bulkDto.setReportingPeriodId(1L);
        bulkDto.setEmissionsProcessIdentifier("1");
        bulkDto.setUnitIdentifier("1");
        bulkDto.setReportingPeriodTypeCode(code);
        reportingPeriodBulkDtoList = new ArrayList<>();
        reportingPeriodBulkDtoList.add(bulkDto);
        when(reportingPeriodMapper.toDto(reportingPeriod)).thenReturn(reportingPeriodDto);
        when(reportingPeriodMapper.toDtoList(reportingPeriodList)).thenReturn(reportingPeriodDtoList);
        when(reportingPeriodMapper.toBulkEntryDtoList(reportingPeriodList)).thenReturn(reportingPeriodBulkDtoList);
        when(reportingPeriodMapper.toUpdateDtoFromBulkList(reportingPeriodBulkDtoList)).thenReturn(reportingPeriodDtoList);

        when(emissionsReportStatusService.resetEmissionsReportForEntity(ArgumentMatchers.anyList(), ArgumentMatchers.any())).thenReturn(null);

    }

    @Test
    public void update_Should_ReportingPeriodObject_When_ReportingPeriodExists(){
        ReportingPeriodUpdateResponseDto reportingPeriod = reportingPeriodServiceImpl.update(reportingPeriodDto);
        assertNotEquals(null, reportingPeriod);
    }

    @Test
    public void retrieveById_Should_Return_ReportingPeriodObject_When_ReportingPeriodExists(){
        ReportingPeriodDto reportingPeriod = reportingPeriodServiceImpl.retrieveById(1L);
        assertNotEquals(null, reportingPeriod);
    }

    @Test
    public void retrieveById_Should_Return_Null_When_ReportingPeriodDoesNotExist(){
        ReportingPeriodDto reportingPeriod = reportingPeriodServiceImpl.retrieveById(2L);
        assertEquals(null, reportingPeriod);
    }

    @Test
    public void retrieveById_Should_Return_Null_When_IDisNull(){
        ReportingPeriodDto reportingPeriod = reportingPeriodServiceImpl.retrieveById(null);
        assertEquals(null, reportingPeriod);
    }

    @Test
    public void retrieveByFacilitySiteId_Should_Return_ReportingPeriodList_When_ReportingPeriodsExist() {
        Collection<ReportingPeriodDto> reportingPeriodList = reportingPeriodServiceImpl.retrieveForEmissionsProcess(1L);
        assertNotEquals(null, reportingPeriodList);
    }

    @Test
    public void retrieveByFacilitySiteId_Should_Return_Empty_When_ReportingPeriodDoNotExist() {
        Collection<ReportingPeriodDto> reportingPeriodList = reportingPeriodServiceImpl.retrieveForEmissionsProcess(2L);
        assertEquals(new ArrayList<ReportingPeriod>(), reportingPeriodList);
    }

    @Test
    public void retrieveBulkEntryReportingPeriodsForFacilitySite_Should_Return_BulkEntryList_When_Valid() {
        Collection<ReportingPeriodBulkEntryDto> bulkList = reportingPeriodServiceImpl.retrieveBulkEntryReportingPeriodsForFacilitySite(1L);
        assertNotEquals(new ArrayList<ReportingPeriodBulkEntryDto>(), bulkList);
    }

    @Test
    public void bulkUpdatee_Should_Return_UpdateResponseList_When_Valid() {
        Collection<ReportingPeriodUpdateResponseDto> updateResponseList = reportingPeriodServiceImpl.bulkUpdate(reportingPeriodBulkDtoList);
        assertNotEquals(new ArrayList<ReportingPeriodBulkEntryDto>(), updateResponseList);
    }

}
