package gov.epa.cef.web.security;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

public class AppRole {

	public enum RoleType {
		Peparer(142710L, ROLE_PREPARER), 
		Certifier(142720L, ROLE_CERTIFIER), 
		Reviewer(142730L, ROLE_REVIEWER),
		Unknown(-9999, "UNKNOWN");

		private final long id;

		private final String roleName;

		RoleType(long id, String roleName) {

			this.id = id;
			this.roleName = roleName;
		}

		public static RoleType fromId(long id) {

			RoleType result = null;

			for (RoleType roleType : RoleType.values()) {
				if (roleType.id == id) {
					result = roleType;
				}
			}

			if (result == null) {

				String msg = String.format("Role ID %d is not valid.", id);
				throw new IllegalArgumentException(msg);
			}

			return result;
		}

		public static RoleType fromRoleName(String roleName) {

			RoleType result = RoleType.Unknown;

			if (StringUtils.isNotEmpty(roleName)) {

				for (RoleType roleType : RoleType.values()) {
					if (roleType.isSameRoleName(roleName)) {
						result = roleType;
					}
				}
			}

			return result;
		}

		public net.exchangenetwork.wsdl.register.program_facility._1.RegistrationRoleType facilityRole() {

			net.exchangenetwork.wsdl.register.program_facility._1.RegistrationRoleType result = new net.exchangenetwork.wsdl.register.program_facility._1.RegistrationRoleType();
			result.setCode(this.id);

			return result;
		}

		public long getId() {

			return this.id;
		}

		public String grantedRoleName() {

			return this.roleName;
		}

		public boolean isOneOf(RoleType primary, RoleType... secondaries) {

			boolean result = equals(primary);
			if (result == false && secondaries != null) {

				for (RoleType test : secondaries) {
					if (equals(test)) {
						result = true;
						break;
					}
				}
			}

			return result;
		}

		public boolean isSameRoleName(String role) {

			return this.roleName.substring(5).equals(role);
		}

		public static Collection<RoleType> cdxRoles() {

			return Stream.of(values())
					.filter(r -> r.getId() > 0)
					.collect(Collectors.toList());
		}

		public String roleName() {

			return this.roleName.substring(5);
		}
	}

	public static final String ROLE_PREPARER = "ROLE_Preparer";

	public static final String ROLE_CERTIFIER = "ROLE_Certifier";

	public static final String ROLE_REVIEWER = "ROLE_Reviewer";

}
