package gov.epa.cef.web.config.slt;

import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.config.SLTPropertyName;
import gov.epa.cef.web.provider.system.SLTPropertyProvider;

public class SLTConfigImpl implements SLTBaseConfig {

    private final SLTAgencyCode agencyCode;

    private final SLTPropertyProvider propertyProvider;

    public SLTConfigImpl(SLTAgencyCode agencyCode, SLTPropertyProvider propertyProvider) {

        super();

        this.agencyCode = agencyCode;
        this.propertyProvider = propertyProvider;

    }

    public String getSltEmail() {
        return this.propertyProvider.getString(SLTPropertyName.EmailAddress, agencyCode);
    }

    public String getSltEisUser() {
        return this.propertyProvider.getString(SLTPropertyName.EisUser, agencyCode);
    }

    public String getSltEisProgramCode() {
        return this.propertyProvider.getString(SLTPropertyName.EisProgramCode, agencyCode);
    }

}
