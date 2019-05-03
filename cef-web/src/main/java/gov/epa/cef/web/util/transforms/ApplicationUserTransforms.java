package gov.epa.cef.web.util.transforms;

import java.util.function.Function;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.dto.UserDto;

/**
 * Class that contains transforms for {@link ApplicationUser}
 * @author tfesperm
 *
 */
public class ApplicationUserTransforms {

	/**
	 * Transform {@link ApplicationUser} into {@link UserDto}
	 * @return
	 */
	public static Function<ApplicationUser, UserDto> toUserDto() {

		return appUser -> new UserDto()
				.withCdxUserId(appUser.getUserId())
				.withEmail(appUser.getEmail())
				.withFirstName(appUser.getFirstName())
				.withLastName(appUser.getLastName())
				.withRole(appUser.getIdTypeText())
				.withUserRoleId(appUser.getUserRoleId());
	}
	
}
