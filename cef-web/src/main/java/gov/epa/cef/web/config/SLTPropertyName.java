package gov.epa.cef.web.config;

import gov.epa.cef.web.provider.system.IPropertyKey;

public enum SLTPropertyName implements IPropertyKey {

    EmailAddress("slt-email"),
    EisUser("slt-eis-user"),
    EisProgramCode("slt-eis-program-code"),
	SltSupportEmail("slt-support-email");

    private final String key;

    SLTPropertyName(String key) {

        this.key = key;
    }

    @Override
    public String configKey() {

        return this.key;
    }
}
