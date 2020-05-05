package gov.epa.cef.web.config;

import gov.epa.cef.web.provider.system.IPropertyKey;

public enum AppPropertyName implements IPropertyKey {

    AdminEmailAddresses("email.admin"),
    DefaultEmailAddress("email.default"),
    EnvAdmins("env.admins"),
    EmissionsTotalErrorTolerance("emissions.tolerance.total.error"),
    EmissionsTotalWarningTolerance("emissions.tolerance.total.warning"),
    FeatureBulkEntryEnabled("feature.bulk-entry.enabled"),
    LastSccUpdateDate("task.scc-update.last-ran"),
    SccUpdateTaskCron("task.scc-update.cron"),
    SccUpdateTaskEnabled("task.scc-update.enabled");

    private final String key;

    AppPropertyName(String key) {

        this.key = key;
    }

    @Override
    public String configKey() {

        return this.key;
    }
}
