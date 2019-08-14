package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.FacilitySourceTypeCode;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionsReportServiceImplTest {
    
    @Mock
    private EmissionsReportRepository erRepo;
    
    @Mock
    private EmissionsReportMapper emissionsReportMapper;
    
    @InjectMocks
    private EmissionsReportServiceImpl emissionsReportServiceImpl;
    
    
    private EmissionsReportDto emissionsReportDto;
    
    private List<EmissionsReportDto> emissionsReportDtoList;
    
    @Before
    public void init(){
        EmissionsReport emissionsReport = new EmissionsReport();
        EmissionsReport previousEmissionsReport = createHydratedEmissionsReport();
        List<EmissionsReport> previousEmissionsReportList = new ArrayList<EmissionsReport>();
        previousEmissionsReportList.add(previousEmissionsReport);
        List<EmissionsReport> emissionsReportList = new ArrayList<EmissionsReport>();
        List<EmissionsReport> emptyReportList = new ArrayList<EmissionsReport>();
        emissionsReportList.add(emissionsReport);
        List<EmissionsReport> emptyEmissionsReportList = new ArrayList<EmissionsReport>();
        when(erRepo.findById(1L)).thenReturn(Optional.of(emissionsReport));
        when(erRepo.findById(2L)).thenReturn(Optional.empty());
        when(erRepo.findByEisProgramId("XXXX")).thenReturn(emissionsReportList);
        when(erRepo.findByEisProgramId("YYYY")).thenReturn(emptyReportList);
        when(erRepo.findByEisProgramId("XXXX", new Sort(Sort.Direction.DESC, "year"))).thenReturn(emissionsReportList);
        when(erRepo.findByEisProgramId("ABC", new Sort(Sort.Direction.DESC, "year"))).thenReturn(previousEmissionsReportList);
        when(erRepo.findByEisProgramId("DEF", new Sort(Sort.Direction.DESC, "year"))).thenReturn(emptyEmissionsReportList);

        emissionsReportDto=new EmissionsReportDto();
        emissionsReportDtoList=new ArrayList<>();
        when(emissionsReportMapper.toDto(emissionsReport)).thenReturn(emissionsReportDto);
        when(emissionsReportMapper.toDtoList(emissionsReportList)).thenReturn(emissionsReportDtoList);
    }

	@Test
    public void findById_Should_Return_EmissionsReportObject_When_EmissionsReportExists(){
        EmissionsReportDto emissionsReport = emissionsReportServiceImpl.findById(1L);
        assertEquals(emissionsReportDto, emissionsReport);
    }
    
    @Test
    public void findById_Should_Return_Null_When_EmissionsReportNotExist(){
        EmissionsReportDto emissionsReport = emissionsReportServiceImpl.findById(2L);
        assertEquals(null, emissionsReport);
    }
    
    @Test
    public void findById_Should_Return_Null_When_IDisNull(){
        EmissionsReportDto emissionsReport = emissionsReportServiceImpl.findById(null);
        assertEquals(null, emissionsReport);
    }
    
    @Test
    public void findByFacilityId_Should_Return_ReportList_WhenReportExist() {
        Collection<EmissionsReportDto> emissionsReportList = emissionsReportServiceImpl.findByFacilityEisProgramId("XXXX");
        assertEquals(emissionsReportDtoList, emissionsReportList);
    }
    
//    @Test
//    public void findByFacilityId_Should_ReturnReportListWithCurrentYear() {
//        Collection<EmissionsReportDto> emissionsReportList = emissionsReportServiceImpl.findByFacilityEisProgramId("XXXX", true);
//        assertEquals(2, emissionsReportList.size());
//        
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        short currentYear = (short) calendar.get(Calendar.YEAR);
//        Object[] array = emissionsReportList.toArray();
//        EmissionsReportDto dto = (EmissionsReportDto) array[0];
//        assertEquals(dto.getYear().shortValue(), currentYear);
//    }
    
    @Test
    public void findByFacilityId_Should_Return_Empty_WhenReportsDoNotExist() {
        Collection<EmissionsReportDto> emissionsReportList = emissionsReportServiceImpl.findByFacilityEisProgramId("YYYY");
        assertEquals(0, emissionsReportList.size());
    }
    
    @Test
    public void findMostRecentByFacilityEisProgramId_Should_ReturnTheLatestEmissionsReportForAFacility_WhenValidFacilityEisProgramIdPassed() {
        EmissionsReportDto emissionsReport = emissionsReportServiceImpl.findMostRecentByFacilityEisProgramId("XXXX");
        assertNotEquals(null, emissionsReport);
    }
    
    @Test
    public void createEmissionReportCopy_Should_ReturnValidDeepCopy_WhenValidFacilityAndYearPassed() {
    	EmissionsReport originalEmissionsReport = createHydratedEmissionsReport();
    	EmissionsReport emissionsReportCopy = emissionsReportServiceImpl.createEmissionReportCopy("ABC", 2020);
    	assertEquals(ReportStatus.IN_PROGRESS, emissionsReportCopy.getStatus());
    	assertEquals(ValidationStatus.UNVALIDATED, emissionsReportCopy.getValidationStatus());
    	assertEquals("2020", emissionsReportCopy.getYear().toString());
    	assertNotEquals(originalEmissionsReport.getId(), emissionsReportCopy.getId());

    	FacilitySite originalFacilitySite = originalEmissionsReport.getFacilitySites().iterator().next();
    	FacilitySite copyFacilitySite = emissionsReportCopy.getFacilitySites().iterator().next();
    	assertEquals(originalFacilitySite.getAltSiteIdentifier(), copyFacilitySite.getAltSiteIdentifier());
    	assertNotEquals(originalFacilitySite.getId(), copyFacilitySite.getId());
    }
    

    @Test
    public void createEmissionReportCopy_Should_ReturnNull_WhenPreviousDoesNotExist() {
    	EmissionsReport nullEmissionsReportCopy = emissionsReportServiceImpl.createEmissionReportCopy("DEF", 2020);
    	assertEquals(null, nullEmissionsReportCopy);
    }
    

    
    private EmissionsReport createHydratedEmissionsReport() {
    	EmissionsReport er = new EmissionsReport();
    	er.setAgencyCode("");
    	er.setEisProgramId("");
    	er.setFrsFacilityId("");
    	er.setId(1L);
    	er.setStatus(ReportStatus.APPROVED);
    	er.setValidationStatus(ValidationStatus.PASSED);
    	er.setYear((short) 2020);
    	
    	HashSet<FacilitySite> facilitySites = new HashSet<FacilitySite>();
    	FacilitySite fs = new FacilitySite();
    	fs.setAltSiteIdentifier("ALTID");
    	fs.setCity("Raleigh");
    	fs.setDescription("Facility Description");
    	fs.setEisProgramId("EISID");
    	fs.setEmissionsReport(er);
    	fs.setId(1L);
    	fs.setLatitude((double)2.5);
    	fs.setLongitude((double)2.5);

    	FacilitySourceTypeCode fstc = new FacilitySourceTypeCode();
    	fstc.setCode("Source Type Code");
    	fstc.setDescription("Source Type Desc");
    	fs.setFacilitySourceTypeCode(fstc);

    	HashSet<FacilitySiteContact> contacts = new HashSet<FacilitySiteContact>();
    	FacilitySiteContact fsc = new FacilitySiteContact();
    	fsc.setCity("Raleigh");
    	fsc.setId(1L);
    	fsc.setFacilitySite(fs);
    	fsc.setFirstName("John");
    	fsc.setLastName("Doe");
    	contacts.add(fsc);
    	fs.setContacts(contacts);

    	HashSet<FacilityNAICSXref> facilityNAICS = new HashSet<FacilityNAICSXref>();
    	FacilityNAICSXref xref = new FacilityNAICSXref();
    	xref.setFacilitySite(fs);
    	xref.setId(1L);
    	NaicsCode naics = new NaicsCode();
    	naics.setCode(123);
    	naics.setDescription("ABCDE");
    	xref.setNaicsCode(naics);
    	fs.setFacilityNAICS(facilityNAICS);
    	
    	HashSet<ReleasePoint> releasePoints = new HashSet<ReleasePoint>();
    	ReleasePoint rp = new ReleasePoint();
    	rp.setId(1L);
    	rp.setComments("Comments");
    	releasePoints.add(rp);
    	fs.setReleasePoints(releasePoints);
    	
    	HashSet<Control> controls = new HashSet<Control>();
    	Control control = new Control();
    	control.setId(1L);
    	control.setFacilitySite(fs);

    	HashSet<ControlAssignment> assignments = new HashSet<ControlAssignment>();
    	ControlAssignment ca = new ControlAssignment();
    	ca.setControl(control);
    	ca.setId(1L);
    	ca.setReleasePoint(rp);
    	ca.setDescription("Control Assignment");
    	ControlPath cp = new ControlPath();
    	cp.setAssignment(ca);
    	cp.setId(1L);
    	ca.setControlPath(cp);
    	assignments.add(ca);
    	
    	control.setAssignments(assignments);
    	controls.add(control);
    	fs.setControls(controls);
    	
    	
    	HashSet<EmissionsUnit> units = new HashSet<EmissionsUnit>();
    	EmissionsUnit eu = new EmissionsUnit();
    	eu.setId(1L);
    	eu.setComments("Test Unit");
    	eu.setFacilitySite(fs);
    	
    	HashSet<EmissionsProcess> processes = new HashSet<EmissionsProcess>();
    	EmissionsProcess ep = new EmissionsProcess();
    	ep.setId(1L);
    	ep.setEmissionsUnit(eu);
    	ep.setComments("Test Process Comments");
    	
    	HashSet<ReportingPeriod> reportingPeriods = new HashSet<ReportingPeriod>();
    	ReportingPeriod repPer = new ReportingPeriod();
    	repPer.setId(1L);
    	repPer.setComments("Reporting Period Comments");
    	repPer.setEmissionsProcess(ep);
    	
    	HashSet<Emission> emissions = new HashSet<Emission>();
    	Emission e = new Emission();
    	e.setId(1L);
    	e.setComments("Test Emission Comments");
    	emissions.add(e);
    	repPer.setEmissions(emissions);
    	
    	reportingPeriods.add(repPer);
    	ep.setReportingPeriods(reportingPeriods);
    	processes.add(ep);
    	eu.setEmissionsProcesses(processes);
    	units.add(eu);
    	fs.setEmissionsUnits(units);
    	
    	facilitySites.add(fs);
    	er.setFacilitySites(facilitySites);
    	
    	return er;
	}
}
