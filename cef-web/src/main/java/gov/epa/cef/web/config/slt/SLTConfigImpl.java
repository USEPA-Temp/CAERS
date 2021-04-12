package gov.epa.cef.web.config.slt;

import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.config.SLTPropertyName;
import gov.epa.cef.web.provider.system.SLTPropertyProvider;

public class SLTConfigImpl implements SLTBaseConfig {

    private final String programSystemCode;

    private final SLTPropertyProvider propertyProvider;

    public SLTConfigImpl(String programSystemCode, SLTPropertyProvider propertyProvider) {

        super();

        this.programSystemCode = programSystemCode;
        this.propertyProvider = propertyProvider;

    }

    public String getSltEmail() {
        return this.propertyProvider.getString(SLTPropertyName.EmailAddress, programSystemCode);
    }

    public String getSltEisUser() {
        return this.propertyProvider.getString(SLTPropertyName.EisUser, programSystemCode);
    }

    public String getSltEisProgramCode() {
        return this.propertyProvider.getString(SLTPropertyName.EisProgramCode, programSystemCode);
    }
    
}
