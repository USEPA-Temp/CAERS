package gov.epa.cef.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import gov.epa.cef.web.config.slt.GAConfig;

@Component
@ConfigurationProperties(prefix = "cef", ignoreInvalidFields = true)
public class CefConfig {

    @Autowired
    protected CdxConfig cdxConfig;
    
    @Autowired
    protected GAConfig gaConfig;
    
    private String defaultEmailAddress;


    public CdxConfig getCdxConfig() {
        return cdxConfig;
    }
    
    public GAConfig getGaConfig() {
        return gaConfig;
    }

    public String getDefaultEmailAddress() {
        return defaultEmailAddress;
    }

    public void setDefaultEmailAddress(String defaultEmailAddress) {
        this.defaultEmailAddress = defaultEmailAddress;
    }

}
