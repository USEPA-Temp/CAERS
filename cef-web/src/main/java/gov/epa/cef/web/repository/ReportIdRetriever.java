package gov.epa.cef.web.repository;

import java.util.Optional;

public interface ReportIdRetriever {

    Optional<Long> retrieveEmissionsReportById(Long id);
}
