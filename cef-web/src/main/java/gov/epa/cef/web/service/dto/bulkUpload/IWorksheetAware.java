package gov.epa.cef.web.service.dto.bulkUpload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"_row"})
public interface IWorksheetAware {

}
