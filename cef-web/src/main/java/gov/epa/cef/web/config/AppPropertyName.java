package gov.epa.cef.web.config;

import gov.epa.cef.web.provider.system.IPropertyKey;

public enum AppPropertyName implements IPropertyKey {

    AdminEmailAddresses("email.admin"),
    AdminEmailEnabled("email.admin.enabled"),
    DefaultEmailAddress("email.default"),
    DevEmailAddress("email.dev"),
    ProdEmailAddress("email.prod"),
    EmissionsTotalErrorTolerance("emissions.tolerance.total.error"),
    EmissionsTotalWarningTolerance("emissions.tolerance.total.warning"),
    FeatureAnnouncementEnabled("feature.announcement.enabled"),
    FeatureAnnouncementText("feature.announcement.text"),
    FeatureBulkEntryEnabled("feature.bulk-entry.enabled"),
    FeatureUserFeedbackEnabled("feature.user-feedback.enabled"),
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
