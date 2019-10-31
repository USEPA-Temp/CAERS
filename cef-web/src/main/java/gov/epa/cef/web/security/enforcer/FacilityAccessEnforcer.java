package gov.epa.cef.web.security.enforcer;

import gov.epa.cef.web.repository.ProgramIdRetriever;

import java.util.Collection;

public interface FacilityAccessEnforcer {

    <T extends ProgramIdRetriever> void enforceEntities(Collection<Long> ids, Class<T> repositoryClazz);

    <T extends ProgramIdRetriever> void enforceEntity(Long id, Class<T> repositoryClazz);

    void enforceFacilitySite(Long id);

    void enforceFacilitySites(Collection<Long> ids);

    void enforceProgramId(String id);

    void enforceProgramIds(Collection<String> ids);
}
