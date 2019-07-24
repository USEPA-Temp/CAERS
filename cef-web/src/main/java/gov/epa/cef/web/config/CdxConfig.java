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
    private Map<String, String> registerSignEndpointConfiguration;
    
    private String naasUser;
    private String naasPassword;
    private String naasTokenUrl;
    private String naasIp;
    private String frsBaseUrl;

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

    public String getFrsBaseUrl() {
        return frsBaseUrl;
    }

    public void setFrsBaseUrl(String frsBaseUrl) {
        this.frsBaseUrl = frsBaseUrl;
    }

}
