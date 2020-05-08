package gov.epa.cef.web.config;

import gov.epa.cef.web.config.slt.GAConfig;
import gov.epa.cef.web.provider.system.PropertyProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CefConfig {

    @Autowired
    protected CdxConfig cdxConfig;

    @Autowired
    protected GAConfig gaConfig;

    @Autowired
    protected PropertyProvider propertyProvider;

    public List<String> getAdmins() {

        return this.propertyProvider.getStringList(AppPropertyName.EnvAdmins);
    }

    public Collection<Object> getAdminsAsLowerCase() {

        return this.getAdmins().stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public List<String> getAdminEmails() {

        return this.propertyProvider.getStringList(AppPropertyName.AdminEmailAddresses);
    }

    public CdxConfig getCdxConfig() {
        return cdxConfig;
    }

    public GAConfig getGaConfig() {
        return gaConfig;
    }

    public String getDefaultEmailAddress() {
        return this.propertyProvider.getString(AppPropertyName.DefaultEmailAddress);
    }

    public BigDecimal getEmissionsTotalErrorTolerance() {
        return this.propertyProvider.getBigDecimal(AppPropertyName.EmissionsTotalErrorTolerance);
    }

    public BigDecimal getEmissionsTotalWarningTolerance() {
        return this.propertyProvider.getBigDecimal(AppPropertyName.EmissionsTotalWarningTolerance);
    }

    public boolean getFeatureBulkEntryEnabled() {
        return this.propertyProvider.getBoolean(AppPropertyName.FeatureBulkEntryEnabled);
    }
    
    public boolean getFeatureUserFeedbackEnabled() {
        return this.propertyProvider.getBoolean(AppPropertyName.FeatureUserFeedbackEnabled);
    }

    public String getLastSccUpdateDate() {
        return this.propertyProvider.getString(AppPropertyName.LastSccUpdateDate);
    }

    public String getSccUpdateTaskCron() {
        return this.propertyProvider.getString(AppPropertyName.SccUpdateTaskCron);
    }

    public boolean getSccUpdateTaskEnabled() {
        return this.propertyProvider.getBoolean(AppPropertyName.SccUpdateTaskEnabled);
    }

}
