package gov.epa.cef.web.security.enforcer;

import gov.epa.cef.web.repository.ProgramIdRetriever;

import java.util.Collection;

public class ReviewerFacilityAccessEnforcerImpl implements FacilityAccessEnforcer {

    @Override
    public <T extends ProgramIdRetriever> void enforceEntities(Collection<Long> ids, Class<T> repositoryClazz) {

    }

    @Override
    public <T extends ProgramIdRetriever> void enforceEntity(Long id, Class<T> repositoryClazz) {
        //Reviewer should be able to access everything
    }

    @Override
    public void enforceFacilitySite(Long id) {
        //Reviewer should be able to access everything
    }

    @Override
    public void enforceFacilitySites(Collection<Long> ids) {
        //Reviewer should be able to access everything
    }

    @Override
    public void enforceProgramId(String id) {
        //Reviewer should be able to access everything
    }

    @Override
    public void enforceProgramIds(Collection<String> ids) {
        //Reviewer should be able to access everything
    }
}
