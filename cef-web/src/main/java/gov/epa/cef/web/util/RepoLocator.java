package gov.epa.cef.web.util;

import gov.epa.cef.web.repository.ProgramIdRetriever;
import gov.epa.cef.web.repository.ReportIdRetriever;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RepoLocator {

    private final ApplicationContext applicationContext;

    public RepoLocator(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    public <T extends ProgramIdRetriever> ProgramIdRetriever getProgramIdRepository(Class<T> clazz) {

        return this.applicationContext.getBean(clazz);
    }

    public <T extends ReportIdRetriever> ReportIdRetriever getReportIdRepository(Class<T> clazz) {

        return this.applicationContext.getBean(clazz);
    }
}
