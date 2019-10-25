package gov.epa.cef.web.security.enforcer;

import gov.epa.cef.web.repository.ProgramIdRetriever;

public interface IProgramIdRepoLocator {

    <T extends ProgramIdRetriever> T getProgramIdRepository(Class<T> clazz);
}
