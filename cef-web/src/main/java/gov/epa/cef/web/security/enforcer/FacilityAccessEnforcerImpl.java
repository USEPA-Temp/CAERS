package gov.epa.cef.web.security.enforcer;

import com.google.common.base.Preconditions;
import gov.epa.cef.web.exception.FacilityAccessException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.repository.ProgramIdRetriever;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class FacilityAccessEnforcerImpl implements FacilityAccessEnforcer {

    private final Collection<Long> authorizedMasterIds;

    private final ProgramIdRepoLocator repoLocator;

    public FacilityAccessEnforcerImpl(ProgramIdRepoLocator repoLocator,
                                      Collection<Long> authorizedMasterIds) {

        this.repoLocator = repoLocator;
        this.authorizedMasterIds = authorizedMasterIds;
    }

    @Override
    public <T extends ProgramIdRetriever> void enforceEntities(Collection<Long> ids, Class<T> repoClazz) {

        ProgramIdRetriever repo = repoLocator.getProgramIdRepository(repoClazz);

        enforceMasterIds(ids.stream()
            .map(id -> {

                return repo.retrieveMasterFacilityRecordIdById(id)
                    .orElseThrow(() -> {

                        String entity = repoClazz.getSimpleName().replace("Repository", "");
                        return new NotExistException(entity, id);
                    });
            })
            .collect(Collectors.toList()));
    }

    @Override
    public <T extends ProgramIdRetriever> void enforceEntity(Long id, Class<T> repoClazz) {

    	Preconditions.checkArgument(id != null,"ID for %s repository can not be null.", repoClazz.getSimpleName());

        enforceEntities(Collections.singletonList(id), repoClazz);
    }

    @Override
    public void enforceFacilitySite(Long id) {

        enforceEntity(id, FacilitySiteRepository.class);
    }

    @Override
    public void enforceFacilitySites(Collection<Long> ids) {

        enforceEntities(ids, FacilitySiteRepository.class);
    }

    @Override
    public void enforceMasterId(Long id) {

        enforceMasterIds(Collections.singletonList(id));
    }

    @Override
    public Collection<Long> getAuthorizedMasterIds() {

        return Collections.unmodifiableCollection(this.authorizedMasterIds);
    }

    @Override
    public void enforceMasterIds(Collection<Long> ids) {

        Collection<String> unauthorized = ids.stream()
            .filter(p -> this.authorizedMasterIds.contains(p) == false)
            .map(p -> p.toString())
            .collect(Collectors.toList());

        if (unauthorized.size() > 0) {

            throw new FacilityAccessException(unauthorized);
        }
    }
}
