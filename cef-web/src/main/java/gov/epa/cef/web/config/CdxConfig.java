package gov.epa.cef.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Component
@Validated
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cdx")
public class CdxConfig {

    private Map<String, String> registerProgramFacilityEndpointConfiguration;
    private Map<String, String> registerSignEndpointConfiguration;

    @NotBlank
    private String naasUser;

    @NotBlank
    private String naasPassword;

    @NotBlank
    private String naasTokenUrl;

    @NotBlank
    private String naasIp;

    @NotBlank
    private String cdxBaseUrl;

    public Map<String, String> getRegisterProgramFacilityEndpointConfiguration() {
        return registerProgramFacilityEndpointConfiguration;
    }

    public void setRegisterProgramFacilityEndpointConfiguration(Map<String, String> registerProgramFacilityEndpointConfiguration) {
        this.registerProgramFacilityEndpointConfiguration = registerProgramFacilityEndpointConfiguration;
    }
    public String getRegisterProgramFacilityServiceEndpoint() {
        return getRegisterProgramFacilityEndpointConfiguration().get("serviceUrl");
    }

    public Map<String, String> getRegisterSignEndpointConfiguration() {
        return registerSignEndpointConfiguration;
    }

    public void setRegisterSignEndpointConfiguration(Map<String, String> registerSignEndpointConfiguration) {
        this.registerSignEndpointConfiguration = registerSignEndpointConfiguration;
    }
    public String getRegisterSignServiceEndpoint() {
        return getRegisterSignEndpointConfiguration().get("serviceUrl");
    }

    public String getNaasUser() {
        return naasUser;
    }

    public void setNaasUser(String naasUser) {
        this.naasUser = naasUser;
    }

    public String getNaasPassword() {
        return naasPassword;
    }

    public void setNaasPassword(String naasPassword) {
        this.naasPassword = naasPassword;
    }

    public String getNaasTokenUrl() {
        return naasTokenUrl;
    }

    public void setNaasTokenUrl(String naasTokenUrl) {
        this.naasTokenUrl = naasTokenUrl;
    }

    public String getNaasIp() {
        return naasIp;
    }

    public void setNaasIp(String naasIp) {
        this.naasIp = naasIp;
    }

    public String getCdxBaseUrl() {
        return cdxBaseUrl;
    }

    public void setCdxBaseUrl(String cdxBaseUrl) {
        this.cdxBaseUrl = cdxBaseUrl;
    }

}
