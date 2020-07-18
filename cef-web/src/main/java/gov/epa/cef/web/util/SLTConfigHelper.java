package gov.epa.cef.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;

@Service
public class SLTConfigHelper {

    @Autowired
    private CefConfig cefConfig;

    //find and return the current SLT Configuration for the user/report that is currently being worked on
    public SLTBaseConfig getCurrentSLTConfig(String slt) {
        SLTBaseConfig currentConfig = null;
        switch (slt) {
            case "GA":
                currentConfig = cefConfig.getGaConfig();
            case "DC":
                currentConfig = cefConfig.getDcConfig();
            default:
                // do nothing
        }

        return currentConfig;
    }

}
