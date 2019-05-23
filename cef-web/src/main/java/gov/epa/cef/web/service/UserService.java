package gov.epa.cef.web.service;

public interface UserService {
    
    /**
     * Creates a new NAAS token for the currently authenticated user
     * 
     * @param userId
     * @return NAAS token
     */
    String createToken(String userId);

}
