package gov.epa.cef.web.config.mock;

import gov.epa.cef.web.config.SLTBaseConfig;

public class MockSLTConfig implements SLTBaseConfig {

    private String sltEmail;
    private String sltEisUser;
    private String sltEisProgramCode;

    public String getSltEmail() {
        return sltEmail;
    }

    public void setSltEmail(String sltEmail) {
        this.sltEmail = sltEmail;
    }

    public String getSltEisUser() {
        return sltEisUser;
    }

    public void setSltEisUser(String sltEisUser) {
        this.sltEisUser = sltEisUser;
    }

    public String getSltEisProgramCode() {
        return sltEisProgramCode;
    }

    public void setSltEisProgramCode(String sltEisProgramCode) {
        this.sltEisProgramCode = sltEisProgramCode;
    }

}
