package gov.epa.cef.web.security.enforcer;

import gov.epa.cef.web.exception.FacilityAccessException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.repository.ProgramIdRetriever;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class FacilityAccessEnforcer implements IFacilityAccessEnforcer {

    private final Collection<String> authorizedProgramIds;

    private final IProgramIdRepoLocator repoLocator;

    public FacilityAccessEnforcer(IProgramIdRepoLocator repoLocator,
                                  Collection<String> authorizedProgramIds) {

        this.repoLocator = repoLocator;
        this.authorizedProgramIds = authorizedProgramIds;
    }

    @Override
    public <T extends ProgramIdRetriever> void enforceEntities(Collection<Long> ids, Class<T> repoClazz) {

        ProgramIdRetriever repo = repoLocator.getProgramIdRepository(repoClazz);

        enforceProgramIds(ids.stream()
            .map(id -> {

                return repo.retrieveEisProgramIdById(id)
                    .orElseThrow(() -> {

                        String entity = repoClazz.getSimpleName().replace("Repository", "");
                        return new NotExistException(entity, id);
                    });
            })
            .collect(Collectors.toList()));
    }

    @Override
    public <T extends ProgramIdRetriever> void enforceEntity(Long id, Class<T> repoClazz) {

        ProgramIdRetriever repo = repoLocator.getProgramIdRepository(repoClazz);

        String programId = repo.retrieveEisProgramIdById(id)
            .orElseThrow(() -> {

                String entity = repoClazz.getSimpleName().replace("Repository", "");
                return new NotExistException(entity, id);
            });

        enforceProgramId(programId);
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
    public void enforceProgramId(String id) {

        enforceProgramIds(Collections.singletonList(id));
    }

    @Override
    public void enforceProgramIds(Collection<String> ids) {

        Collection<String> unauthorized = ids.stream()
            .filter(p -> this.authorizedProgramIds.contains(p) == false)
            .collect(Collectors.toList());

        if (unauthorized.size() > 0) {

            throw new FacilityAccessException(unauthorized);
        }
    }
}
