package gov.epa.cef.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.config.slt.SLTConfigImpl;
import gov.epa.cef.web.provider.system.SLTPropertyProvider;

@Service
public class SLTConfigHelper {

    private final SLTPropertyProvider propertyProvider;

    @Autowired
    public SLTConfigHelper(SLTPropertyProvider propertyProvider) {

        super();

        this.propertyProvider = propertyProvider;
    }

    //find and return the current SLT Configuration for the user/report that is currently being worked on
    public SLTBaseConfig getCurrentSLTConfig(String slt) {

        return new SLTConfigImpl(slt, propertyProvider);
    }

}
