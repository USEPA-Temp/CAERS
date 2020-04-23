package gov.epa.cef.web.config;

import gov.epa.cef.web.provider.system.IPropertyKey;

public enum AppPropertyName implements IPropertyKey {

    DefaultEmailAddress("email.default"),
    EnvAdmins("env.admins"),
    EmissionsTotalErrorTolerance("emissions.tolerance.total.error"),
    EmissionsTotalWarningTolerance("emissions.tolerance.total.warning");

    private final String key;

    AppPropertyName(String key) {

        this.key = key;
    }

    @Override
    public String configKey() {

        return this.key;
    }
}
