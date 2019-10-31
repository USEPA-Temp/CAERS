package gov.epa.cef.web.security;


import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.security.enforcer.FacilityAccessEnforcerImpl;
import gov.epa.cef.web.security.enforcer.FacilityAccessEnforcer;
import gov.epa.cef.web.security.enforcer.ProgramIdRepoLocator;
import gov.epa.cef.web.security.enforcer.ReviewerFacilityAccessEnforcerImpl;
import gov.epa.cef.web.service.RegistrationService;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    private static final String FacilityRolePrefix = "{EIS}";

    private final CacheManager cacheManager;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProgramIdRepoLocator programIdRepoLocator;

    private final RegistrationService registrationService;

    @Autowired
    SecurityService(RegistrationService registrationService,
                    CacheManager cacheManager,
                    ProgramIdRepoLocator programIdRepoLocator) {

        this.registrationService = registrationService;

        this.cacheManager = cacheManager;

        this.programIdRepoLocator = programIdRepoLocator;
    }

    /**
     * Add the provided user to the security context
     *
     * @param appUser
     */
    public void addUserToSecurityContext(ApplicationUser appUser) {

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities()));
        logger.debug("User {} explicitly added to security context.", appUser.getUsername());
        for (GrantedAuthority a : appUser.getAuthorities()) {
            logger.debug("Role {} explicitly added to security context.", a.getAuthority());
        }
    }

    /**
     * Add the provided user to the security context
     *
     * @param user
     */
    public void asUser(ApplicationUser user) {

        addUserToSecurityContext(user);
    }

    public List<GrantedAuthority> createUserRoles(AppRole.RoleType role, Long userRoleId) {

        List<GrantedAuthority> roles = new ArrayList<>();
        if (role != null) {
            roles.add(new SimpleGrantedAuthority(role.grantedRoleName()));
        } else {

            logger.warn("RoleId is null.");
        }

        if (userRoleId != null) {
            try {
                roles.addAll(this.registrationService.retrieveFacilities(userRoleId).stream()
                    .map(new SecurityService.FacilityToRoleTransform())
                    .collect(Collectors.toList()));
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        } else {

            logger.warn("UserRoleId is null.");
        }

        return roles;
    }

    public void evictUserCachedItems() {

        if (hasSecurityContext()) {

            Long userRoleId = getCurrentApplicationUser().getUserRoleId();
            evictUserCachedItems(userRoleId);

        } else {

            logger.warn("No user logged in. No cache items were evicted.");
        }
    }

    public FacilityAccessEnforcer facilityEnforcer() {

        if (hasRole(AppRole.RoleType.REVIEWER)) {

            return new ReviewerFacilityAccessEnforcerImpl();
        }

        return new FacilityAccessEnforcerImpl(this.programIdRepoLocator, getCurrentUserFacilityProgramIds());
    }

    public ApplicationUser getCurrentApplicationUser() {

        return (ApplicationUser) getCurrentPrincipal();
    }

    public String getCurrentUserId() {

        return getCurrentApplicationUser().getUsername();
    }

    /**
     * Check if the current user has any of the provided roles
     *
     * @param role
     * @return
     */
    public boolean hasRole(AppRole.RoleType role) {

        return getCurrentRoles().stream().anyMatch(r -> r.getAuthority().equals(role.grantedRoleName()));
    }

    public boolean hasSecurityContext() {

        return SecurityContextHolder.getContext() != null
            && SecurityContextHolder.getContext().getAuthentication() != null
            && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null;
    }

    List<GrantedAuthority> createUserRoles(Long roleId, Long userRoleId) {

        AppRole.RoleType role = null;
        if (roleId != null) {
            try {
                role = AppRole.RoleType.fromId(roleId);
            } catch (IllegalArgumentException e) {
                logger.warn(e.getMessage());
            }
        } else {

            logger.warn("RoleId is null.");
        }

        return createUserRoles(role, userRoleId);
    }

    void evictUserCachedItems(long userRoleId) {

        this.cacheManager.getCache(CacheName.UserProgramFacilities).evict(userRoleId);

        logger.info("Program Facilities for UserRoleId-[{}] were evicted from cache.", userRoleId);
    }

    ApplicationUser getCurrentApplicationUser(Authentication authentication) {

        return (ApplicationUser) getCurrentPrincipal();
    }

    /**
     * Check if the security context is empty
     *
     * @throws ApplicationException
     */
    private void checkSecurityContext() {

        if (hasSecurityContext() == false) {
            throw new ApplicationException(
                ApplicationErrorCode.E_AUTHORIZATION,
                "Security Context, authentication or principal is empty.");
        }
    }

    /**
     * Return the current security principal
     *
     * @return
     */
    private Object getCurrentPrincipal() {
        // checking security context
        checkSecurityContext();
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Get the security roles of the current user
     *
     * @return
     */
    private Collection<? extends GrantedAuthority> getCurrentRoles() {

        checkSecurityContext();
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    private List<String> getCurrentUserFacilityProgramIds() {

        return getCurrentApplicationUser().getAuthorities().stream()
            .filter(r -> r.getAuthority().startsWith(SecurityService.FacilityRolePrefix))
            .map(r -> r.getAuthority().substring(SecurityService.FacilityRolePrefix.length()))
            .collect(Collectors.toList());
    }

    static class FacilityToRoleTransform implements Function<ProgramFacility, GrantedAuthority> {

        @Override
        public GrantedAuthority apply(ProgramFacility programFacility) {

            return new SimpleGrantedAuthority(
                String.format("%s%s", FacilityRolePrefix, programFacility.getProgramId()));
        }
    }
}
