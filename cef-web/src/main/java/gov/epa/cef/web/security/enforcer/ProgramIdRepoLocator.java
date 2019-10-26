package gov.epa.cef.web.security.enforcer;

import gov.epa.cef.web.repository.ProgramIdRetriever;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ProgramIdRepoLocator {

    private final ApplicationContext applicationContext;

    public ProgramIdRepoLocator(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    public <T extends ProgramIdRetriever> ProgramIdRetriever getProgramIdRepository(Class<T> clazz) {

        return this.applicationContext.getBean(clazz);
    }
}
