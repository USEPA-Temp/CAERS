package gov.epa.cef.web.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cdx")
public class CdxConfig {

	private Map<String, String> registerProgramFacilityEndpointConfiguration;

	public Map<String, String> getRegisterProgramFacilityEndpointConfiguration() {
		return registerProgramFacilityEndpointConfiguration;
	}

	public void setRegisterProgramFacilityEndpointConfiguration(
			Map<String, String> registerProgramFacilityEndpointConfiguration) {
		this.registerProgramFacilityEndpointConfiguration = registerProgramFacilityEndpointConfiguration;
	}

	public String getRegisterProgramFacilityOperatorUser() {
		return getRegisterProgramFacilityEndpointConfiguration().get("operatorId");
	}

	public String getRegisterProgramFacilityOperatorPassword() {
		return getRegisterProgramFacilityEndpointConfiguration().get("operatorPassword");
	}

	public String getRegisterProgramFacilityServiceEndpoint() {
		return getRegisterProgramFacilityEndpointConfiguration().get("serviceUrl");
	}
}
