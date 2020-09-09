package gov.epa.cef.web.config;

import gov.epa.cef.web.provider.system.AdminPropertyProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    protected AdminPropertyProvider propertyProvider;

    @Autowired
    protected Environment environment;

    public List<String> getAdminEmails() {

        return this.propertyProvider.getStringList(AppPropertyName.AdminEmailAddresses);
    }

    public CdxConfig getCdxConfig() {
        return cdxConfig;
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

    public String getMaxFileSize() {
    	return environment.getProperty("spring.servlet.multipart.max-file-size");
    }

    public Boolean getExcelExportEnabled() {
        return environment.getProperty("excel-export.enabled", Boolean.class, Boolean.FALSE);
    }

}
