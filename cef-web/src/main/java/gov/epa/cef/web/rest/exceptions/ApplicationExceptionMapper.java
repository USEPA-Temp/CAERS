package gov.epa.cef.web.rest.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import gov.epa.cef.web.exception.ApplicationException;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {
    public Response toResponse(ApplicationException e) {
        switch (e.getErrorCode()) {
            case E_Validation:
                return getResponse(400, e);
            case E_Authentication:
                return getResponse(401, e);
            case E_Authorization:
                return getResponse(403, e);
            case E_NotFound:
                return getResponse(404, e);
            default:
                return getResponse(500, e);
        }
    }

    private Response getResponse(int status, ApplicationException e) {
        return Response
                .status(status)
                .entity(e.getMessage())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Authorization")
                .build();
    }
}
