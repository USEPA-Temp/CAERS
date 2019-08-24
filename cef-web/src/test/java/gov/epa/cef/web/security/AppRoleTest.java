package gov.epa.cef.web.security;

import gov.epa.cef.web.security.AppRole.RoleType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class AppRoleTest extends BaseSecurityTest {


    @Test
    public void testRoleNames_ShouldReturn_TheValidRolesNames(){
        assertEquals("ROLE_Preparer", AppRole.ROLE_PREPARER);
        assertEquals("ROLE_Certifier", AppRole.ROLE_CERTIFIER);
        assertEquals("ROLE_Reviewer", AppRole.ROLE_REVIEWER);
    }

    @Test
    public void RoleType_getFormId_Should_ReturnValidRoleType_WhenTheCorrespondingIdPassed() {
        assertEquals(RoleType.PREPARER, RoleType.fromId(142710L));
        assertEquals(RoleType.CERTIFIER, RoleType.fromId(142720L));
        assertEquals(RoleType.REVIEWER, RoleType.fromId(142730L));
    }

    @Test(expected=IllegalArgumentException.class)
    public void RoleType_getFormId_Should_ThrowException_WhenTheInvalidIddPassed() {
       RoleType.fromId(142L);
    }

    @Test
    public void RoleType_fromRoleName_Should_ReturnValidRoleType_WhenTheCorrespondingNamePassed() {
        assertEquals(RoleType.PREPARER, RoleType.fromRoleName("Preparer"));
        assertEquals(RoleType.CERTIFIER, RoleType.fromRoleName("Certifier"));
        assertEquals(RoleType.REVIEWER, RoleType.fromRoleName("Reviewer"));
        assertEquals(RoleType.UNKNOWN, RoleType.fromRoleName(""));
    }

    @Test
    public void RoleType_grantedRoleName_Should_ReturnGrantedRoleName() {
        assertEquals("ROLE_Preparer", RoleType.PREPARER.grantedRoleName());
        assertEquals("ROLE_Certifier", RoleType.CERTIFIER.grantedRoleName());
        assertEquals("ROLE_Reviewer", RoleType.REVIEWER.grantedRoleName());
    }

    @Test
    public void RoleType_facilityRole_Should_ReturnRegistrationRoleType() {
        assertNotEquals(null, RoleType.PREPARER.facilityRole());
        assertNotEquals(null, RoleType.CERTIFIER.facilityRole());
        assertNotEquals(null, RoleType.REVIEWER.facilityRole());
    }

    @Test
    public void RoleType_getId_Should_ReturnTheCorrspondingIdForTheRoleType() {
        assertEquals(142710L, RoleType.PREPARER.getId());
        assertEquals(142720L, RoleType.CERTIFIER.getId());
        assertEquals(142730L, RoleType.REVIEWER.getId());
    }

    @Test
    public void RoleType_isOneOf_Should_ReturnTrue_When_TheRoleTypeIsPartOfThePassedOnes() {
        assertEquals(Boolean.TRUE, RoleType.PREPARER.isOneOf(RoleType.CERTIFIER, RoleType.REVIEWER, RoleType.PREPARER));
    }

    @Test
    public void RoleType_roleName_Should_ReturnRoleName() {
        assertEquals("Preparer", RoleType.PREPARER.roleName());
        assertEquals("Certifier", RoleType.CERTIFIER.roleName());
        assertEquals("Reviewer", RoleType.REVIEWER.roleName());
    }

    @Test
    public void RoleType_cdxRoles_Should_ReturnListOfThreeCdxRoles() {
        assertEquals(3, RoleType.PREPARER.cdxRoles().size());
    }
}
