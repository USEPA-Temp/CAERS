package gov.epa.cef.web.security;

public enum SessionKey {

    UserRoleId("__CefSessionKey_UserRoleId__");

    private final String key;

    SessionKey(String key) {

        this.key = key;
    }

    public String key() {
        return this.key;
    }
}
