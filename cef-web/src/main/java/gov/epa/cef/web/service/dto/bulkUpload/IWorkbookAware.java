package gov.epa.cef.web.service.dto.bulkUpload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"_config", "emissionFormulaVariables"})
public interface IWorkbookAware {

}
