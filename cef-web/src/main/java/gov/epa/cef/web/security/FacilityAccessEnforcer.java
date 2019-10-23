package gov.epa.cef.web.security;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.exception.FacilityAccessException;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.dto.FacilitySiteAware;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FacilityAccessEnforcer {

    private final ApplicationUser applicationUser;

    private final FacilitySiteRepository facilitySiteRepository;

    FacilityAccessEnforcer(FacilitySiteRepository facilitySiteRepository,
                           ApplicationUser applicationUser) {

        this.applicationUser = applicationUser;
        this.facilitySiteRepository = facilitySiteRepository;
    }

    public <T extends FacilitySiteAware> void enforce(Collection<T> facilityAwarables) {

        if (isUserReviewer()) {
            return;
        }

        enforceProgramIds(facilityAwarables.stream()
            .map(f -> this.facilitySiteRepository.findEisProgramIdById(f.getFacilitySiteId()))
            .collect(Collectors.toList()));
    }

    public void enforce(Long facilitySiteId) {

        if (isUserReviewer()) {
            return;
        }

        enforceProgramIds(Collections.singletonList(
            this.facilitySiteRepository.findEisProgramIdById(facilitySiteId)));
    }

    public void enforce(FacilitySiteAware facilitySiteAware) {

        enforce(facilitySiteAware.getFacilitySiteId());
    }

    public void enforceProgramId(String eisProgramId) {

        enforceProgramIds(Collections.singletonList(eisProgramId));
    }

    public void enforceProgramIds(Collection<String> programIds) {

        if (isUserReviewer()) {
            return;
        }

        List<String> authorized = getUserFacilityProgramIds();

        Collection<String> unauthorized = programIds.stream()
            .filter(p -> authorized.contains(p) == false)
            .collect(Collectors.toList());

        if (unauthorized.size() > 0) {

            throw new FacilityAccessException(unauthorized);
        }
    }

    private List<String> getUserFacilityProgramIds() {

        return this.applicationUser.getAuthorities().stream()
            .filter(r -> r.getAuthority().startsWith(SecurityService.FacilityRolePrefix))
            .map(r -> r.getAuthority().substring(SecurityService.FacilityRolePrefix.length()))
            .collect(Collectors.toList());
    }

    private boolean isUserReviewer() {

        return this.applicationUser.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(s -> AppRole.RoleType.REVIEWER.grantedRoleName().equals(s));
    }
}
