package gov.epa.cef.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Component
@Validated
@ConfigurationProperties(prefix = "frs")
public class FrsConfig {

    private boolean debugging = false;

    @NotNull
    private URL facilityIptEndpoint;

    @NotBlank
    private String naasPassword;

    @NotBlank
    private String naasUser;

    public URL getFacilityIptEndpoint() {

        return facilityIptEndpoint;
    }

    public void setFacilityIptEndpoint(URL facilityIptEndpoint) {

        this.facilityIptEndpoint = facilityIptEndpoint;
    }

    public String getNaasPassword() {

        return naasPassword;
    }

    public void setNaasPassword(String naasPassword) {

        this.naasPassword = naasPassword;
    }

    public String getNaasUser() {

        return naasUser;
    }

    public void setNaasUser(String naasUser) {

        this.naasUser = naasUser;
    }

    public boolean isDebugging() {

        return debugging;
    }

    public void setDebugging(boolean debugging) {

        this.debugging = debugging;
    }
}
