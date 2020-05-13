package gov.epa.cef.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Validated
@ConfigurationProperties(prefix = "cdx")
public class CdxConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, String> registerProgramFacilityEndpointConfiguration;
    private Map<String, String> registerSignEndpointConfiguration;

    @NotBlank
    private String naasUser;

    @NotBlank
    private String naasPassword;

    @NotNull
    private URL naasTokenUrl;

    @NotBlank
    private String naasIp;

    @NotBlank
    private String cdxBaseUrl;

    private final List<String> allowedOrigins = new ArrayList<>();

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

    public URL getNaasTokenUrl() {
        return naasTokenUrl;
    }

    public void setNaasTokenUrl(URL naasTokenUrl) {
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

    public List<String> getAllowedOrigins() {

        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {

        this.allowedOrigins.clear();
        if (allowedOrigins != null) {
            this.allowedOrigins.addAll(allowedOrigins);
        }
    }

    CorsConfigurationSource createCorsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(this.allowedOrigins);

        config.setAllowedMethods(
            Arrays.asList("GET", "POST", "DELETE", "PUT", "HEAD", "PATCH"));
        config.setAllowedHeaders(
            Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-XSRF-TOKEN", "X-Requested-With"));

        UrlBasedCorsConfigurationSource result = new UrlBasedCorsConfigurationSource();
        result.registerCorsConfiguration("/**", config);

        logger.info("Created CORS Configuration w/ allowedOrigins: {}",
            String.join(", ", this.allowedOrigins));

        return result;
    }

}
