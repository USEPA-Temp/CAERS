package gov.epa.cef.web.service.dto;

import gov.epa.cef.web.domain.EfVariableValidationType;

public class EfVariableCodeDto extends CodeLookupDto {

    private static final long serialVersionUID = 1L;

    private EfVariableValidationType validationType;

    public EfVariableValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(EfVariableValidationType validationType) {
        this.validationType = validationType;
    }

}
