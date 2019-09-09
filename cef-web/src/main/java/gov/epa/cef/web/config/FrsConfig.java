package gov.epa.cef.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Component
@EnableConfigurationProperties
@Validated
@ConfigurationProperties(prefix = "frs")
public class FrsConfig {

    @NotNull
    private URL facilityIptEndpoint;

    @NotBlank
    private String naasIp;

    @NotBlank
    private String naasPassword;

    @NotNull
    private URL naasTokenUrl;

    @NotBlank
    private String naasUser;

    public URL getFacilityIptEndpoint() {

        return facilityIptEndpoint;
    }

    public void setFacilityIptEndpoint(URL facilityIptEndpoint) {

        this.facilityIptEndpoint = facilityIptEndpoint;
    }

    public String getNaasIp() {

        return naasIp;
    }

    public void setNaasIp(String naasIp) {

        this.naasIp = naasIp;
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

    public String getNaasUser() {

        return naasUser;
    }

    public void setNaasUser(String naasUser) {

        this.naasUser = naasUser;
    }
}
