package gov.epa.cef.web.security;

import com.google.common.collect.Sets;
import gov.epa.cef.web.exception.FacilityAccessException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.ControlRepository;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.repository.ProgramIdRetriever;
import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.security.enforcer.FacilityAccessEnforcerImpl;
import gov.epa.cef.web.security.enforcer.ProgramIdRepoLocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FacilityAccessEnforcerTest extends BaseSecurityTest {

    @Mock
    ProgramIdRepoLocator programIdRepoLocator;

    @Mock
    ProgramIdRetriever programIdRetriever;

    @Test
    public void enforceEntities_Authorized() {

        when(programIdRetriever.retrieveEisProgramIdById(2L)).thenReturn(Optional.of("2"));
        when(programIdRetriever.retrieveEisProgramIdById(4L)).thenReturn(Optional.of("4"));

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceEntities(Arrays.asList(2L, 4L), EmissionsReportRepository.class);
    }

    @Test(expected = FacilityAccessException.class)
    public void enforceEntities_NotAuthorized() {

        when(programIdRetriever.retrieveEisProgramIdById(2L)).thenReturn(Optional.of("2"));
        when(programIdRetriever.retrieveEisProgramIdById(4L)).thenReturn(Optional.of("4"));
        when(programIdRetriever.retrieveEisProgramIdById(5L)).thenReturn(Optional.of("5"));

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceEntities(Arrays.asList(2L, 4L, 5L), ReportingPeriodRepository.class);
    }

    @Test(expected = NotExistException.class)
    public void enforceEntities_NotExist() {

        when(programIdRetriever.retrieveEisProgramIdById(2L)).thenReturn(Optional.of("2"));
        when(programIdRetriever.retrieveEisProgramIdById(4L)).thenReturn(Optional.of("4"));
        when(programIdRetriever.retrieveEisProgramIdById(6L)).thenReturn(Optional.empty());

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceEntities(Arrays.asList(2L, 4L, 6L), EmissionRepository.class);
    }

    @Test
    public void enforceEntity_Authorized() {

        when(programIdRetriever.retrieveEisProgramIdById(4L)).thenReturn(Optional.of("4"));

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceEntity(4L, ReleasePointRepository.class);
    }

    @Test(expected = FacilityAccessException.class)
    public void enforceEntity_NotAuthorized() {

        when(programIdRetriever.retrieveEisProgramIdById(5L)).thenReturn(Optional.of("5"));

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceEntity(5L, EmissionsUnitRepository.class);
    }

    @Test(expected = NotExistException.class)
    public void enforceEntity_NotExist() {

        when(programIdRetriever.retrieveEisProgramIdById(6L)).thenReturn(Optional.empty());

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceEntity(6L, ControlRepository.class);
    }


    @Test
    public void enforceFacilitySite_Authorized() {

        when(programIdRetriever.retrieveEisProgramIdById(4L)).thenReturn(Optional.of("4"));

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceFacilitySite(4L);
    }

    @Test(expected = FacilityAccessException.class)
    public void enforceFacilitySite_NotAuthorized() {

        when(programIdRetriever.retrieveEisProgramIdById(5L)).thenReturn(Optional.of("5"));

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceFacilitySite(5L);
    }

    @Test(expected = NotExistException.class)
    public void enforceFacilitySite_NotExist() {

        when(programIdRetriever.retrieveEisProgramIdById(6L)).thenReturn(Optional.empty());

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceFacilitySite(6L);
    }

    @Test
    public void enforceFacilitySites_Authorized() {

        when(programIdRetriever.retrieveEisProgramIdById(2L)).thenReturn(Optional.of("2"));
        when(programIdRetriever.retrieveEisProgramIdById(4L)).thenReturn(Optional.of("4"));

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceFacilitySites(Arrays.asList(2L, 4L));
    }

    @Test(expected = FacilityAccessException.class)
    public void enforceFacilitySites_NotAuthorized() {

        when(programIdRetriever.retrieveEisProgramIdById(2L)).thenReturn(Optional.of("2"));
        when(programIdRetriever.retrieveEisProgramIdById(4L)).thenReturn(Optional.of("4"));
        when(programIdRetriever.retrieveEisProgramIdById(5L)).thenReturn(Optional.of("5"));

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceFacilitySites(Arrays.asList(2L, 4L, 5L));
    }

    @Test(expected = NotExistException.class)
    public void enforceFacilitySites_NotExist() {

        when(programIdRetriever.retrieveEisProgramIdById(2L)).thenReturn(Optional.of("2"));
        when(programIdRetriever.retrieveEisProgramIdById(4L)).thenReturn(Optional.of("4"));
        when(programIdRetriever.retrieveEisProgramIdById(6L)).thenReturn(Optional.empty());

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceFacilitySites(Arrays.asList(2L, 4L, 6L));
    }

    @Test
    public void enforceProgramId_Authorized() {

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceProgramId("4");
    }

    @Test(expected = FacilityAccessException.class)
    public void enforceProgramId_NotAuthorized() {

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");

        createEnforcer(authorized).enforceProgramId("5");
    }

    @Test
    public void enforceProgramIds_Authorized() {

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");
        Set<String> goodcheck = Sets.newHashSet("2", "4");

        createEnforcer(authorized).enforceProgramIds(goodcheck);
    }

    @Test(expected = FacilityAccessException.class)
    public void enforceProgramIds_NotAuthorized() {

        Set<String> authorized = Sets.newHashSet("1", "2", "3", "4");
        Set<String> badcheck = Sets.newHashSet("2", "4", "5", "6", "7");

        createEnforcer(authorized).enforceProgramIds(badcheck);
    }

    private FacilityAccessEnforcerImpl createEnforcer(Collection<String> programIds) {

        when(this.programIdRepoLocator.getProgramIdRepository(any()))
            .thenReturn(this.programIdRetriever);

        return new FacilityAccessEnforcerImpl(this.programIdRepoLocator, programIds);
    }
}
