package gov.epa.cef.web.repository;

import java.util.Optional;

public interface ProgramIdRetriever {

    Optional<Long> retrieveMasterFacilityRecordIdById(Long id);
}
