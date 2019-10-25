package gov.epa.cef.web.security.enforcer;

import gov.epa.cef.web.repository.ProgramIdRetriever;

import java.util.Collection;

public class ReviewerFacilityAccessEnforcer implements IFacilityAccessEnforcer {

    @Override
    public <T extends ProgramIdRetriever> void enforceEntities(Collection<Long> ids, Class<T> repositoryClazz) {

    }

    @Override
    public <T extends ProgramIdRetriever> void enforceEntity(Long id, Class<T> repositoryClazz) {

    }

    @Override
    public void enforceFacilitySite(Long id) {

    }

    @Override
    public void enforceFacilitySites(Collection<Long> ids) {

    }

    @Override
    public void enforceProgramId(String id) {

    }

    @Override
    public void enforceProgramIds(Collection<String> ids) {

    }
}
