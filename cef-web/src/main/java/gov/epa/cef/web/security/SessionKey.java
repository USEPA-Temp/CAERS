package gov.epa.cef.web.security;

public enum SessionKey {

    UserRoleId("__CefSessionKey_UserRoleId__"),
    SessionUuid("__CefSessionKey_SessionUuid__");

    private final String key;

    SessionKey(String key) {

        this.key = key;
    }

    public String key() {
        return this.key;
    }
}
