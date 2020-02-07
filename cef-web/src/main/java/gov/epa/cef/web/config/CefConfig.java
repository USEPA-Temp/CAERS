package gov.epa.cef.web.config;

import gov.epa.cef.web.config.slt.GAConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix = "cef", ignoreInvalidFields = true)
public class CefConfig {

    @Autowired
    protected CdxConfig cdxConfig;

    @Autowired
    protected GAConfig gaConfig;

    private String defaultEmailAddress;

    private final List<String> admins = new ArrayList<>();

    private BigDecimal qaTolerance;

    public List<String> getAdmins() {

        return admins;
    }

    public Collection<Object> getAdminsAsLowerCase() {

        return this.admins.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public void setAdmins(List<String> admins) {

        this.admins.clear();
        if (admins != null) {
            this.admins.addAll(admins);
        }
    }

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

    public BigDecimal getQaTolerance() {
        return qaTolerance;
    }

    public void setQaTolerance(BigDecimal qaTolerance) {
        this.qaTolerance = qaTolerance;
    }

}
