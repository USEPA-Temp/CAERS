package gov.epa.cef.web.repository;

import java.util.Optional;

public interface ProgramIdRetriever {

    Optional<String> retrieveEisProgramIdById(Long id);
}
