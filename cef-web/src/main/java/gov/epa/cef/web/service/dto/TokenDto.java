package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class TokenDto implements Serializable{
    
    /**
     * default version
     */
    private static final long serialVersionUID = 1L;
    
    
    private String token;
    private Long userRoleId;
    private String baseServiceUrl;
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getUserRoleId() {
        return userRoleId;
    }
    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }
    public String getBaseServiceUrl() {
        return baseServiceUrl;
    }
    public void setBaseServiceUrl(String baseServiceUrl) {
        this.baseServiceUrl = baseServiceUrl;
    }
}