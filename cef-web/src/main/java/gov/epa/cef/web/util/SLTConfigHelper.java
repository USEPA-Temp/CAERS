package gov.epa.cef.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.config.slt.SLTAgencyCode;
import gov.epa.cef.web.config.slt.SLTConfigImpl;
import gov.epa.cef.web.provider.system.SLTPropertyProvider;

@Service
public class SLTConfigHelper {

    private final SLTPropertyProvider propertyProvider;

    private final SLTConfigImpl dcConfig;

    private final SLTConfigImpl gaConfig;

    @Autowired
    public SLTConfigHelper(SLTPropertyProvider propertyProvider) {

        super();

        this.propertyProvider = propertyProvider;
        this.dcConfig = new SLTConfigImpl(SLTAgencyCode.DC, propertyProvider);
        this.gaConfig = new SLTConfigImpl(SLTAgencyCode.GA, propertyProvider);
    }

    //find and return the current SLT Configuration for the user/report that is currently being worked on
    public SLTBaseConfig getCurrentSLTConfig(String slt) {
        SLTBaseConfig currentConfig = null;
        switch (slt) {
            case "GA":
                currentConfig = this.gaConfig;
                break;
            case "DC":
                currentConfig = this.dcConfig;
                break;
            default:
                // do nothing
        }

        return currentConfig;
    }

}
